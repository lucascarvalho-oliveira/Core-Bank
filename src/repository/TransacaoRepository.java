package repository;

import database.Conexao;
import model.Transacao;

import java.sql.*;
import java.time.format.DateTimeFormatter;

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
            stmt.setInt(5, transacao.getConta().getId_conta());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    int idGerado = rs.getInt(1);
                    transacao.setId_transacao(idGerado);
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro ao salvar transação.\n");
            e.printStackTrace();
        }
    }

    public void extrato(int id_conta){
        String sql = "SELECT * FROM transacao WHERE id_conta_fk = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id_conta);

            ResultSet rs = stmt.executeQuery();

            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            boolean temDados = false;
            while(rs.next()){
                temDados = true;
                System.out.println("\n=======================================================================");
                System.out.println("Data e Hora: " + rs.getTimestamp("data_hora").toLocalDateTime().format(formatador));
                System.out.println("Tipo: " + rs.getString("tipo_transacao"));
                System.out.println("Valor: R$ " +String.format("%.2f", rs.getDouble("valor")));
                System.out.println("Saldo em conta: R$ " + String.format("%.2f", rs.getDouble("saldo_conta")));
            }

            if(!temDados){
                System.out.println("Nenhum histórico encontrado para essa conta.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
