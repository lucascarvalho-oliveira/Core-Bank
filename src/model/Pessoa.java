package model;

import model.enums.TipoPessoa;

import java.util.List;

public class Pessoa {
    private int id_pessoa;
    private String nome;
    private String documento;
    private String senha;
    private TipoPessoa tipoPessoa;

    private List<Conta> conta;

    public Pessoa(){}

    public Pessoa(String nome, String documento, String senha, TipoPessoa tipoPessoa, List<Conta> conta) {
        this.nome = nome;
        this.documento = documento;
        this.senha = senha;
        this.tipoPessoa = tipoPessoa;
        this.conta = conta;
    }

    public int getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
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
