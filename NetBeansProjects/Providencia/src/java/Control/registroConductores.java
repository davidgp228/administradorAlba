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
@WebServlet(name = "registroConductores", urlPatterns = {"/registroConductores"})
public class registroConductores extends HttpServlet {

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
            
            String conductor=Consultas.getAttrib(request, "_idconductor");

            String id= Consultas.getAttrib(request, "_id");
            String nombre= Consultas.getAttrib(request, "_nombre");
            String apellidopaterno= Consultas.getAttrib(request, "_apellidopaterno");
            String apellidomaterno= Consultas.getAttrib(request, "_apellidomaterno");
            String dia= Consultas.getAttrib(request, "_dia");
            String mes= Consultas.getAttrib(request, "_mes");
            String anio= Consultas.getAttrib(request, "_anio");
            
            String edad= Consultas.getAttrib(request, "_edad");
            String sexo= Consultas.getAttrib(request, "_sexo");
            String numerolicencia= Consultas.getAttrib(request, "_numerolicencia");
            String tiposangre= Consultas.getAttrib(request, "_tiposangre");
            String donador= Consultas.getAttrib(request, "_donador");
            String calle= Consultas.getAttrib(request, "_calle");
            String numeroexterior= Consultas.getAttrib(request, "_numeroexterior");
            String numerointerior= Consultas.getAttrib(request, "_numerointerior");
            String codigopostal= Consultas.getAttrib(request, "_codigopostal");
            String colonia= Consultas.getAttrib(request, "_colonia");
            String municipio= Consultas.getAttrib(request, "_municipio");
            String estado= Consultas.getAttrib(request, "_estado");
            String telefono= Consultas.getAttrib(request, "_telefono");
            String correo= Consultas.getAttrib(request, "_correo");
                        
            switch(accion){
                case "insertarConductor":
                   out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+insertarConductor(id,nombre,apellidopaterno,apellidomaterno,
                           dia,mes,anio,edad,sexo,numerolicencia,tiposangre,donador,calle,numeroexterior,numerointerior,
                           codigopostal,colonia,municipio,estado,telefono,correo)+"');");
                   out.println("window.location = 'registroConductores.jsp';");
                   out.println("</script>");
                break;
                  case "eliminarConductor":
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('"+eliminarConductor(conductor)+"');");
                        out.println("window.location = 'registroConductores.jsp';");
                        out.println("</script>");
                        break;
                  case "actualizarConductor":
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('"+actualizarConductor(conductor,id,nombre,apellidopaterno,apellidomaterno,
                           dia,mes,anio,edad,sexo,numerolicencia,tiposangre,donador,calle,numeroexterior,numerointerior,
                           codigopostal,colonia,municipio,estado,telefono,correo)+"');");
                        out.println("window.location = 'registroConductores.jsp';");
                        out.println("</script>");
                        break;
            }
           
        }
    }
    
    public static String insertarConductor(String id,String nombre,String apellidopaterno,
            String apellidomaterno,String dia,String mes, String anio, String edad, String sexo,String numerolicencia,
            String tiposangre,String donador,String calle,String numeroexterior,String numerointerior,String codigopostal,
            String colonia, String municipio, String estado,String telefono,String correo){
        String request="No se ha podido insertar conductor";
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("INSERT INTO Conductores (ID,Nombre,ApellidoPaterno,"
                    + "ApellidoMaterno,Dia,Mes,Anio,Edad,Sexo,NumeroLicencia,TipoSangre,Donador,Calle,NumeroExterior,"
                    + "NumeroInterior,CodigoPostal,Colonia,Municipio,Estado,Telefono,Correo)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
           ){
            pst.setString(1, id);
            pst.setString(2, nombre);
            pst.setString(3, apellidopaterno);
            pst.setString(4, apellidomaterno);
            pst.setString(5, dia);
            pst.setString(6, mes);
            pst.setString(7, anio);
            pst.setString(8, edad);
            pst.setString(9, sexo);
            pst.setString(10, numerolicencia);
            pst.setString(11, tiposangre);
            pst.setString(12, donador);
            pst.setString(13, calle);
            pst.setString(14, numeroexterior);
            pst.setString(15, numerointerior);
            pst.setString(16, codigopostal);
            pst.setString(17, colonia);
            pst.setString(18, municipio);
            pst.setString(19, estado);
            pst.setString(20, telefono);
            pst.setString(21, correo);
            pst.execute();
           
        
            request="Datos insertados correctamente";
        }catch(Exception e){
         e.printStackTrace();
        }
        
        return request;
    }
    
    public static String actualizarConductor(String ideditar,String id,String nombre,String apellidopaterno,
            String apellidomaterno,String dia,String mes, String anio, String edad, String sexo,String numerolicencia,
            String tiposangre,String donador,String calle,String numeroexterior,String numerointerior,String codigopostal,
            String colonia, String municipio, String estado,String telefono,String correo){
        
        String request="No se han podido actualizar los datos";
    
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("UPDATE Conductores SET ID=?,Nombre=?, ApellidoPaterno=?,"
                    + "ApellidoMaterno=?, Dia=?, Mes=?, Anio=?, Edad=?, Sexo=?, NumeroLicencia=?, TipoSangre=?,"
                    + "Donador=?, Calle=?, NumeroExterior=?, NumeroInterior=?, CodigoPostal=?, Colonia=?, Municipio=?,"
                    + "Estado=?, Telefono=?, Correo=? WHERE IdConductor=?")){
            pst.setString(1, id);
            pst.setString(2, nombre);
            pst.setString(3, apellidopaterno);
            pst.setString(4, apellidomaterno);
            pst.setString(5, dia);
            pst.setString(6, mes);
            pst.setString(7, anio);
            pst.setString(8, edad);
            pst.setString(9, sexo);
            pst.setString(10, numerolicencia);
            pst.setString(11, tiposangre);
            pst.setString(12, donador);
            pst.setString(13, calle);
            pst.setString(14, numeroexterior);
            pst.setString(15, numerointerior);
            pst.setString(16, codigopostal);
            pst.setString(17, colonia);
            pst.setString(18, municipio);
            pst.setString(19, estado);
            pst.setString(20, telefono);
            pst.setString(21, correo);
            pst.setString(22, ideditar);      
            
            pst.execute();
            
            request="Datos actualizados correctamente";
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }

    public static String eliminarConductor(String id){
        String request="No se ha podido eliminar conductor";
        String fkauto="";
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement psteliminar= con.prepareStatement("DELETE FROM Conductores WHERE IdConductor=?");
            ){
            
            psteliminar.setString(1, id);
            psteliminar.execute();
            
            request="Datos eliminados correctamente";
            
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
