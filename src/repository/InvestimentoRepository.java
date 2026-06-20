package repository;

import database.Conexao;
import model.Conta;
import model.Investimento;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoRepository {

    public void salvarInvestimento(Investimento investimento){
        String sql = "INSERT INTO investimento (valor, data_hora, id_conta_fk) VALUES (?, ?, ?)";

        try(Connection conn  = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setDouble(1, investimento.getValor());
            stmt.setTimestamp(2, Timestamp.valueOf(investimento.getDataAplicacao()));
            stmt.setInt(3, investimento.getConta().getIdConta());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    int idGerado = rs.getInt(1);
                    investimento.setIdInvestimento(idGerado);
                    System.out.println("Investimento feito com sucesso.");
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro! ao fazer investimento.\n" + e.getMessage());
        }
    }

    public void consultarInveste(Conta conta){
        String sql = "SELECT * FROM investimento WHERE id_conta_fk = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, conta.getIdConta());

            try(ResultSet rs = stmt.executeQuery()){
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                boolean temDados = false;
                while(rs.next()){
                    temDados = true;
                    System.out.println("\n=======================================================================");
                    System.out.println("Valor: R$ " + rs.getDouble("valor"));
                    System.out.println("Data e hora do investimento: " + rs.getTimestamp("data_hora").toLocalDateTime().format(formatador));
                }
                if(!temDados){
                    System.out.println("Nenhum histórico de investimento encontrado para essa conta.");
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro! ao fazer investimento.\n" + e.getMessage());
        }
    }

    public List<Investimento> buscarInvestimento(Conta conta){
        String sql = "SELECET * FROM investimento WHERE id_conta_fk = ?";

        List<Investimento> investimentos = new ArrayList<>();

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, conta.getIdConta());

            try(ResultSet rs = stmt.executeQuery()){

                boolean temDados = false;
                while (rs.next()){
                    Investimento investe = new Investimento();

                    investe.setIdInvestimento(rs.getInt("id_investimento"));
                    investe.setValor(rs.getDouble("valor"));
                    investe.setDataAplicacao(rs.getTimestamp("data_hora").toLocalDateTime());

                    investimentos.add(investe);
                }

            }
        }catch (SQLException e){
            System.out.println("\nErro! ao fazer investimento.\n" + e.getMessage());
        }
        return investimentos;
    }

    public void atualizarValor(double novoValor, int id_investimento){
        String sql = "UPDATE investimento SET valor = ? WHERE id_investimento = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setDouble(1, novoValor);
            stmt.setInt(2, id_investimento);

            int linhaAfetada = stmt.executeUpdate();

            if(linhaAfetada > 0){
                System.out.println("\nValor atualizado com sucesso.");
            }

        }catch (SQLException e){
            System.out.println("\nErro! ao atualizar valor.\n" + e.getMessage());
        }
    }

    public void deletarInvest(int id_investimento){
        String sql = "DELETE FROM investimento WHERE id_investimento = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id_investimento);

            int linhaAfetada = stmt.executeUpdate();

            if(linhaAfetada > 0){
                System.out.println("\nInvestimento deletado com sucesso.");
            }

        }catch (SQLException e){
            System.out.println("\nErro! ao deletar investimento.\n" + e.getMessage());
        }
    }
}
