package service;

import model.CNPJ;
import model.CPF;
import model.Pessoa;
import model.Senha;
import model.enums.TipoPessoa;
import repository.PessoaRepository;

public class PessoaService {
    private PessoaRepository repositoryPessoa;
    private Senha senha;
    private CPF cpf;
    private CNPJ cnpj;

    public PessoaService(PessoaRepository repositoryPessoa, Senha senha, CPF cpf, CNPJ cnpj){
        this.repositoryPessoa = repositoryPessoa;
        this.senha = senha;
        this.cpf = cpf;
        this.cnpj = cnpj;
    }

    public void salvarPessoa(Pessoa pessoa){
        if(pessoa.getNome() == null || pessoa.getNome().isBlank()){
            throw new IllegalArgumentException("Nome incorreto!");
        }
        if(pessoa.getDocumento() == null || pessoa.getDocumento().isBlank()){
            throw new IllegalArgumentException("Documento incorreto!");
        }

        if(pessoa.getSenha() != null){
            senha.confirmaSenha(pessoa.getSenha());
        }else{
            throw new IllegalArgumentException("Senha é obrigatória!");
        }

        if(pessoa.getTipoPessoa() == TipoPessoa.PESSOA_FISiCA){
            cpf.confirmaCpf(pessoa.getDocumento());
        }
        if(pessoa.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA){
            cnpj.confirmaCnpj(pessoa.getDocumento());
        }

        repositoryPessoa.salvarPessoa(pessoa);
    }

    public void atualizarSenha(Pessoa pessoa, String senhaDigitada, String documento, String senhaNova){
        if (!senha.validarSenha(pessoa, senhaDigitada)) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        try {
            senha.confirmaSenha(senhaNova);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        repositoryPessoa.atualizarSenha(documento, senhaNova);
    }

    public void apagarPessoa(String documento){
        repositoryPessoa.apagarPessoa(documento);
    }
}
