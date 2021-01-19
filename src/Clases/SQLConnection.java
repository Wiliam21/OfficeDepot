/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;

/**
 *
 * @author apple
 */
public class SQLConnection {
    Connection conectar=null;
    public Connection conexion()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar=DriverManager.getConnection("jdbc:mysql://localhost/OfficeDepot?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");
            if(conectar.isValid(0)){
                System.out.println("Conectado a la base de datos");
            }
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("No se pudo conectar");
            System.out.println(e.getMessage());
        }
        return conectar;
    }
}
