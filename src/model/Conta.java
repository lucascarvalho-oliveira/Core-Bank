package model;

import model.enums.TipoConta;

public abstract class Conta {
    private int idConta;
    protected double saldo;

    private Pessoa pessoa;

    public Conta(){}

    public Conta(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
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

    public abstract double getLimite();

}
