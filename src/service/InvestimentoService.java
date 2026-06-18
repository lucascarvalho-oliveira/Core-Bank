package service;


import model.Conta;
import model.Investimento;
import repository.InvestimentoRepository;

public class InvestimentoService {
    private InvestimentoRepository repositoryInveste;

    public InvestimentoService(InvestimentoRepository repositoryInveste){
        this.repositoryInveste = repositoryInveste;
    }

    public void SalvarInvestimento(Investimento investe, Conta conta){
        if(investe.getValor() > conta.getSaldo()){
            throw new IllegalArgumentException("Saldo em conta insuficiente.");
        }

        repositoryInveste.salvarInvestimento(investe);
    }
}
