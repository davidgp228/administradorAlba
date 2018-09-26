/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mac
 */
@WebServlet(name = "registroDispositivos", urlPatterns = {"/registroDispositivos"})
public class registroDispositivos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
         try (PrintWriter out = response.getWriter()) {
             String accion= Consultas.getAttrib(request, "accion");
             
             String id=     Consultas.getAttrib(request, "_id");
             String fkauto= Consultas.getAttrib(request, "_idauto");
             String imei=   Consultas.getAttrib(request, "_imei");
             String nombre= Consultas.getAttrib(request, "_nombre");
             String clave=  Consultas.getAttrib(request, "_clave");
             
             switch(accion){
                case "insertarDispositivo":
                    System.out.println("Insertando datos... fkauto " +fkauto+" imei "+ imei+" nombre: "+nombre+" clave "+clave);
                   out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+insertarDispositivos(fkauto,imei,nombre,clave)+"');");
                   out.println("window.location = 'registrarDispositivos.jsp';");
                   out.println("</script>");
                    break;
                case "eliminarDispositivo":
                    System.out.println("eliminar...");
                   out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+eliminarDispositivos(id)+"');");
                   out.println("window.location = 'registrarDispositivos.jsp';");
                   out.println("</script>");
                    break;
                case "actualizarDispositivo":
                    System.out.println("actualizar... id="+id);
                   out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+actualizarDispostivo(id,fkauto,imei,nombre,clave)+"');");
                   out.println("window.location = 'registrarDispositivos.jsp';");
                   out.println("</script>");
                    break;
             }
         }
         
    }
    
    public String insertarDispositivos(String fkauto, String imei, String nombre, String clave){
        String request="No se han podido insertar  los datos";
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pstvalidar= con.prepareStatement("SELECT IdDispositivo FROM Dispositivos WHERE IMEI=?");
            PreparedStatement pst= con.prepareStatement("INSERT INTO Dispositivos (FkAuto, IMEI, Nombre, Clave) values (?,?,?,?)")){
           
            pstvalidar.setString(1, imei);
            
            ResultSet rs= pstvalidar.executeQuery();
            
            if(!rs.next()){
                pst.setString(1, fkauto);
                pst.setString(2, imei);
                pst.setString(3, nombre);
                pst.setString(4, clave);
                pst.execute();
                request="Datos insertados correctamente";
            }
            else{
                request="El IMEI o serie ya existe";
            }
            
          
        }catch(Exception e){
        e.printStackTrace();
        }
        return request;
    }
    
    public String actualizarDispostivo(String id, String fkauto, String imei, String nombre, String clave){
        String request="No se han podido actualizar los datos";
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("UPDATE Dispositivos SET FkAuto=?, IMEI=?, Nombre=?, Clave=? WHERE IdDispositivo=?")){
            pst.setString(1, fkauto);
            pst.setString(2, imei);
            pst.setString(3, nombre);
            pst.setString(4, clave);
            pst.setString(5, id);
            pst.execute();
            request="Datos actualizados correctamente";
        }catch(Exception e){
            e.printStackTrace();
        }
        return request;
    }

    public String eliminarDispositivos(String id){
        String request="Error al eliminar dispositivo";
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("DELETE FROM Dispositivos WHERE IdDispositivo=?");
            PreparedStatement pstconsultarimei=con.prepareStatement("SELECT IMEI FROM Dispositivos WHERE IdDispositivo=?");
            PreparedStatement psteliminarmarcadores=con.prepareStatement("DELETE FROM Marcadores WHERE IMEI = ?;")){
           
            pstconsultarimei.setString(1, id);
            ResultSet rs= pstconsultarimei.executeQuery();
            rs.next();
            String imeieliminar=rs.getString(1);
           
            psteliminarmarcadores.setString(1, imeieliminar);
            psteliminarmarcadores.execute();
            
            pst.setString(1, id);
            pst.execute();
            request = "Datos eliminados correctamente";
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
