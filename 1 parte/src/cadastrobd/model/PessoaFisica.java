/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

/**
 *
 * @author Nanda
 */

public class PessoaFisica extends Pessoa {
    private String cpf;

    public PessoaFisica() {
        super();
    }

    public PessoaFisica(int idPessoaFisica, String nome, String logradouro, String cidade, String estado, String telefone, String email, String cpf) {
        super(idPessoaFisica, nome, logradouro, cidade, estado, telefone, email);
        this.cpf = cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }    
    public String getCpf() {
        return cpf;
    }
    
    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CPF: " + cpf);
    }    
}