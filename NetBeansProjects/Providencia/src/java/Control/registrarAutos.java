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
@WebServlet(name = "registrarAutos", urlPatterns = {"/registrarAutos"})
public class registrarAutos extends HttpServlet {

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
        
            String fkempresa= request.getParameter("fkempresa");
        
            String accion= getAttrib(request,"accion");
            
            String auto=Consultas.getAttrib(request, "_idauto");
            System.out.println("accion "+accion+" id seleccionado "+auto);
            
            String vehiculo=getAttrib(request,"_vehiculo");
            String placas=getAttrib(request,"_placas");
            String marca=getAttrib(request,"_marca");
            String modelo=getAttrib(request,"_modelo");
            String clasevehicular=getAttrib(request,"_clasevehicular");
            String NIV=getAttrib(request,"_NIV");
            String numeromotor=getAttrib(request,"_numeromotor");
            String ID=getAttrib(request,"_ID");
            
            String cilindros= getAttrib(request,"_cilindros");
            String combustible=getAttrib(request,"_combustible");
            String ejes=getAttrib(request,"_ejes");
            
            String uso=getAttrib(request,"_uso");
            String origen=getAttrib(request,"_origen");
            String clase=getAttrib(request,"_clase");
            
            String tipo=getAttrib(request,"_tipo");
            
            String litros=getAttrib(request,"_litros");
            String toneladas=getAttrib(request,"_toneladas");
            String personas=getAttrib(request,"_personas");
            
            String nombre=getAttrib(request,"_nombre");
            String apellidopaterno=getAttrib(request,"_apellidopaterno");
            String apellidomaterno=getAttrib(request,"_apellidomaterno");
        
        try (PrintWriter out = response.getWriter()) {
            
            switch(accion){
                case "insertarAuto":
                   out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+insertarAuto(fkempresa,vehiculo,placas,marca,modelo,clasevehicular,NIV,numeromotor,ID,cilindros,combustible,
                            ejes,uso,origen,clase,tipo,litros,toneladas,personas,nombre,apellidopaterno,apellidomaterno)+"');");
                   out.println("window.location = 'registrarAutos.jsp';");
                   out.println("</script>");
                   break;
                case "eliminarAuto":
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('"+eliminarAuto(auto)+"');");
                        out.println("window.location = 'registrarAutos.jsp';");
                        out.println("</script>");
                        break;
                case "actualizarAuto":
                      out.println("<script type=\"text/javascript\">");
                   out.println("alert('"+actualizarAuto(auto,vehiculo,placas,marca,modelo,clasevehicular,NIV,numeromotor,ID,cilindros,combustible,
                            ejes,uso,origen,clase,tipo,litros,toneladas,personas,nombre,apellidopaterno,apellidomaterno)+"');");
                   out.println("window.location = 'registrarAutos.jsp';");
                   out.println("</script>");
                    break;
            }
            
        }
    
    }
    
    
    public static String insertarAuto(String fkempresa,String vehiculo, String placas, String marca, String modelo, 
            String clasevehicular, String niv, String numeromotor, String id, String cilindros,
            String combustible, String ejes, String uso, String origenvehiculo,String clase, String tipo,
            String litros,String toneladas, String personas,String nombre, String apellidopaterno,String apellidomaterno){
        System.out.println("Insertando datos...");
    String request="No se han podido insertar los datos";
    int fkauto=0;
    
    try(Connection con= Conexion.getdatasource().getConnection();
        PreparedStatement pstinsertar= con.prepareStatement("INSERT INTO Autos "
        + "(Vehiculo, Placas, Marca, Modelo, ClaseVehicular, NIV, NumeroMotor, ID, Cilindros, Combustible, Ejes, Uso,"
        + " OrigenVehiculo, Clase, Tipo, Litros, Toneladas, Personas,Asignado, fkEmpresa) "
        + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        PreparedStatement pstconsultarid=con.prepareStatement("SELECT MAX(IdAuto) FROM Autos;");
        PreparedStatement pstinsertarpropietario= con.prepareStatement("INSERT INTO PropietarioAuto"
                + " (FkAuto, Nombre, ApellidoPaterno, ApellidoMaterno)"
                + " VALUES (?,?,?,?);")){
        pstinsertar.setString(1, vehiculo);
        pstinsertar.setString(2, placas);
        pstinsertar.setString(3, marca);
        pstinsertar.setString(4, modelo);
        pstinsertar.setString(5, clasevehicular);
        pstinsertar.setString(6, niv);
        pstinsertar.setString(7, numeromotor);
        pstinsertar.setString(8, id);
        pstinsertar.setString(9, cilindros);
        pstinsertar.setString(10, combustible);
        pstinsertar.setString(11, ejes);
        pstinsertar.setString(12, uso);
        pstinsertar.setString(13, origenvehiculo);
        pstinsertar.setString(14, clase);
        pstinsertar.setString(15, tipo);
        pstinsertar.setString(16, litros);
        pstinsertar.setString(17, toneladas);
        pstinsertar.setString(18, personas);
        pstinsertar.setString(19, "0");
        pstinsertar.setString(20, fkempresa);
        pstinsertar.execute();
        
        ResultSet rs=pstconsultarid.executeQuery();
        rs.next();
        fkauto= rs.getInt(1);
        
        pstinsertarpropietario.setInt   (1, fkauto);
        pstinsertarpropietario.setString(2, nombre);
        pstinsertarpropietario.setString(3, apellidopaterno);
        pstinsertarpropietario.setString(4, apellidomaterno);
        pstinsertarpropietario.execute();
          
        request="Datos insertados correctamente";
    }catch(Exception e){
        request="Error al insertar "+ e;
    }
    
    return request;
    }
    
    
    public String eliminarAuto(String id){
        String request="No se han podido eliminar los datos";
            try(Connection con= Conexion.getdatasource().getConnection();
                PreparedStatement psteliminarpropietario=con.prepareStatement("DELETE FROM PropietarioAuto WHERE FkAuto=?");
                PreparedStatement psteliminarauto=con.prepareStatement("DELETE FROM Autos WHERE IdAuto=? ")){
                psteliminarpropietario.setString(1, id);
                psteliminarpropietario.execute();
                psteliminarauto.setString(1, id);
                psteliminarauto.execute();
                request="Datos eliminados correctamente";
            }catch(Exception e){
                e.printStackTrace();
            }
        return request;
    }
    
    public String actualizarAuto(String idactualizar,String vehiculo, String placas, String marca, String modelo, 
            String clasevehicular, String niv, String numeromotor, String id, String cilindros,
            String combustible, String ejes, String uso, String origenvehiculo,String clase, String tipo,
            String litros,String toneladas, String personas,String nombre, String apellidopaterno,String apellidomaterno){
            String request="No se ha podido actualizar la informacion";
        
        try(Connection con=Conexion.getdatasource().getConnection();
            PreparedStatement pstactualizarAuto=con.prepareStatement("UPDATE Autos SET Vehiculo=?, Placas=?, Marca=?,"
                    + "Modelo=?,ClaseVehicular=?,NIV=?,NumeroMotor=?,ID=?,Cilindros=?,Combustible=?,Ejes=?,Uso=?,"
                    + "OrigenVehiculo=?, Clase=?, Tipo=?, Litros=?, Toneladas=?,Personas=? WHERE IdAuto=?");
            PreparedStatement pstactualizarPropietario= con.prepareStatement("UPDATE PropietarioAuto SET Nombre=?,"
                    + "ApellidoPaterno=?,ApellidoMaterno=? WHERE FkAuto=?;")){
            pstactualizarAuto.setString(1, vehiculo);
            pstactualizarAuto.setString(2, placas);
            pstactualizarAuto.setString(3, marca);
            pstactualizarAuto.setString(4, modelo);
            pstactualizarAuto.setString(5, clasevehicular);
            pstactualizarAuto.setString(6, niv);
            pstactualizarAuto.setString(7, numeromotor);
            pstactualizarAuto.setString(8, id);
            pstactualizarAuto.setString(9, cilindros);
            pstactualizarAuto.setString(10, combustible);
            pstactualizarAuto.setString(11, ejes);
            pstactualizarAuto.setString(12, uso);
            pstactualizarAuto.setString(13, origenvehiculo);
            pstactualizarAuto.setString(14, clase);
            pstactualizarAuto.setString(15, tipo);
            pstactualizarAuto.setString(16, litros);
            pstactualizarAuto.setString(17, toneladas);
            pstactualizarAuto.setString(18, personas);
            pstactualizarAuto.setString(19, idactualizar);
            pstactualizarAuto.execute();
            
            pstactualizarPropietario.setString(1, nombre);
            pstactualizarPropietario.setString(2,apellidopaterno);
            pstactualizarPropietario.setString(3,apellidomaterno);
            pstactualizarPropietario.setString(4,idactualizar);
            pstactualizarPropietario.execute();
        
            request="Datos actualizados correctamente";
        }catch(Exception e){
        e.printStackTrace();
        }
        
        return request;
    }
    
    public static String getAttrib(HttpServletRequest request,String name){
        String att=request.getParameter(name);
        if(att!=null)return att;
        else return "";
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
