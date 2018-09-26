/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ResourceHandler;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author mac
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    BasicDataSource datasource;

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
        
         try (PrintWriter out = response.getWriter()) {
        response.setContentType("text/html;charset=UTF-8");
         System.out.print("iniciando...");
        String accion=Eventos.getAttrib(request, "accion");
        HttpSession sesion= null;
        switch(accion){
            case "cerrar":               
                request.getSession().invalidate();
                response.sendRedirect("Login.jsp");
                return;
            case "cambio":
                sesion=request.getSession();
                int idu=Integer.parseInt(sesion.getAttribute("userId").toString());
                String correo=Eventos.getAttrib(request, "_correo");
                String contrasena=Eventos.getAttrib(request, "_passNew");
                String contrasenaAnt=Eventos.getAttrib(request, "_passOld");
                
                boolean exito =cambiarContrasena(idu,correo,contrasena,contrasenaAnt);
                sesion.setAttribute("usuario", correo);
                if(exito){
                    //request.setAttribute("mensaje", "si");
                    response.sendRedirect("Configuracion.jsp?mensaje=si");
                }else {
                    //request.setAttribute("mensaje", "no");
                    response.sendRedirect("Configuracion.jsp?mensaje=no");
                }
                return;
        }
        
        String usuario=request.getParameter( "_user");
        String pass=request.getParameter("_pass");
        
        sesion=request.getSession(true);
        int id=getIdUser(usuario,pass);
        sesion.setAttribute("usuario", usuario);
        sesion.setAttribute("userId", id);
        sesion.setAttribute("nombre", getName(id));
        sesion.setAttribute("fkempresa", getfkEmpresa(id));
        
        if(id==-1){
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Usuario o contraseña incorrectos');");
            out.println("window.location = 'Login.jsp';");
            out.println("</script>");
        }else{
            response.sendRedirect(Consultas.menu(id));//** Redirecciona al menu de cada usuario
        }
       
        
         }
       
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
    
    public int getIdUser(String usuario,String pass){
        datasource= Conexion.getdatasource();
        try (Connection con = datasource.getConnection()){
            PreparedStatement pst=con.prepareStatement("SELECT IdAdministrador FROM Administrador where Usuario=? and Password=?");
            pst.setString(1, usuario);
            pst.setString(2, pass);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return -1;
    }
    
    public String getName(int userId){
        datasource= Conexion.getdatasource();
        try (Connection con = datasource.getConnection()){
            PreparedStatement pst=con.prepareStatement("SELECT Nombre FROM Administrador where IdAdministrador=?");
            pst.setInt(1, userId);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return "";
    }
    
    public String getfkEmpresa(int userId){
        String request="";
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("SELECT fkEmpresa FROM Administrador WHERE IdAdministrador=?")){
            pst.setInt(1, userId);
            
            ResultSet rs= pst.executeQuery();
            
            if(rs.next()){
                request=rs.getString(1);
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }
    
    private boolean cambiarContrasena(int idu,String usuario, String contrasena,String contrasenaAnterior ) {
        datasource=Conexion.getdatasource();
        try (Connection con = datasource.getConnection()){
            PreparedStatement pst=con.prepareStatement("select IdAdministrador,Usuario from Administrador where IdAdministrador=? and Password=?");
            pst.setInt(1, idu);
            pst.setString(2, contrasenaAnterior);
            ResultSet rs=pst.executeQuery();
            if(!rs.next())return false;
            if(usuario.equals("")){
                usuario=rs.getString(2);
                System.out.println("Datossss: "+usuario);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        try (Connection con = datasource.getConnection()){
            PreparedStatement pst=con.prepareStatement("update Administrador set Password=?,Usuario=? where IdAdministrador=?");
            pst.setString(1, contrasena);
            pst.setString(2, usuario);
            pst.setInt(3, idu);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }      
        return true;
    }
    
       public String Encriptar(String password){
   	MessageDigest md = null;
        String encript="";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] mdbytes = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
              sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format : " + sb.toString());  
            encript=sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //Error
        }
        return encript;
  }

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
