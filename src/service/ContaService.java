package service;

import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.Pessoa;
import model.enums.TipoConta;
import repository.ContaRepository;
import repository.PixRepository;

public class ContaService {
    private ContaRepository repositoryConta;
    private PixRepository repositoryPix;
    private Pessoa pessoa;

    public ContaService(ContaRepository repositoryConta, PixRepository repositoryPix, Pessoa pessoa){
        this.repositoryConta = repositoryConta;
        this.repositoryPix = repositoryPix;
        this.pessoa = pessoa;
    }

    public Conta criarConta(TipoConta tipoConta){
        Conta conta;

        if(tipoConta == TipoConta.CONTA_CORRENTE){
            ContaCorrente cc = new ContaCorrente();
            conta = cc;
        }else{
            ContaPoupanca cp = new ContaPoupanca();
            conta = cp;
        }

        repositoryConta.salvarConta(conta);

        return conta;
    }

    public void depositar(Conta conta, double valor){
            if(valor <= 0){
                throw new IllegalArgumentException("Valor incorreto para deposito.");
            }

            conta.setSaldo(conta.getSaldo() + valor);

            repositoryConta.atualizarSaldo(conta);
    }

    public void sacar(Conta conta, double valor){
        conta.sacar(valor);
        repositoryConta.atualizarSaldo(conta);
    }

    public void transferir(Conta contaOrigem, double valor, String chavePix){
        int idContaDestino = repositoryPix.buscarPix(chavePix);

        if(idContaDestino == -1){
            throw new IllegalArgumentException("Chave pix não encontrada.");
        }

        Conta contaDestino = repositoryConta.buscarContaIdConta(idContaDestino);

        if(contaDestino == null){
            throw new IllegalArgumentException("Conta destino não encontrada.");
        }

        sacar(contaOrigem, valor);

        depositar(contaDestino, valor);
    }
}
