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
import javax.servlet.http.HttpSession;

/**
 *
 * @author mac
 */
@WebServlet(name = "registroUsuarios", urlPatterns = {"/registroUsuarios"})
public class registroUsuarios extends HttpServlet {

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
        HttpSession sesion=request.getSession();
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String accion= Consultas.getAttrib(request, "accion");
            
            String permisos= Consultas.getAttrib(request, "_permisos");
            String nombre=Consultas.getAttrib(request, "_nombre");
            String usuario=Consultas.getAttrib(request, "_nombreusuario");
            String contrasena=Consultas.getAttrib(request, "_nuevacontrasena");
            String fkempresa= sesion.getAttribute("fkempresa").toString();
            
            String idusuario=Consultas.getAttrib(request, "_idusuario");
            
            System.out.println("permisos= "+ permisos +" accion= "+accion+" fkempresa= "+fkempresa);
            
            switch(accion){
                case "insertarUsuario":
                   out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+insertarUsuario(nombre,usuario,contrasena,fkempresa,permisos)+"');");
                   out.println("window.location = 'registrarUsuarios.jsp';");
                   out.println("</script>");
                    break;
                case "eliminarUsuario":
                   out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+eliminarUsuario(idusuario)+"');");
                   out.println("window.location = 'registrarUsuarios.jsp';");
                   out.println("</script>");
                        break;
                case "actualizarUsuario":
                   out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+actualizarUsuario(idusuario,fkempresa,nombre,usuario,contrasena,permisos)+"');");
                   out.println("window.location = 'registrarUsuarios.jsp';");
                   out.println("</script>");
                    break;
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

    public static String insertarUsuario(String nombre, String usuario,String contrasena, String fkempresa, String permisos){
        String request="No se han podido insertar los datos", validarnombre="", validarusuario="";
        boolean exist=false;
        int id=0;
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pstvalidar=con.prepareStatement("SELECT Nombre,Usuario FROM Administrador WHERE fkEmpresa=?");
            PreparedStatement pst= con.prepareStatement("INSERT INTO Administrador (Nombre,Usuario,Password,fkEmpresa) VALUES (?,?,?,?)");
            PreparedStatement pstconsultarid=con.prepareStatement("SELECT MAX(IdAdministrador) FROM Administrador");
            PreparedStatement pstinsertarpermisos=con.prepareStatement("INSERT INTO Permisos (fkAdministrador,Permiso) VALUES (?,?)")){
        
            pstvalidar.setString(1, fkempresa);
            ResultSet validar=pstvalidar.executeQuery();
            
            while(validar.next()){
                validarnombre= validar.getString(1);
                validarusuario=validar.getString(2);
                
                if(validarnombre.equals(nombre)||validarusuario.equals(usuario)){
                    exist=true;
                }
            }
            
            if(exist){
               return request="El nombre o usuario ya se encuentran registrados";
            }
            pst.setString(1, nombre);
            pst.setString(2, usuario);
            pst.setString(3, contrasena);
            pst.setString(4, fkempresa);
            
            pst.execute();
            
                ResultSet rs= pstconsultarid.executeQuery();
                rs.next();
                id=rs.getInt(1);
                                
                String []split= permisos.split(",");
                
                for (String permiso : split) {
                   pstinsertarpermisos.setInt(1, id);
                   pstinsertarpermisos.setString(2, permiso);
                   pstinsertarpermisos.execute();
                }
                request="Datos insertados correctamente";
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }
    
    public static String actualizarUsuario(String idusuario,String fkempresa,String nombre, String usuario,String contrasena, String permisos){
        String request="No se han podido actualizar los datos",validarnombre="", validarusuario="";;
        String nombreanterior="",usuarioanterior="";
        boolean exist=false;
        
        try(Connection con=Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("UPDATE Administrador SET Nombre=?, Usuario=?, Password=? WHERE IdAdministrador=?");
            PreparedStatement pstdatosanteriores= con.prepareStatement("SELECT Nombre,Usuario FROM Administrador WHERE IdAdministrador=?");
            PreparedStatement pstvalidar=con.prepareStatement("SELECT Nombre,Usuario FROM Administrador WHERE fkEmpresa=?");
            PreparedStatement psteliminarpermisos=con.prepareStatement("DELETE FROM Permisos WHERE fkAdministrador=? ");
            PreparedStatement pstinsertarpermisos=con.prepareStatement("INSERT INTO Permisos (fkAdministrador,Permiso) VALUES (?,?)")){
            
            pstdatosanteriores.setString(1, idusuario);
            ResultSet rsanterior= pstdatosanteriores.executeQuery();
            rsanterior.next();
            nombreanterior=rsanterior.getString(1);
            usuarioanterior=rsanterior.getString(2);
            System.out.println("Datos anteriores "+nombreanterior+" "+usuarioanterior+" Datos nuevos "+nombre+" "+usuario);
            //** Si los datos han cambiado validar que los nuevos datos de nombre y usuario no esten registrados
            if( !nombreanterior.equals(nombre) || !usuarioanterior.equals(usuario) ){
                pstvalidar.setString(1, fkempresa);
                ResultSet validar=pstvalidar.executeQuery();
                
                while(validar.next()){
                    validarnombre= validar.getString(1);
                    validarusuario=validar.getString(2);

                    if(validarnombre.equals(nombre)||validarusuario.equals(usuario)){
                        exist=true;
                    }
               }
            
                if(exist){
                   return request="El nombre o usuario ya se encuentran registrados";
                }
            }
           
            
            pst.setString(1, nombre);
            pst.setString(2, usuario);
            pst.setString(3, contrasena);
            pst.setString(4, idusuario);
            pst.execute();
            
            psteliminarpermisos.setString(1, idusuario);
            psteliminarpermisos.execute();
            
            String []split= permisos.split(",");
                
                for (String permiso : split) {
                   pstinsertarpermisos.setString(1, idusuario);
                   pstinsertarpermisos.setString(2, permiso);
                   pstinsertarpermisos.execute();
                }
            
            request="Datos actualizados correctamente";
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }
    
    public static String eliminarUsuario(String idusuario){
        String request="No se ha podido eliminar el usuario";
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("DELETE FROM Administrador WHERE IdAdministrador=?");
            PreparedStatement psteliminarpermisos= con.prepareStatement("DELETE FROM Permisos WHERE fkAdministrador=?")){
            
            psteliminarpermisos.setString(1, idusuario);
            psteliminarpermisos.execute();
            
            pst.setString(1, idusuario);
            pst.execute();
            
            request="Datos eliminados correctamente";
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }

}
