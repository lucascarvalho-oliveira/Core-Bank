package repository;

import database.Conexao;
import model.Pix;

import java.sql.*;

public class PixRepository {
    public void salvarPix(Pix pix){
        String sql = "INSERT INTO pix(chave, tipo_pix, id_conta_fk) VALUES(?, ?, ?)";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setString(1, pix.getChave());
            stmt.setString(2, pix.getTipoPix().name());
            stmt.setInt(3, pix.getConta().getIdConta());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    int idGerado = rs.getInt(1);
                    pix.setIdPix(idGerado);
                    System.out.println("Pix gerado com sucesso.");
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro! ao salvar conta.\n" + e.getMessage());
        }
    }

    public int buscarPix(String chavePix){
        String sql = "SELECT id_conta_fk FROM pix WHERE chave = ?";
        int idGerado = -1;

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setString(1, chavePix);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                idGerado = rs.getInt("id_conta_fk");
            }
        }catch (SQLException e){
            System.out.println("\nErro! ao buscar pix.\n" + e.getMessage());
        }

        return idGerado;
    }

    public void apagarPix(String chavePix){
        String sql = "DELETE FROM pix WHERE chave = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setString(1, chavePix);

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Pix apagado com sucesso.");
            }
        }catch (SQLException e){
            System.out.println("\nERRO! ao apagar pix.\n" + e.getMessage());
        }
    }
}
