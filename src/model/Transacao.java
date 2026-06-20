package model;

import model.enums.TipoTransacao;

import java.time.LocalDateTime;

public class Transacao {
    private int idTransacao;
    private LocalDateTime data_hora;
    private double valor;
    private double saldoConta;
    private TipoTransacao tipoTransacao;

    private Conta conta;

    public Transacao(){}

    public Transacao(LocalDateTime data_hora, double valor, double saldoConta, TipoTransacao tipoTransacao, Conta conta) {
        this.data_hora = data_hora;
        this.valor = valor;
        this.saldoConta = saldoConta;
        this.tipoTransacao = tipoTransacao;

        this.conta = conta;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public LocalDateTime getData_hora() {
        return data_hora;
    }

    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(double saldoConta) {
        this.saldoConta = saldoConta;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
