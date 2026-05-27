package model;

import model.enums.TipoConta;

public class ContaPoupanca extends Conta{

    public ContaPoupanca(){}

    public ContaPoupanca(Pessoa pessoa){
        super(pessoa);
    }

    @Override
    public TipoConta getTipoConta(){
        return TipoConta.CONTA_POUPANCA;
    }

    @Override
    public void sacar(double valor){
        if(valor <= 0){
            throw new IllegalArgumentException("valor incorreto para saque.");
        }
        if(valor > saldo){
            throw new IllegalArgumentException("Saldo insuficiente para essa transação.");
        }

        saldo -= valor;
    }

    @Override
    public double getLimite(){
        return 0;
    }
}
