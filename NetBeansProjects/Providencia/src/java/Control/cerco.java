/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
@WebServlet(name = "cerco", urlPatterns = {"/cerco"})
public class cerco extends HttpServlet {
    
    private static BasicDataSource datasource;
    private static boolean request;
    Rectangle2D punto,area;
    double[] start  = {19.172263046253324,-98.23644330127615};
    double[] medium = {19.17002346871644,-98.23275258167166};
    double[] end = {19.172921739736516,-98.23037078006644};
    double[] puntomedio={19.172901472283986,-98.233353396491};
    double wight=0;
    double height=0;
    
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
    }
    
    public static boolean insertarCerco(int fkRuta,String coordenadaA,String coordenadaB, String coordenadaC){
        request=false;
        
        datasource= Conexion.getdatasource();
        try(Connection con= datasource.getConnection()){
            
                PreparedStatement pstinsertarcerco= con.prepareStatement("INSERT INTO RutaCercos "
                        + "(FkRuta,coordenadaA,coordenadaB,coordenadaC) values (?,?,?,?);");
                pstinsertarcerco.setString(1, ""+fkRuta);
                pstinsertarcerco.setString(2, coordenadaA);
                pstinsertarcerco.setString(3, coordenadaB);
                pstinsertarcerco.setString(4, coordenadaC);
                
                request= pstinsertarcerco.execute();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(cerco.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return request;
    }

    public boolean compararPrueba(double latitud, double longitud ){
        boolean request=false;
        wight=calcularDistancia(start[0],start[1],medium[0],medium[1]);
        height=calcularDistancia(medium[0],medium[1],end[0],end[1]);
        
        if(comparar(""+latitud,""+longitud,""+puntomedio[0],""+puntomedio[1])){
         request=true;
        }
       
        return request;
    }
    
    public boolean comparar(String lat1,String lon1, String lat2, String lon2){
    
        boolean res= false;
        punto= new  Rectangle2D.Double(parseDouble(lat1),parseDouble(lon1),0.1,0.1);
        area=new  Rectangle2D.Double(parseDouble(lat2),parseDouble(lon2),wight,height);
                
        if(area.intersects(punto)){
           res= true;
        //Colisiono
        System.out.println("Colisionaron");
        res=true;
        }else{
        System.out.println("No Colisionaron");
        }
        
        return res;
    }
    
   
     public static double calcularDistancia(double x1, double y1, double x2, double y2){
        double value=0, elevarX, elevarY, restaY , restaX, sumaXY;
        DecimalFormat df = new DecimalFormat("#.##########");
        restaY = (y2- (y1));
        restaX = (x2- (x1));
        System.out.println("Resta Y "+restaY+" Restax= "+restaX );
        
        elevarY= Math.pow(restaY, 2);
        elevarX= Math.pow(restaX, 2);
        System.out.println("ElevarY= "+elevarY+" ElevarX= "+elevarX);
        
        sumaXY=elevarY+elevarX;
        System.out.println("Suma "+sumaXY);
        
        value=Math.sqrt(sumaXY);
        System.out.println("Total= "+df.format(value));
       
        return value;
    }
    
    public double parseDouble(String s){
        double parse=0;
        try{
        parse= Double.parseDouble(s);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return parse;
    }
    
    public int parseInt(String s){
        int parse=0;
        try{
        parse= Integer.parseInt(s);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return parse;
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
