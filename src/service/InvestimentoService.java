package service;


import model.Conta;
import model.Investimento;
import repository.InvestimentoRepository;

import java.time.LocalDateTime;

public class InvestimentoService {
    private InvestimentoRepository repositoryInveste;
    private ContaService serviceConta;

    public InvestimentoService(InvestimentoRepository repositoryInveste, ContaService serviceConta){
        this.repositoryInveste = repositoryInveste;
        this.serviceConta = serviceConta;
    }

    public void SalvarInvestimento(double valorInveste, Conta conta){
        if(valorInveste > conta.getSaldo()){
            throw new IllegalArgumentException("Saldo em conta insuficiente.");
        }

        Investimento investimento = new Investimento(valorInveste, LocalDateTime.now(), conta);

        serviceConta.sacar(conta, valorInveste);

        repositoryInveste.salvarInvestimento(investimento);
    }
}
