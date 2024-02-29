package org.example.clases;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class DbHelper {
    //conexion con bd******************
    private static Connection c;
    private static Statement s;
    private String db;
    private String host;
    private String port;
    private static String urlConnection;
    private static String user;
    private static String password;

    public DbHelper() throws SQLException {
        this.s = null;
        this.db = "bar2";
        this.host = "127.0.0.1";
        this.port = "3306";
        this.urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + db;
        this.user = "Usuario1";
        this.password = "password";
        this.c = DriverManager.getConnection(urlConnection, user, password);
    }

    public Connection getC() {
        return c;
    }

    public void setC(Connection c) {
        this.c = c;
    }

    public Statement getS() {
        return s;
    }

    public void setS(Statement s) {
        this.s = s;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrlConnection() {
        return urlConnection;
    }

    public void setUrlConnection(String urlConnection) {
        this.urlConnection = urlConnection;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void showSQLError(SQLException e) {
        System.err.println("SQL error message: " + e.getMessage());
        System.err.println("SQL state: " + e.getSQLState());
        System.err.println("SQL error code: " + e.getErrorCode());
    }

    public static void close(Connection c) {
        try {
            if(c!=null){
                c.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //conexion con bd******************

    public void addFactura(Factura factura) {
        try {
            c = DriverManager.getConnection(urlConnection, user, password);
            s = c.createStatement();
            PreparedStatement s = c.prepareStatement("insert into factura (id,mesa) values (?,?);");
            c.setAutoCommit(false);
            s.setString(1, factura.getId());
            s.setInt(2, factura.getMesa());
            s.executeUpdate();
            c.commit();
            s.close();
            c.close();
            System.out.println("Factura añadida");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(c);
        }
    }
    public void addPedido(int mesa,String factura, int producto, int cantidad) {
        int idFactura = 0;
        try {
            c = DriverManager.getConnection(urlConnection, user, password);
            s = c.createStatement();
            PreparedStatement s = c.prepareStatement("insert into pedidos(mesa, factura, producto, cantidad, fecha) values\n" +
                    "(?,?,?,?,now());");
            c.setAutoCommit(false);
            s.setInt(1, mesa);
            s.setString(2, factura);
            s.setInt(3, producto);
            s.setInt(4,cantidad);
            s.executeUpdate();
            c.commit();

            s.close();
            c.close();
            System.out.println("Pedido añadido");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(c);
        }
    }
    public void updatePedido(int cantidad, int producto, String factura){
        try {
            c = DriverManager.getConnection(urlConnection, user, password);
            s = c.createStatement();
            s.executeUpdate("update pedidos set cantidad = "+cantidad+" where producto="+producto
                    +" and factura = '"+factura+"';");
            s.close();
            c.close();
            System.out.println("Pedido actualizado");
        } catch (SQLException e) {
            showSQLError(e);
        } finally {
            close(c);
        }
    }
    public void eliminarPedido(Producto producto){

        try {
            c = DriverManager.getConnection(urlConnection, user, password);
            s = c.createStatement();
            s.executeUpdate("delete from pedidos where producto="+producto.getId()+";");
            s.close();
            c.close();
            System.out.println("Pedido eliminado");
        } catch (SQLException e) {
            showSQLError(e);
        } finally {
            close(c);
        }
    }


}
