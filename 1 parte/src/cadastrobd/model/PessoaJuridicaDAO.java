/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

import cadastro.model.util.ConectorBD;
import cadastro.model.util.SequenceManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nanda
 */

public class PessoaJuridicaDAO {
    private final ConectorBD conector;
    private final SequenceManager sequenceManager;

    public PessoaJuridicaDAO() {
        this.conector = new ConectorBD();
        this.sequenceManager = new SequenceManager();
    }

    public PessoaJuridica getPessoa(int idPessoaJuridica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = conector.getConnection();
            String sql = "SELECT * FROM pessoa_juridica WHERE idPessoaJuridica = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idPessoaJuridica);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return buildPessoaJuridicaFromResultSet(resultSet);
            }
        } catch (SQLException e) {
        } finally {
            conector.close(resultSet);
            conector.close(preparedStatement);
            conector.close(connection);
        }

        return null;
    }

    public List<PessoaJuridica> getPessoas() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();

        try {
            connection = conector.getConnection();
            String sql = "SELECT * FROM pessoa_juridica";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pessoasJuridicas.add(buildPessoaJuridicaFromResultSet(resultSet));
            }
        } catch (SQLException e) {
        } finally {
            conector.close(resultSet);
            conector.close(preparedStatement);
            conector.close(connection);
        }

        return pessoasJuridicas;
    }

    public void incluir(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = conector.getConnection();
            connection.setAutoCommit(false);

            int idPessoaJuridica = sequenceManager.getValue("pessoa_juridica_seq");

            String sqlPessoa = "INSERT INTO pessoa (idPessoaJuridica, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlPessoa);
            preparedStatement.setInt(1, idPessoaJuridica);
            preparedStatement.setString(2, pessoaJuridica.getNome());
            preparedStatement.setString(3, pessoaJuridica.getLogradouro());
            preparedStatement.setString(4, pessoaJuridica.getCidade());
            preparedStatement.setString(5, pessoaJuridica.getEstado());
            preparedStatement.setString(6, pessoaJuridica.getTelefone());
            preparedStatement.setString(7, pessoaJuridica.getEmail());
            preparedStatement.executeUpdate();

            String sqlPessoaJuridica = "INSERT INTO pessoa_juridica (idPessoaJuridica, cnpj) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sqlPessoaJuridica);
            preparedStatement.setInt(1, idPessoaJuridica);
            preparedStatement.setString(2, pessoaJuridica.getCnpj());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                }
            }
        } finally {
            conector.close(preparedStatement);
            conector.close(connection);
        }
    }

    public void alterar(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = conector.getConnection();
            connection.setAutoCommit(false);

            String sqlPessoa = "UPDATE pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoaJuridica = ?";
            preparedStatement = connection.prepareStatement(sqlPessoa);
            preparedStatement.setString(1, pessoaJuridica.getNome());
            preparedStatement.setString(2, pessoaJuridica.getLogradouro());
            preparedStatement.setString(3, pessoaJuridica.getCidade());
            preparedStatement.setString(4, pessoaJuridica.getEstado());
            preparedStatement.setString(5, pessoaJuridica.getTelefone());
            preparedStatement.setString(6, pessoaJuridica.getEmail());
            preparedStatement.setInt(7, pessoaJuridica.getId());
            preparedStatement.executeUpdate();

            String sqlPessoaJuridica = "UPDATE pessoa_juridica SET cnpj = ? WHERE idPessoaJuridica = ?";
            preparedStatement = connection.prepareStatement(sqlPessoaJuridica);
            preparedStatement.setString(1, pessoaJuridica.getCnpj());
            preparedStatement.setInt(2, pessoaJuridica.getId());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                }
            }
        } finally {
            conector.close(preparedStatement);
            conector.close(connection);
        }
    }

    public void excluir(int idPessoaJuridica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = conector.getConnection();
            connection.setAutoCommit(false);

            String sqlPessoaJuridica = "DELETE FROM pessoa_juridica WHERE idPessoaJuridica = ?";
            preparedStatement = connection.prepareStatement(sqlPessoaJuridica);
            preparedStatement.setInt(1, idPessoaJuridica);
            preparedStatement.executeUpdate();

            String sqlPessoa = "DELETE FROM pessoa WHERE idPessoaJuridica = ?";
            preparedStatement = connection.prepareStatement(sqlPessoa);
            preparedStatement.setInt(1, idPessoaJuridica);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                }
            }
        } finally {
            conector.close(preparedStatement);
            conector.close(connection);
        }
    }

    private PessoaJuridica buildPessoaJuridicaFromResultSet(ResultSet resultSet) throws SQLException {
        int idPessoaJuridica = resultSet.getInt("idPessoaJuridica");
        String nome = resultSet.getString("nome");
        String logradouro = resultSet.getString("logradouro");
        String cidade = resultSet.getString("cidade");
        String estado = resultSet.getString("estado");
        String telefone = resultSet.getString("telefone");
        String email = resultSet.getString("email");
        String cnpj = resultSet.getString("cnpj");

        PessoaJuridica pessoaJuridica = new PessoaJuridica(idPessoaJuridica, nome, logradouro, cidade, estado, telefone, email, cnpj);
        return pessoaJuridica;
    }
}