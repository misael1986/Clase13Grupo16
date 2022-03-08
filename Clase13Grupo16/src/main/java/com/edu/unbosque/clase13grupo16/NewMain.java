/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.unbosque.clase13grupo16;

/**
 *
 * @author Usuario
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Conectar conn= new Conectar();
        conn.PruebaconectarBD();
        //conn.MostrarTodo();// Trae MUCHOS Resultados -> executeQuery
        //conn.GuardarPersona("Misael", "Perilla", 35);// NO trae Resultados -> executeUpdate
        //conn.GuardarPersona("Juan", "Perez", 36);
        //conn.BuscarPersona("Perez");//-> executeQuery
        //conn.ModificarPersona("Sosa", "Jorge", "Ramos", 50);//executeUpdate
        //conn.BorrarPersona("Perilla");//executeUpdate
        //conn.MostrarTodo();//executeQuery
                
    }
    
}
