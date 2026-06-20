package repository;

import database.Conexao;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.enums.TipoConta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaRepository {
    public void salvarConta(Conta conta){
        String sql = "INSERT INTO conta(saldo, limite, tipo_conta, id_pessoa_fk) VALUES(?, ?, ?, ?)";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setDouble(1, conta.getSaldo());
            stmt.setDouble(2, conta.getLimite());
            stmt.setString(3, conta.getTipoConta().name());
            stmt.setInt(4, conta.getPessoa().getIdPessoa());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    int idGerado = rs.getInt(1);
                    conta.setIdConta(idGerado);
                    System.out.println("\nConta gerada com sucesso.\n");
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro! ao salvar conta.\n" + e.getMessage());
        }
    }

    public void atualizarSaldo(Conta conta){
        String sql = "UPDATE conta SET saldo = ? WHERE id_conta = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setDouble(1, conta.getSaldo());
            stmt.setInt(2, conta.getIdConta());

            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("\nERRO! ao atualizar o saldo.\n" + e.getMessage());
        }
    }

    public List<Conta> buscarContaIdPessoa(int id_pessoa){
        String sql = "SELECT * FROM conta WHERE id_pessoa_fk = ?";

        List<Conta> contas = new ArrayList<>();

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, id_pessoa);

            try(ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String tipoContaBanco = rs.getString("tipo_conta");
                    TipoConta tipoConta = TipoConta.valueOf(tipoContaBanco);

                    Conta conta;

                    if (tipoConta == TipoConta.CONTA_CORRENTE) {
                        ContaCorrente cc = new ContaCorrente();
                        cc.setLimite(rs.getDouble("limite"));
                        conta = cc;
                    } else {
                        ContaPoupanca cp = new ContaPoupanca();
                        conta = cp;
                    }

                    conta.setIdConta(rs.getInt("id_conta"));
                    conta.setSaldo(rs.getDouble("saldo"));

                    contas.add(conta);
                }
            }
        }catch (SQLException e){
            System.out.println("\nERRO! ao encontrar pessoa.\n" + e.getMessage());
        }
        return contas;
    }

    public Conta buscarContaIdConta(int id_conta){
        String sql = "SELECT * FROM conta WHERE id_conta = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, id_conta);

            try(ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String TipoContaBanco = rs.getString("tipo_conta");
                    TipoConta tipoConta = TipoConta.valueOf(TipoContaBanco);

                    Conta conta;

                    if (tipoConta == TipoConta.CONTA_CORRENTE) {
                        ContaCorrente cc = new ContaCorrente();
                        cc.setLimite(rs.getDouble("limite"));
                        conta = cc;
                    } else {
                        ContaPoupanca cp = new ContaPoupanca();
                        conta = cp;
                    }

                    conta.setIdConta(rs.getInt("id_conta"));
                    conta.setSaldo(rs.getDouble("saldo"));

                    return conta;
                }
            }
        }catch (SQLException e){
            System.out.println("\nERRO! conta nao encontrada.\n" + e.getMessage());
        }
        return null;
    }

    public void apagarConta(Conta conta){
        String sql = "DELETE FROM conta WHERE id_conta = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, conta.getIdConta());

            int linhaAfetada = stmt.executeUpdate();

            if(linhaAfetada > 0){
                System.out.println("\nConta deletada com sucesso.");
            }

        }catch (SQLException e){
            System.out.println("\nERRO! ao deletar conta.\n" + e.getMessage());
        }
    }
}
