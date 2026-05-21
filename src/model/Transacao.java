package model;

import model.enums.TipoTransacao;

import java.time.LocalDateTime;

public class Transacao {
    private int id_transacao;
    private LocalDateTime data_hora;
    private double valor;
    private TipoTransacao tipoTransacao;

    private Conta conta;

    public Transacao(int id_transacao, LocalDateTime data_hora, double valor, TipoTransacao tipoTransacao, Conta conta) {
        this.id_transacao = id_transacao;
        this.data_hora = data_hora;
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;

        this.conta = conta;
    }

    public int getId_transacao() {
        return id_transacao;
    }

    public void setId_transacao(int id_transacao) {
        this.id_transacao = id_transacao;
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
