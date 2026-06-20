package model;

import java.time.LocalDateTime;

public class Investimento {
    private int idInvestimento;
    private double valor;
    private LocalDateTime dataAplicacao;

    private Conta conta;

    public Investimento() {}

    public Investimento(double valor, LocalDateTime dataAplicacao, Conta conta) {
        this.valor = valor;
        this.dataAplicacao = dataAplicacao;
        this.conta = conta;
    }

    public int getIdInvestimento() {
        return idInvestimento;
    }

    public void setIdInvestimento(int idInvestimento) {
        this.idInvestimento = idInvestimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
