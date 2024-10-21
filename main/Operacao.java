package main;

import main.model.Conta;

public class Operacao implements Runnable {
    private Conta conta;
    private double valor;
    private String tipoOperacao;

    public Operacao(Conta conta, double valor, String tipoOperacao) {
        this.conta = conta;
        this.valor = valor;
        this.tipoOperacao = tipoOperacao;
    }

    @Override
    public void run() {
        switch (tipoOperacao) {
            case "depositar":
                conta.depositar(valor);
                break;
            case "sacar":
                conta.sacar(valor);
                break;
            default:
                System.out.println("Operação inválida.");
                break;
        }
    }
}
