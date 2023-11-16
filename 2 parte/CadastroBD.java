/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Nanda
 */

public class CadastroBD {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        try {
            int opcao;
            do {
                System.out.println("\n===== Menu =====");
                System.out.println("1 - Incluir");
                System.out.println("2 - Alterar");
                System.out.println("3 - Excluir");
                System.out.println("4 - Exibir pelo ID");
                System.out.println("5 - Exibir Todos");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opcao: ");

                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        incluirOpcao(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 2:
                        alterarOpcao(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 3:
                        excluirOpcao(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 4:
                        exibirPorIdOpcao(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 5:
                        exibirTodosOpcao(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 0:
                        System.out.println("Ate logo!");
                        break;
                    default:
                        System.out.println("Opcao invalida. Tente novamente.");
                }
            } while (opcao != 0);
        } catch (InputMismatchException e) {
            System.out.println("Entrada invalida. Certifique-se de inserir um numero do Menu.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante a execucao do programa: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void incluirOpcao(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.print("Escolha o tipo (1 - Pessoa Fisica, 2 - Pessoa Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            PessoaFisica pessoaFisica = obterDadosPessoaFisica(scanner);
            pessoaFisicaDAO.incluir(pessoaFisica);
            System.out.println("Pessoa Fisica incluida com sucesso!");
        } else if (tipo == 2) {
            PessoaJuridica pessoaJuridica = obterDadosPessoaJuridica(scanner);
            pessoaJuridicaDAO.incluir(pessoaJuridica);
            System.out.println("Pessoa Juridica incluida com sucesso!");
        } else {
            System.out.println("Opcao invalida. Tente novamente.");
        }
    }

    private static void alterarOpcao(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.print("Escolha o tipo (1 - Pessoa Fisica, 2 - Pessoa Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o ID da pessoa que deseja alterar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
            if (pessoaFisica != null) {
                System.out.println("Dados atuais da Pessoa Fisica:");
                pessoaFisica.exibir();

                System.out.println("Digite os novos dados:");
                PessoaFisica novosDados = obterDadosPessoaFisica(scanner);
                novosDados.setId(id);
                pessoaFisicaDAO.alterar(novosDados);
                System.out.println("Pessoa Fisica alterada com sucesso!");
            } else {
                System.out.println("Pessoa Fisica nao encontrada.");
            }
        } else if (tipo == 2) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
            if (pessoaJuridica != null) {
                System.out.println("Dados atuais da Pessoa Juridica:");
                pessoaJuridica.exibir();

                System.out.println("Digite os novos dados:");
                PessoaJuridica novosDados = obterDadosPessoaJuridica(scanner);
                novosDados.setId(id);
                pessoaJuridicaDAO.alterar(novosDados);
                System.out.println("Pessoa Juridica alterada com sucesso!");
            } else {
                System.out.println("Pessoa Juridica nao encontrada.");
            }
        } else {
            System.out.println("Opcao invalida. Tente novamente.");
        }
    }

    private static void excluirOpcao(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.print("Escolha o tipo (1 - Pessoa Fisica, 2 - Pessoa Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o ID da pessoa que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            pessoaFisicaDAO.excluir(id);
            System.out.println("Pessoa Fisica excluida com sucesso!");
        } else if (tipo == 2) {
            pessoaJuridicaDAO.excluir(id);
            System.out.println("Pessoa Juridica excluida com sucesso!");
        } else {
            System.out.println("Opcao invalida. Tente novamente.");
        }
    }

    private static void exibirPorIdOpcao(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.print("Escolha o tipo (1 - Pessoa Fisica, 2 - Pessoa Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o ID da pessoa que deseja exibir: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
            if (pessoaFisica != null) {
                System.out.println("Dados da Pessoa Fisica:");
                pessoaFisica.exibir();
            } else {
                System.out.println("Pessoa Fisica nao encontrada.");
            }
        } else if (tipo == 2) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
            if (pessoaJuridica != null) {
                System.out.println("Dados da Pessoa Juridica:");
                pessoaJuridica.exibir();
            } else {
                System.out.println("Pessoa Juridica nao encontrada.");
            }
        } else {
            System.out.println("Opcao invalida. Tente novamente.");
        }
    }

    private static void exibirTodosOpcao(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.print("Escolha o tipo (1 - Pessoa Fisica, 2 - Pessoa Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
            System.out.println("\nTodas as Pessoas Fisicas no banco:");
            for (PessoaFisica pf : pessoasFisicas) {
                pf.exibir();
            }
        } else if (tipo == 2) {
            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
            System.out.println("\nTodas as Pessoas Juridicas no banco:");
            for (PessoaJuridica pj : pessoasJuridicas) {
                pj.exibir();
            }
        } else {
            System.out.println("Opcao invalida. Tente novamente.");
        }
    }

    private static PessoaFisica obterDadosPessoaFisica(Scanner scanner) {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite o e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        return new PessoaFisica(0, nome, logradouro, cidade, estado, telefone, email, cpf);
    }

    private static PessoaJuridica obterDadosPessoaJuridica(Scanner scanner) {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite o e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Digite o CNPJ: ");
        String cnpj = scanner.nextLine();

        return new PessoaJuridica(0, nome, logradouro, cidade, estado, telefone, email, cnpj);
    }
}