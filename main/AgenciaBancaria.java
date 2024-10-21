package main;

import java.util.ArrayList;
import java.util.Scanner;

import main.model.Conta;
import main.model.Pessoa;

public class AgenciaBancaria {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Conta> contasBancarias;

    public static void main(String[] args) {
        contasBancarias = new ArrayList<Conta>();
        operacoes();
    }

    public static void operacoes() {
        System.out.println("------------------------------------------------------");
        System.out.println("-------------Bem vindos a nossa Agência---------------");
        System.out.println("------------------------------------------------------");
        System.out.println("***** Selecione uma operação que deseja realizar *****");
        System.out.println("------------------------------------------------------");
        System.out.println("|   Opção 1 - Criar conta   |");
        System.out.println("|   Opção 2 - Depositar     |");
        System.out.println("|   Opção 3 - Sacar         |");
        System.out.println("|   Opção 4 - Transferir    |");
        System.out.println("|   Opção 5 - Listar        |");
        System.out.println("|   Opção 6 - Sair          |");

        int operacao = input.nextInt();

        switch (operacao) {
            case 1:
                criarConta();
                break;
            case 2:
                depositar();
                break;
            case 3:
                sacar();
                break;
            case 4:
                transferir();
                break;
            case 5:
                listarContas();
                break;
            case 6:
                System.out.println("ok!");
                System.exit(0);
            default:
                System.out.println("Opção inválida!");
                operacoes();
                break;
        }
    }

    public static void criarConta() {
        String nome;
        while (true) {
            System.out.println("\nNome: ");
            nome = input.next();
            
            // Validação do nome
            if (nome.matches("[a-zA-ZÀ-ÿ ]+")) {
                break; // Nome válido, sai do loop
            } else {
                System.out.println("O nome deve conter apenas letras e espaços. Tente novamente.");
            }
        }
    
        String cpf;
        while (true) {
            System.out.println("\nCPF: ");
            cpf = input.next();
            
            // Validação do cpf para aceitar apenas números
            if (cpf.matches("\\d+")) { // Verifica se o CPF contém apenas dígitos
                break; // CPF válido, sai do loop
            } else {
                System.out.println("O CPF deve conter apenas números. Tente novamente.");
            }
        }
    
        System.out.println("Email: ");
        String email = input.next();
    
        Pessoa cliente = new Pessoa(nome, cpf, email);
        Conta conta = new Conta(cliente);
        contasBancarias.add(conta);
        System.out.println("--- Sua conta foi criada com sucesso! ---");
    
        operacoes();
    }

    private static Conta encontrarConta(int numeroConta) {
        Conta conta = null;
        if (contasBancarias.size() > 0) {
            for (Conta contaa : contasBancarias) {
                if (contaa.getNumeroConta() == numeroConta) {
                    conta = contaa;
                }
            }
        }
        return conta;
    }

    public static void depositar() {
        System.out.println("Número da conta: ");
        int numeroConta = input.nextInt();
        Conta conta = encontrarConta(numeroConta);

        if (conta != null) {
            System.out.println("Qual valor deseja depositar? ");
            Double valorDeposito = input.nextDouble();

            // Criar uma thread para a operação
            Operacao operacao = new Operacao(conta, valorDeposito, "depositar");
            Thread thread = new Thread(operacao);
            thread.start();

            try {
                thread.join(); // Espera a thread terminar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("--- Conta não encontrada ---");
        }

        operacoes();
    }

    public static void sacar() {
        System.out.println("Número da conta: ");
        int numeroConta = input.nextInt();
        Conta conta = encontrarConta(numeroConta);

        if (conta != null) {
            System.out.println("Qual valor deseja sacar? ");
            Double valorSaque = input.nextDouble();

            // Criar uma thread para a operação
            Operacao operacao = new Operacao(conta, valorSaque, "sacar");
            Thread thread = new Thread(operacao);
            thread.start();

            try {
                thread.join(); // Espera a thread terminar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("--- Conta não encontrada ---");
        }

        operacoes();
    }

    public static void transferir() {
        System.out.println("Número da conta que vai enviar a transferência: ");
        int numeroContaRemetente = input.nextInt();

        Conta contaRemetente = encontrarConta(numeroContaRemetente);

        if (contaRemetente != null) {
            System.out.println("Número da conta do destinatário: ");
            int numeroContaDestinatario = input.nextInt();

            Conta contaDestinatario = encontrarConta(numeroContaDestinatario);

            if (contaDestinatario != null) {
                System.out.println("Valor da transferência: ");
                Double valor = input.nextDouble();
                contaRemetente.transferencia(contaDestinatario, valor);
            } else {
                System.out.println("--- A conta para depósito não foi encontrada ---");
            }

        } else {
            System.out.println("--- Conta para transferência não encontrada ---");
        }
        operacoes();
    }

    public static void listarContas() {
        if (contasBancarias.size() > 0) {
            for (Conta conta : contasBancarias) {
                System.out.println(conta);
            }
        } else {
            System.out.println("--- Não há contas cadastradas ---");
        }

        operacoes();
    }
}
