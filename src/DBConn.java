/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users_administration;

//Librerias para funcionamiento de MySQL 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Estas dos librerias permiten crear un log del programa
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Alohanet
 */

public class DBConn {
    
    protected final String DBHost = "us-cdbr-iron-east-04.cleardb.net";
    protected final String DBUser = "bb93fad27fd7a5";
    protected final String DBPass = "de319f9d";
    protected final String DBName = "ad_5a8b0d74982dbe1";
    
    private static Connection Conexion;
    
    // Funcion que nos permite establecer la conexión con la base de datos
    public void MySQLConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Conexion = DriverManager.getConnection("jdbc:mysql://" + DBHost + ":3306/" + DBName + "?useSSL=false", DBUser, DBPass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Funcion que nos permite cerrar la conexión con la base de datos
    public void closeConnection() {
        try {
            Conexion.close();
            //System.err.println("Se ha finalizado la conexión con el servidor");
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Funcion que nos permite insertar datos en una tabla ya existente
    public void insertarUsuario(String table_name,String user_name, String user_pass) {
        try {  
            String Query = "INSERT INTO `"+ DBName +"`.`"+ table_name +"` (`username`, `userpass`) VALUES ('"+user_name+"', '"+user_pass+"')";               
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);
            System.out.println("\n<<< Datos almacenados de forma exitosa.\n<<< Usuario: "+user_name+"\n<<< Contraseña: "+ user_pass +"\n");            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");
        }
    }
    
    //Metodo para validar un usuario de la base de datos
    public boolean validarUsuario (String user, String pass){
       return true; 
    }
 
    //Funcion que nos permite recoger valores de una tabla existente
    public void getUsers(String table_name) {
        try {
            String Query = "SELECT * FROM ad_5a8b0d74982dbe1.users;";
            Statement st = Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(Query);
            
            if (resultSet.next()== false){
                System.out.print("\n<<< No se han encontrado usuarios\n");
            }else{
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    System.out.println("\n >>> ID [" + resultSet.getString("iduser") + "] \n >>> Usuario: " + resultSet.getString("username")+ "\n >>> Contraseña: " + resultSet.getString("userpass"));                       
                }
            }
 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la adquisición de datos");
        }
        
    }
 
    //Funcion que nos permite borrar un registro
    public void deleteRecord(String table_name, int ID) {
        try {
            String Query = "DELETE FROM " + table_name + " WHERE iduser = " + ID ;
            Statement st = Conexion.createStatement();
            if (st.executeUpdate(Query)==1){
            System.out.print("\n<<< Registro de la tabla '"+table_name+"' con numero de identificación '"+ID+"' borrado correctamente\n");
            }else{
                System.out.print("\n<<< Número de identificación '"+ID+"' no encontrado\n");
            }
 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error borrando el registro especificado");
        }
    }
    
    public void cleanDB (String table_name) {
        
        try {
            String Query = "TRUNCATE "+table_name+"";
            Statement st = Conexion.createStatement();
            if (st.executeUpdate(Query)==0){
            System.out.print("\n<<< Se ha limpiado la base de datos correctamente.\n");
            }else{
            System.out.print("\n<<< ERROR: Ha surgido algun error al limpiar la base de datos.\n");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error limpiando los registros");
        }
    }
    
    /*
    *   FUNCIONES ACTUALMENTE NO UTILIZADAS
    */
    
     //Funcion que nos permite crear una nueva base de datos
//    public void createDB(String name) {
//        try {
//            String Query = "CREATE DATABASE " + name;
//            Statement st = Conexion.createStatement();
//            st.executeUpdate(Query);
//            MySQLConnection("root", "", name);
//            JOptionPane.showMessageDialog(null, "Se ha creado la base de datos " + name + " de forma exitosa");
//        } catch (SQLException ex) {
//            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
 
    //Funcion que nos permite crear una nueva tabla
    public void createTable(String name) {
        try {
            String Query = "CREATE TABLE " + name + ""
                    + "(ID VARCHAR(25),Nombre VARCHAR(50), Apellido VARCHAR(50),"
                    + " Edad VARCHAR(3), Sexo VARCHAR(1))";
            JOptionPane.showMessageDialog(null, "Se ha creado la base de tabla " + name + " de forma exitosa");
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
