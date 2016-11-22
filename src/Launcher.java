/**
 * @author Alohanet
 */

package users_administration;

import static java.lang.System.exit;
import java.util.Scanner;

public class Launcher {
    
    protected static String DBUserTable = "users";
    protected static String DBEventTable = "events";
    
   
    public static void main(String[] args) {
       
        DBConn conn = new DBConn();
        
        
        while (true){
        Menu();
        Scanner sc = new Scanner (System.in);
        int opt = sc.nextInt();        
            switch (opt){
                case 1:              
                    System.out.print("\nNombre: ");                    
                    String user_name = sc.next();
                    System.out.print("Contraseña: ");
                    String user_pass = sc.next();                     
           
                        conn.MySQLConnection();
                        conn.insertarUsuario(DBUserTable, user_name, user_pass);
                        conn.closeConnection();                  
                
                    break;
                case 2:   
                    System.out.print("\nID: ");                    
                    int user_id = sc.nextInt();
                    conn.MySQLConnection();
                    conn.deleteRecord(DBUserTable, user_id);
                    conn.closeConnection();
                    
                    break;
                case 3:    
                        conn.MySQLConnection();
                        conn.getUsers(DBUserTable);
                        conn.closeConnection();
                    break;
                case 4: 
                    if(confirm(sc)==1){
                        conn.MySQLConnection();
                        conn.cleanDB(DBUserTable);
                        conn.closeConnection();
                    }else{
                        break;
                    }
                    break;
                case 5:   
                    exit(0);
                    break;
                default:                  
                    break;
                    
            }
        }
      
    }
    
    static void Menu (){
        
        System.out.println("\nMENU DE GESTIÓN DE USUARIOS:");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Lista de usuarios");
        System.out.println("4. Restaurar base de datos");
        System.out.println("5. Salir");
        
        System.out.print("  Seleccione su opción: ");
        
    }
    
    static int confirm(Scanner sc){
        System.out.print("\nSi continua se perderán los datos. ¿Está seguro? (s/n):");
        String aux = sc.next();        
        if (aux.equals("s")){         
          return 1;
        }else if (aux.equals("n")){
          return 0;
        }else{
          confirm(sc);
        }
       return 0;
    }
    

}
