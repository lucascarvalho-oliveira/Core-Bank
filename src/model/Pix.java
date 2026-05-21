package model;

import model.enums.TipoPix;

public class Pix {
    private int id_pix;
    private String chave;
    private TipoPix tipoPix;

    private Conta conta;

    public Pix(String chave, TipoPix tipoPix, Conta conta) {
        this.chave = chave;
        this.tipoPix = tipoPix;
        this.conta = conta;
    }

    public int getId_pix() {
        return id_pix;
    }

    public void setId_pix(int id_pix) {
        this.id_pix = id_pix;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public TipoPix getTipoPix() {
        return tipoPix;
    }

    public void setTipoPix(TipoPix tipoPix) {
        this.tipoPix = tipoPix;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
