package repository;

import database.Conexao;
import model.Conta;
import model.Transacao;
import model.enums.TipoTransacao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransacaoRepository {

    public void salvarTransacao(Transacao transacao){
        String sql = "INSERT INTO transacao(data_hora, valor, saldo_conta, tipo_transacao, id_conta_fk) VALUES(?, ?, ?, ?, ?)";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setTimestamp(1, Timestamp.valueOf(transacao.getData_hora()));
            stmt.setDouble(2, transacao.getValor());
            stmt.setDouble(3, transacao.getConta().getSaldo());
            stmt.setString(4, transacao.getTipoTransacao().name());
            stmt.setInt(5, transacao.getConta().getIdConta());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    int idGerado = rs.getInt(1);
                    transacao.setIdTransacao(idGerado);
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro! ao salvar transação.\n" + e.getMessage());
        }
    }

    public void extrato(int id_conta){
        String sql = "SELECT * FROM transacao WHERE id_conta_fk = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id_conta);

            try(ResultSet rs = stmt.executeQuery()) {
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                boolean temDados = false;
                while (rs.next()) {
                    temDados = true;
                    System.out.println("\n=======================================================================");
                    System.out.println("Data e Hora: " + rs.getTimestamp("data_hora").toLocalDateTime().format(formatador));
                    System.out.println("Tipo: " + rs.getString("tipo_transacao"));
                    System.out.println("Valor: R$ " + String.format("%.2f", rs.getDouble("valor")));
                    System.out.println("Saldo em conta: R$ " + String.format("%.2f", rs.getDouble("saldo_conta")));
                }
                if(!temDados){
                    System.out.println("Nenhum histórico encontrado para essa conta.");
                }
            }
        }catch (SQLException e){
            System.out.println("\nERRO! no extrato.\n" + e.getMessage());
        }
    }

    public List<Transacao> buscarTransacao(int id_conta) {
        String sql = "SELECT * FROM transacao WHERE id_conta_fk = ?";

        List<Transacao> transacaos = new ArrayList<>();

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id_conta);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Transacao transacao = new Transacao();

                    transacao.setIdTransacao(rs.getInt("id_transacao"));
                    transacao.setData_hora(rs.getTimestamp("data_hora").toLocalDateTime());
                    transacao.setTipoTransacao(TipoTransacao.valueOf(rs.getString("tipo_transacao")));

                    transacaos.add(transacao);
                }
            }
        }catch (SQLException e){
            System.out.println("\nERRO! no extrato.\n" + e.getMessage());
        }
        return transacaos;
    }

    public void updateData(LocalDateTime dataNova, int id_Transacao){
        String sql = "UPDATE transacao SET data_hora = ? WHERE id_Transacao = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setTimestamp(1, Timestamp.valueOf(dataNova));
            stmt.setInt(2, id_Transacao);

            int linhaAfetada = stmt.executeUpdate();

            if(linhaAfetada > 0){
                System.out.println("\nData e hora atualizada com sucesso");
            }

        }catch (SQLException e){
            System.out.println("\nERRO! ao atualizar data.\n" + e.getMessage());
        }
    }

    public void apagarTransacao(int id_conta){
        String sql = "DELETE FROM transacao WHERE id_conta = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id_conta);

            int linhaAfetada = stmt.executeUpdate();

            if(linhaAfetada > 0){
                System.out.println("\nHistórico deletado com sucesso.");
            }

        }catch (SQLException e){
            System.out.println("\nERRO! ao deletar histórico.\n" + e.getMessage());
        }
    }
}
