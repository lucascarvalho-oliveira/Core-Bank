package model;

import model.enums.TipoConta;

public class ContaCorrente extends Conta{
    private double limite;

    public ContaCorrente(){}

    public ContaCorrente(double limite, Pessoa pessoa){
        super(pessoa);
        this.limite = limite;
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
            System.out.println("Saldo insuficiente para essa transação. O sistema utilizará o Limite do cheque especial disponível, aplicando uma taxa de 20% sobre o valor utilizado.");
            double sobra = saldo - valor;
            double valorUsadoLimite = Math.abs(sobra);
            double taxa = valorUsadoLimite * 0.2;

            saldo = sobra - taxa;
        }
    }

    @Override
    public double getLimite(){
        return limite;
    }
}
