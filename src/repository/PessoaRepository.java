package repository;

import database.Conexao;
import model.Pessoa;

import java.sql.*;

public class PessoaRepository {
    public void salvarPessoa(Pessoa pessoa){
        String sql = "INSERT INTO pessoa(nome, documento, tipo_pessoa, senha) VALUES(?, ?, ?, ?)";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getDocumento());
            stmt.setString(3, pessoa.getTipoPessoa().name());
            stmt.setString(4, pessoa.getSenha());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    int idGerado = rs.getInt(1);
                    pessoa.setIdPessoa(idGerado);
                    System.out.println("\nUsuário gerado com sucesso.\n");
                }
            }
        }catch (SQLException e){
            System.out.println("\nErro! ao salvar conta.\n" + e.getMessage());
        }
    }

    public Pessoa buscarPessoa(String documento){
        String sql = "SELECT * FROM pessoa WHERE documento = ?";

        try(Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setString(1, documento);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Pessoa pessoa = new Pessoa();

                pessoa.setIdPessoa(rs.getInt("id_pessoa"));
                pessoa.setSenha(rs.getString("senha"));

                return  pessoa;
            }
        }catch (SQLException e){
            System.out.println("\nERRO! pessoa nao encontrada.\n" + e.getMessage());
        }
        return null;
    }
}
