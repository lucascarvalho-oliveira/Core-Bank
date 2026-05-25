package service;

import model.Conta;
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
        Conta conta = new Conta();

        conta.setTipoConta(tipoConta);
        conta.setPessoa(pessoa);

        if(tipoConta == TipoConta.CONTA_CORRENTE){
            conta.setLimite(500);
        }

        repositoryConta.salvarConta(conta);

        return conta;
    }

    public void depositar(Conta conta, double valor){
            if(valor <= 0){
                throw new IllegalArgumentException("Valor incorreto para deposito");
            }

            conta.setSaldo(conta.getSaldo() + valor);

            repositoryConta.atualizarSaldo(conta);
    }

    public void sacar(Conta conta, double valor, boolean usarLimite){
        if(conta.getTipoConta() == TipoConta.CONTA_CORRENTE){
            if(valor <= 0){
                throw new IllegalArgumentException("Valor incorreto para sacar");
            }
            if(valor > conta.getSaldo() + conta.getLimite()){
                throw new IllegalArgumentException("Saldo insuficiente para essa transação");
            }

            if(usarLimite){
                double sobra = conta.getSaldo() - valor;
                double soma = sobra + conta.getLimite();

                double valorUsadoLimite = Math.abs(sobra);
                double taxa = valorUsadoLimite * 0.2;

                conta.setSaldo(sobra - taxa);
            }

            if(valor <= conta.getSaldo()){
                double sobra = conta.getSaldo() - valor;

                conta.setSaldo(sobra);
            }
        }
        if(conta.getTipoConta() == TipoConta.CONTA_POUPANCA){
            if(valor <= 0){
                throw new IllegalArgumentException("Valor incorreto para sacar");
            }
            if(valor > conta.getSaldo()){
                throw new IllegalArgumentException("Saldo insuficiente para essa transação");
            }

            double sobra = conta.getSaldo() - valor;

            conta.setSaldo(sobra);
        }
            repositoryConta.atualizarSaldo(conta);
    }

    public void transferir(Conta contaOrigem, double valor, boolean usarLimite, String chavePix){
        int idContaDestino = repositoryPix.buscarPix(chavePix);

        if(idContaDestino == -1){
            throw new IllegalArgumentException("Chave pix não encontrada");
        }

        Conta contaDestino = repositoryConta.buscarContaIdConta(idContaDestino);

        if(contaDestino == null){
            throw new IllegalArgumentException("Conta destino não encontrada");
        }

        sacar(contaOrigem, valor, usarLimite);

        depositar(contaDestino, valor);
    }
}
