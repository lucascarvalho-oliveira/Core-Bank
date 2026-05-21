package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    final private String URL =  ;
    final private String USER = ;
    final private String PASSWORD = ;

    public Connection conectar(){
        try{
            return DriverManager.getConnection(URL,USER,PASSWORD);

        }catch (SQLException e){
            throw new RuntimeException("Erro ao conectar com banco.", e);
        }
    }
}
