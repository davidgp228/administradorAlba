/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import static Control.haversine.desviacionDeRuta;
import static Control.haversine.obj;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mac
 */
@WebServlet(name = "Eventos", urlPatterns = {"/Eventos"})
public class Eventos extends HttpServlet{
    private static BasicDataSource datasource;
    
    /* static double[] start  = {19.181545314566797,-98.24276089668274};
    static double[] medium = {19.175384216666973,-98.24303984642029};
    static double[] end = {19.175222079400072,-98.23653817176819};*/
    
    static double[] start  = new double[2];
    static double[] medium =new double[2];
    static double[] end = new double[2];
    
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
        String Latitud="",Longitud="",IMEI="",Status="" ;
        
        Latitud=""+request.getParameter("Latitud");
        Longitud=""+request.getParameter("Longitud");
        IMEI=""+request.getParameter("IMEI");
        Status=""+request.getParameter("Status");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
        if(!Latitud.equals("null")&&!Longitud.equals("null")&&!IMEI.equals("null")&&!Status.equals("null")){
        InsertarMarcador(Latitud,Longitud,IMEI,Status);    
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Coordenadas ingresas!!');");
        out.println("</script>");
        }
        else {
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Faltan datos');");
        out.println("</script>");
        }
        }
    }
    
    public static void InsertarMarcador(String Latitud,String Longitud,String IMEI,String Status){
    
        datasource= Conexion.getdatasource();
        try(Connection con = datasource.getConnection()){
        
        PreparedStatement pst2=con.prepareStatement("insert into Marcadores (IMEI,Latitud,Longitud,Status) values (?,?,?,?)");
        pst2.setString(1, IMEI);
        pst2.setString(2, Latitud);
        pst2.setString(3, Longitud);
        pst2.setString(4, Status);
        pst2.execute();
            
        }
        catch(SQLException ex){
        Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static String ActualizarMarcadorHaversine(String imei, int fkruta, double maxdis){
    System.out.println("fkruta "+fkruta+" IMEI "+imei);
    String coordenadas="",IMEI="", velocidad="", rpm="", temperatura="", fecha="";
    double latitud=0, longitud=0;
    boolean validarUbicacion=false;
    JSONObject obj = new JSONObject();
    datasource=Conexion.getdatasource();
    
    try(Connection con =datasource.getConnection();
        PreparedStatement pst=con.prepareStatement("SELECT IMEI,Latitud, Longitud FROM Marcadores where IdMarcador="
               + "(select MAX(IdMarcador) from Marcadores where IMEI=?)");
        PreparedStatement pstestadisticas= con.prepareStatement("SELECT Velocidad,RPM, Temperatura, Fecha "
                + "FROM Estadisticas WHERE IdEstadistica=(select MAX(IdEstadistica) from Estadisticas where IMEI=?)");
        PreparedStatement pstruta= con.prepareStatement("SELECT Latitud,Longitud FROM RutaMarcadores WHERE FkRuta = ?;")){
       
       pst.setString(1, imei);
       ResultSet rs=pst.executeQuery(); 
       
       pstestadisticas.setString(1, imei);
       ResultSet rsestadisticas=pstestadisticas.executeQuery();
       
       if(rs.next()){
           
            //*** Ubicacion del auto
            IMEI=rs.getString("IMEI");
            latitud=rs.getDouble("Latitud");
            longitud=rs.getDouble("Longitud");

            //*** Estadisticas del auto
            if(rsestadisticas.next()){
                velocidad=   rsestadisticas.getString(1);
                rpm=         rsestadisticas.getString(2);
                temperatura= rsestadisticas.getString(3);
                fecha=       rsestadisticas.getString(4);
                System.out.println("feha "+fecha);
            }

            //** Consultar ruta y validar que el dispositivo esta dentro utlizando la clase Haversine
            pstruta.setInt(1, fkruta);
            ResultSet rsruta= pstruta.executeQuery();

            while (rsruta.next()){
                      obj.accumulate("Lat", new Double(rsruta.getString(1)));
                      obj.accumulate("Lon", new Double(rsruta.getString(2)));
                 }

            if(!obj.isNull("Lat")){
                if(haversine.desviacionDeRuta(obj,latitud,longitud)<=maxdis){
                validarUbicacion=true;
                }
            }

           coordenadas=IMEI+","+latitud+","+longitud+","+validarUbicacion+","+velocidad+","+rpm+","+temperatura+","+fecha;
           System.out.println("Dato---- "+coordenadas);
       }
    }
    catch(SQLException ex){
    Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE,null,ex);
    }   catch (JSONException ex) {
            Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return coordenadas;  
    }
    
    public static String ActualizarMarcador(String imei, int fkruta){
    String coordenadas="",IMEI="";
    double latitud=0, longitud=0;
    boolean validarUbicacion=false;
    datasource=Conexion.getdatasource();
    
    try(Connection con =datasource.getConnection();
        PreparedStatement pst=con.prepareStatement("SELECT IMEI,Latitud, Longitud FROM Marcadores where IdMarcador="
               + "(select MAX(IdMarcador) from Marcadores where IMEI=?)");
        PreparedStatement pstcercos=con.prepareStatement("SELECT coordenadaA,coordenadaB,coordenadaC "
                + " FROM RutaCercos WHERE FkRuta=?");){
       
       pst.setString(1, imei);
       ResultSet rs=pst.executeQuery(); 
       rs.next();
       IMEI=rs.getString("IMEI");
       latitud=rs.getDouble("Latitud");
       longitud=rs.getDouble("Longitud");
       
       //** Consultar ruta y validar que el dispositivo esta dentro de algu cerco
       pstcercos.setInt(1, fkruta);
       ResultSet rscercos= pstcercos.executeQuery();
       
       while(rscercos.next() && !validarUbicacion){
            String splitA[]=rscercos.getString("coordenadaA").split(",");
            start[0]=Double.parseDouble(splitA[0]) ;
            start[1]=Double.parseDouble(splitA[1]) ;
            
            String splitB[]=rscercos.getString("coordenadaB").split(",");
            medium[0]=Double.parseDouble(splitB[0]) ;
            medium[1]=Double.parseDouble(splitB[1]) ;
            
            String splitC[]=rscercos.getString("coordenadaC").split(",");
            end[0]=Double.parseDouble(splitC[0]) ;
            end[1]=Double.parseDouble(splitC[1]) ;
            
            validarUbicacion=ubicacion.validarUbicacion(latitud, longitud, start, medium, end);
       }
       
       
       
       coordenadas=IMEI+","+latitud+","+longitud+","+validarUbicacion;
    }
    catch(SQLException ex){
    Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE,null,ex);
    }
    
    return coordenadas;    
    }
    
    public static JSONObject ActualizarMultiplesMarcadores(String fecha,String imei){
      JSONObject obj = new JSONObject(); 
        datasource=Conexion.getdatasource();
        try (Connection con = datasource.getConnection()){
            PreparedStatement pst=con.prepareStatement("SELECT * FROM Marcadores where"
         + " Fecha >= ? AND IMEI=? ORDER BY Fecha ASC;");
            pst.setString(1, fecha);
            pst.setString(2, imei);
            ResultSet rs=pst.executeQuery();
            String r[]={"",""};
            while (rs.next()){
                r[0]=rs.getString("Latitud");
                obj.accumulate("Lat",rs.getString("Latitud") );
                r[1]=rs.getString("Longitud");
                obj.accumulate("Lon",rs.getString("Longitud") );
            }
             System.out.print(obj);
        } catch (SQLException ex) {
            Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {     
            Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("JSON"+obj);
        return obj;     
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
