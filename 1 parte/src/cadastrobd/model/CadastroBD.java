/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

import java.util.List;

/**
 *
 * @author Nanda
 */

public class CadastroBD {
    public static void main(String[] args) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        // Operações para Pessoa Física
        PessoaFisica pessoaFisica = new PessoaFisica(1, "Fulano", "Rua A", "Cidade A", "Estado A", "123456789", "fulano@gmail.com", "123.456.789-01");
        pessoaFisicaDAO.incluir(pessoaFisica);
        System.out.println("Pessoa Fisica incluida: ");
        pessoaFisica.exibir(); // Exibir os dados da pessoa física

        // Alterar os dados da pessoa física
        pessoaFisica.setNome("Fulano da Silva");
        pessoaFisicaDAO.alterar(pessoaFisica);

        // Consultar e listar todas as pessoas físicas
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
        for (PessoaFisica pf : pessoasFisicas) {
            pf.exibir();
        }

        // Excluir a pessoa física
        pessoaFisicaDAO.excluir(pessoaFisica.getId());
        System.out.println("\nPessoa Fisica excluida.");

        // Operações para Pessoa Jurídica
        PessoaJuridica pessoaJuridica = new PessoaJuridica(1, "Empresa XYZ", "Rua B", "Cidade B", "Estado B", "987654321", "empresa@xyz.com", "12345678901234");
        pessoaJuridicaDAO.incluir(pessoaJuridica);
        System.out.println("\nPessoa Juridica incluida: ");
        pessoaJuridica.exibir(); // Exibir os dados da pessoa jurídica

        // Alterar os dados da pessoa jurídica
        pessoaJuridica.setNome("Nova Empresa XYZ");
        pessoaJuridicaDAO.alterar(pessoaJuridica);

        // Consultar e listar todas as pessoas jurídicas
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
        for (PessoaJuridica pj : pessoasJuridicas) {
            pj.exibir();
        }

        // Excluir a pessoa jurídica
        pessoaJuridicaDAO.excluir(pessoaJuridica.getId());
        System.out.println("\nPessoa Juridica excluida.");
    }
}



        

