package com.example.session_cepe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    static String user = "root";
    static String password = "!fanomezantsoa88";
    static String url = "jbc:mysql://localhost/cepe";
    static  String driver = "com.mysql.cj.jbc.Driver";

    public static Connection getCon() throws ClassNotFoundException {
        Connection con = null;
        try{
            Class.forName(driver);
            try{
                con  = DriverManager.getConnection(url,user,password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        return con;
    }

}