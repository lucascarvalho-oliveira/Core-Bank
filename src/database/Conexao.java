package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    final private String URL =  "jdbc:mysql://localhost:3306/db_CoreBank";
    final private String USER = "root";
    final private String PASSWORD = "Carvalho@ptc50";

    public Connection conectar(){
        try{
            return DriverManager.getConnection(URL,USER,PASSWORD);

        }catch (SQLException e){
            throw new RuntimeException("Erro ao conectar com banco.", e);
        }
    }
}
