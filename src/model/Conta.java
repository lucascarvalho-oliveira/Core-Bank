package model;

import model.enums.TipoConta;

public abstract class Conta {
    private int id_conta;
    protected double saldo;

    protected Pessoa pessoa;

    public Conta(){}

    public Conta(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public int getId_conta() {
        return id_conta;
    }

    public void setId_conta(int id_conta) {
        this.id_conta = id_conta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public abstract TipoConta getTipoConta();

    public abstract void sacar(double valor);

}
