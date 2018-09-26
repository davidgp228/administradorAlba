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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mac
 */
@WebServlet(name = "Datos", urlPatterns = {"/Datos"})
public class Datos extends HttpServlet {

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
            
            String imei=request.getParameter("imei");
            String latitud=request.getParameter("latitud");
            String longitud=request.getParameter("longitud");
            String status=request.getParameter("status");
            
            if(insertarMarcador(imei,latitud,longitud,status))
            out.println("<center><font size=\"5\" color=\"#0033cc\">Insercion correcta!</font></center>");
            else
            out.println("<center><font size=\"5\" color=\"#0033cc\">Error al insertar!"+imei+","+latitud
                    +","+longitud+","+status+"</font></center>"); 
            
           //  out.println("<script type=\"text/javascript\">");
           //        out.println("alert('"+modo+"');");
           //        out.println("</script>");
        }
    }
    
    private boolean insertarMarcador(String imei, String latitud, String longitud, String status){
        boolean request=false;
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("INSERT INTO Marcadores (IMEI,Latitud,Longitud,Status)"
                    + " VALUES (?,?,?,?)")){
            pst.setString(1, imei);
            pst.setString(2, latitud);
            pst.setString(3, longitud);
            pst.setString(4, status);
            pst.execute();
            
            request=true;
        
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
