package repository;

import database.Conexao;
import model.Investimento;

import java.sql.*;

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
}
