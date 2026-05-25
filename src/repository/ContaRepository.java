package repository;

import database.Conexao;
import model.Conta;
import model.Pessoa;
import model.enums.TipoConta;

import java.sql.*;

public class ContaRepository {
    public void salvarConta(Conta conta){
        String sql = "INSERT INTO conta(saldo, limite, tipo_conta, id_pessoa_fk) VALUES(?, ?, ?, ?)";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setDouble(1, conta.getSaldo());
            stmt.setDouble(2, conta.getLimite());
            stmt.setString(3, conta.getTipoConta().name());
            stmt.setInt(4, conta.getPessoa().getId_pessoa());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    int idGerado = rs.getInt(1);
                    conta.setId_conta(idGerado);
                    System.out.println("\nConta gerada com sucesso\n");
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro ao salvar conta.\n");
            e.printStackTrace();
        }
    }

    public void atualizarSaldo(Conta conta){
        String sql = "UPDATE conta SET saldo = ? WHERE id_conta = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setDouble(1, conta.getSaldo());
            stmt.setInt(2, conta.getId_conta());

            stmt.executeUpdate();
            System.out.println("\nSaldo atualizado!");
        }catch (SQLException e){
            System.out.println("\nERRO! ao atualizar o saldo!\n");
        }
    }

    public Conta buscarContaIdPessoa(int id_pessoa){
        String sql = "SELECT * FROM conta WHERE id_pessoa_fk = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, id_pessoa);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Conta conta = new Conta();
                conta.setId_conta(rs.getInt("id_conta"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
                conta.setLimite(rs.getDouble("limite"));

                return conta;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Conta buscarContaIdConta(int id_conta){
        String sql = "SELECT * FROM conta WHERE id_conta = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, id_conta);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Conta conta = new Conta();
                conta.setId_conta(rs.getInt("id_conta"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
                conta.setLimite(rs.getDouble("limite"));

                return conta;
            }
        }catch (SQLException e){
            System.out.println("Conta nao encontrada!");
            e.printStackTrace();
        }
        return null;
    }
}
