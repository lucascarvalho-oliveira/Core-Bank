package model;

import model.enums.TipoConta;

import java.util.ArrayList;
import java.util.List;

public class ContaPoupanca extends Conta{
    List<Investimento> investimentos;

    public ContaPoupanca(){}

    public ContaPoupanca(Pessoa pessoa){
        super(pessoa);

        this.investimentos = new ArrayList<>();
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
