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

public class PessoaFisicaDAO {
    private ConectorBD conector;
    private SequenceManager sequenceManager;

    public PessoaFisicaDAO() {
        this.conector = new ConectorBD();
        this.sequenceManager = new SequenceManager();
    }

    public PessoaFisica getPessoa(int idPessoa) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = conector.getConnection();
            String sql = "SELECT * FROM pessoa_fisica WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idPessoa);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return buildPessoaFisicaFromResultSet(resultSet);
            }
        } catch (SQLException e) {
        } finally {
            conector.close(resultSet);
            conector.close(preparedStatement);
            conector.close(connection);
        }

        return null;
    }

    public List<PessoaFisica> getPessoas() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();

        try {
            connection = conector.getConnection();
            String sql = "SELECT * FROM pessoa_fisica";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pessoasFisicas.add(buildPessoaFisicaFromResultSet(resultSet));
            }
        } catch (SQLException e) {
        } finally {
            conector.close(resultSet);
            conector.close(preparedStatement);
            conector.close(connection);
        }

        return pessoasFisicas;
    }

    public void incluir(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = conector.getConnection();
            connection.setAutoCommit(false);

            int idPessoa = sequenceManager.getValue("pessoa_fisica_seq");

            String sqlPessoa = "INSERT INTO pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlPessoa);
            preparedStatement.setInt(1, idPessoa);
            preparedStatement.setString(2, pessoaFisica.getNome());
            preparedStatement.setString(3, pessoaFisica.getLogradouro());
            preparedStatement.setString(4, pessoaFisica.getCidade());
            preparedStatement.setString(5, pessoaFisica.getEstado());
            preparedStatement.setString(6, pessoaFisica.getTelefone());
            preparedStatement.setString(7, pessoaFisica.getEmail());
            preparedStatement.executeUpdate();

            String sqlPessoaFisica = "INSERT INTO pessoa_fisica (idPessoa, cpf) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sqlPessoaFisica);
            preparedStatement.setInt(1, idPessoa);
            preparedStatement.setString(2, pessoaFisica.getCpf());
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

    public void alterar(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = conector.getConnection();
            connection.setAutoCommit(false);

            String sqlPessoa = "UPDATE pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlPessoa);
            preparedStatement.setString(1, pessoaFisica.getNome());
            preparedStatement.setString(2, pessoaFisica.getLogradouro());
            preparedStatement.setString(3, pessoaFisica.getCidade());
            preparedStatement.setString(4, pessoaFisica.getEstado());
            preparedStatement.setString(5, pessoaFisica.getTelefone());
            preparedStatement.setString(6, pessoaFisica.getEmail());
            preparedStatement.setInt(7, pessoaFisica.getId());
            preparedStatement.executeUpdate();

            String sqlPessoaFisica = "UPDATE pessoa_fisica SET cpf = ? WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlPessoaFisica);
            preparedStatement.setString(1, pessoaFisica.getCpf());
            preparedStatement.setInt(2, pessoaFisica.getId());
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

    public void excluir(int idPessoa) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = conector.getConnection();
            connection.setAutoCommit(false);

            String sqlPessoaFisica = "DELETE FROM pessoa_fisica WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlPessoaFisica);
            preparedStatement.setInt(1, idPessoa);
            preparedStatement.executeUpdate();

            String sqlPessoa = "DELETE FROM pessoa WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlPessoa);
            preparedStatement.setInt(1, idPessoa);
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

    private PessoaFisica buildPessoaFisicaFromResultSet(ResultSet resultSet) throws SQLException {
        int idPessoa = resultSet.getInt("idPessoa");
        String nome = resultSet.getString("nome");
        String logradouro = resultSet.getString("logradouro");
        String cidade = resultSet.getString("cidade");
        String estado = resultSet.getString("estado");
        String telefone = resultSet.getString("telefone");
        String email = resultSet.getString("email");
        String cpf = resultSet.getString("cpf");

        PessoaFisica pessoaFisica = new PessoaFisica(idPessoa, nome, logradouro, cidade, estado, telefone, email, cpf);
        return pessoaFisica;
    }
}

