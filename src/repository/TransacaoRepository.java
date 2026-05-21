package repository;

import database.Conexao;
import model.Transacao;

import java.sql.*;

public class TransacaoRepository {

    public void salvarTransacao(Transacao transacao){
        String sql = "INSERT INTO transacao(data_hora, valor, tipo_transacao) VALUES(?, ?, ?)";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setTimestamp(1, Timestamp.valueOf(transacao.getData_hora()));
            stmt.setDouble(2, transacao.getValor());
            stmt.setString(3, transacao.getTipoTransacao().name());

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

    public void extrato(int id_conta){}
}
