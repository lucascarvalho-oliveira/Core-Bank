package model;

import model.enums.TipoConta;

public class Conta {
    private int id_conta;
    private double saldo;
    private double limite;
    private TipoConta tipoConta;

    private Pessoa pessoa;

    public Conta(){}

    public Conta(double saldo, TipoConta tipoConta, Pessoa pessoa) {
        this.saldo = saldo;
        this.tipoConta = tipoConta;

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

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id_conta=" + id_conta +
                ", saldo=" + saldo +
                ", tipoConta=" + tipoConta +
                ", limite=" + limite +
                ", pessoa=" + pessoa +
                '}';
    }
}
