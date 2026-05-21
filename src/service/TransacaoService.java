package service;

import model.Transacao;
import repository.TransacaoRepository;

public class TransacaoService {
    private TransacaoRepository repositoryTransacao;

    public TransacaoService(TransacaoRepository repositoryTransacao){
        this.repositoryTransacao = repositoryTransacao;
    }

    public void salvarTransacao(Transacao transacao){
        if(transacao.getData_hora() == null){
            throw new IllegalArgumentException("ERRO! data hora incorreto.");
        }
        if(transacao.getValor() == 0){
            throw new IllegalArgumentException("ERRO! valor incorreto.");
        }

        repositoryTransacao.salvarTransacao(transacao);
    }
}
