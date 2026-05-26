package model;

import model.enums.TipoConta;

public class ContaCorrente extends Conta{
    private double limite;

    public ContaCorrente(){}

    public ContaCorrente(double limite, Pessoa pessoa){
        super(pessoa);
        this.limite = 500;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    @Override
    public TipoConta getTipoConta(){
        return TipoConta.CONTA_CORRENTE;
    }

    @Override
    public void sacar(double valor){
        if(valor <= 0){
            throw new IllegalArgumentException("valor incorreto para saque.");
        }

        double valorLimite = saldo + limite;

        if(valor > valorLimite){
            throw new IllegalArgumentException("Saldo insuficiente para essa transação.");
        }
        if(valor <= saldo) {
            saldo -= valor;
        }else{
            double sobra = saldo - valor;
            double valorUsadoLimite = Math.abs(sobra);
            double taxa = valorUsadoLimite * 0.2;

            saldo = sobra - taxa;
        }
    }
}
