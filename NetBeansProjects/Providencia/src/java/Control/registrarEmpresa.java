/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import static Control.registroConductores.eliminarConductor;
import com.mysql.jdbc.MysqlDataTruncation;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mac
 */
@WebServlet(name = "registrarEmpresa", urlPatterns = {"/registrarEmpresa"})
public class registrarEmpresa extends HttpServlet {

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
            HttpSession session= request.getSession();
            
            String accion= Consultas.getAttrib(request, "accion");
            
            String fkempresa= session.getAttribute("fkempresa").toString();
            
            String imagen= Consultas.getAttrib(request, "_imagen");
            String nombre= Consultas.getAttrib(request, "_nombre");
            String descripcion=Consultas.getAttrib(request, "_descripcion");
            
            switch(accion){
                
                case "actualizarEmpresa":
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('"+actualizarEmpresa(fkempresa,nombre,descripcion,imagen)+"');");
                     out.println("window.location = 'registrarEmpresa.jsp';");
                     out.println("</script>");
                    break;
            
            }
            
        }
    }
    
    public static String actualizarEmpresa(String fkempresa, String nombre, String descripcion, String logo){
        String request ="No se han podido actualizar los datos";
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("UPDATE Empresa SET Nombre=?, Descripcion=?, Logo=? "
                    + " WHERE IdEmpresa=?")){
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            pst.setString(3, logo);
            pst.setString(4, fkempresa);
            pst.execute();
            
            request="Datos actualizados correctamente";
            
        }
        catch(MysqlDataTruncation e){
            e.printStackTrace();
        }
        catch(Exception ex){
            ex.printStackTrace();
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
