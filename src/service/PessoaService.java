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

    public PessoaService(PessoaRepository repositoryPessoa, Senha senha){
        this.repositoryPessoa = repositoryPessoa;
        this.senha = senha;
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
            CPF cpf = new CPF(pessoa.getDocumento());
        }
        if(pessoa.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA){
            CNPJ cnpj = new CNPJ(pessoa.getDocumento());
        }

        repositoryPessoa.salvarPessoa(pessoa);
    }
}
