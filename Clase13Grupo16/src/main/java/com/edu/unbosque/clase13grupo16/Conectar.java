/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.unbosque.clase13grupo16;

import java.sql.Connection;//Objetos necesarios para operar datos con una BD
import java.sql.DriverManager;//Permite la conexión de la librería
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class Conectar {

    public void PruebaconectarBD() {
        Connection conn = null;
        try {

            String url = "jdbc:sqlite:C:\\Users\\Usuario\\Documents\\NetBeansProjects\\BDs\\personas.db";

            conn = DriverManager.getConnection(url);

            System.out.println("Conection con SQLite Exitosa.");

        } catch (SQLException e) {
            System.out.println("Error al Conectar con BD: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private Connection ConectarBD() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\Usuario\\Desktop\\personas.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error al Conectar: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    //-------------------Enviar datos a BD para Crear Registros------------
    //CRUD
    //-----------------C = Create ---------- Guardar -----------------
    public void GuardarPersona(String nombre, String apell, int edad) {
        String sql = "INSERT INTO persona(nombre,apellido,edad) VALUES(?,?,?)";
        // Cada ? es un Parametro a ser modificado despues

        try (Connection conn = this.ConectarBD();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);//Acá se sustituye cada ? por el valor
            pstmt.setString(2, apell);
            pstmt.setInt(3, edad);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al guardar en la BD: ");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    //-----------------R = Read ---------- Leer o Buscar -----------------
    public void BuscarPersona(String apell) {
        /*String sql = "SELECT id, nombre, apellido, edad FROM persona WHERE apellido = '"
              + apell + "'";//Consulta 'quemada'*/

        String sql = "SELECT id, nombre, apellido, edad FROM persona WHERE apellido = ?";
        //Consulta 'preparada'

        try (Connection conn = this.ConectarBD();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, apell);

            ResultSet rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("nombre") + "\t"
                        + rs.getString("apellido") + "\t"
                        + rs.getInt("edad"));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Buscar: " + e.getMessage());
            e.printStackTrace();
        }

    }

//-----------------U = Update ---------- Modificar o Actualizar-----------
    public void ModificarPersona(String apellOld, String nombre, String apellNew, int edad) {
        String sql = "UPDATE persona SET nombre = ? , "
                + "apellido = ? ,"
                + "edad = ? "
                + "WHERE apellido = ?";//Lo normal es usar el ID

        try (Connection conn = this.ConectarBD();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellNew);
            pstmt.setInt(3, edad);
            pstmt.setString(4, apellOld);

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Modificar: " + e.getMessage());
            e.printStackTrace();
        }

    }

    //-----------------D = Delete ---------- Eliminar o Borrar -----------------
    //Ley 1266 de 2008 (Habeas Data) y Ley 1581 de 2012 (Protección de Datos)
    public void BorrarPersona(String apell) {
        String sql = "DELETE FROM persona WHERE apellido = ?";

        try (Connection conn = this.ConectarBD();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, apell);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Borrar: " + e.getMessage());
            e.printStackTrace();
        }

    }

    // ---------------------- el QUERY Que trae Todo-------------------
    public void MostrarTodo() {
        String sql = "SELECT id, nombre, apellido, edad FROM persona";
        //             el famoso SELECT * from tabla

        try (Connection conn = this.ConectarBD();
                Statement stmt = conn.createStatement();//Preparar Consulta -> Abrir la tabla
                ResultSet rs = stmt.executeQuery(sql)) {//ResultSet carga almacena
            //El resulatdo de la consulta

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"//trae el dato con encabezado "id"
                        + rs.getString("nombre") + "\t"
                        + rs.getString("apellido") + "\t"
                        + rs.getInt("edad"));
            }
            conn.close();
        } catch (SQLException e) {

            System.out.println("Error al tratar de Leer Todo: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
