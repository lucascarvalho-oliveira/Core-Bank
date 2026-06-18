package model;

import model.enums.TipoPix;

public class Pix {
    private int idPix;
    private TipoPix tipoPix;
    private String chave;

    private Conta conta;

    public Pix(TipoPix tipoPix, String chave, Conta conta) {
        this.chave = chave;
        this.tipoPix = tipoPix;

        this.conta = conta;
    }

    public int getIdPix() {
        return idPix;
    }

    public void setIdPix(int idPix) {
        this.idPix = idPix;
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
