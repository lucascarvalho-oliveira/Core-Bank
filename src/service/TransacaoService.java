package service;

import model.Transacao;
import repository.TransacaoRepository;

import java.time.LocalDateTime;

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

    public void updateData(LocalDateTime dataNova, int idTransacao){
        repositoryTransacao.updateData(dataNova, idTransacao);
    }

    public void apagarTransacao(int idConta){
        repositoryTransacao.apagarTransacao(idConta);
    }
}
