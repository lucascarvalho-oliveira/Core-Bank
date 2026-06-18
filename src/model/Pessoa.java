package model;

import model.enums.TipoPessoa;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
    private int idPessoa;
    private String nome;
    private TipoPessoa tipoPessoa;
    private String documento;
    private String senha;

    private List<Conta> conta;

    public Pessoa(){}

    public Pessoa(String nome, TipoPessoa tipoPessoa, String documento, String senha) {
        this.nome = nome;
        this.tipoPessoa = tipoPessoa;
        this.documento = documento;
        this.senha = senha;

        this.conta = new ArrayList<>();
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public List<Conta> getConta() {
        return conta;
    }

    public void setConta(List<Conta> conta) {
        this.conta = conta;
    }
}
