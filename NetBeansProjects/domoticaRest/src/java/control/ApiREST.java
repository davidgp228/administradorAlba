/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Random;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import clases.*;


@Path("/restapi")
public class ApiREST {
    
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response login(@QueryParam("usuario") String usuario, @QueryParam("contrasena") String contrasena){
    return Response.ok(
                respuestaLogin(usuario, contrasena), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/loginPanel")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response loginPanel(@QueryParam("usuario") String usuario, @QueryParam("contrasena") String contrasena){
    return Response.ok(
                respuestaLoginPanel(usuario, contrasena), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/actualizacionesPanel")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response actualizacionesPanel(@QueryParam("token") String token){
    return Response.ok(
                respuestaActualizacionesPanel(token), 
                MediaType.APPLICATION_JSON).build();
    }
    
    
    @GET
    @Path("/inmuebles")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response inmueblesGET(@QueryParam("token1") String token1, @QueryParam("token2") String token2){
    return Response.ok(
                respuestaInmuebles(token1, token2), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/inmueblesPanel")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response inmueblesPanelGET(@QueryParam("token") String token){
    return Response.ok(
                respuestaInmueblesPanel(token), 
                MediaType.APPLICATION_JSON).build();
    }
   
    
    @GET
    @Path("/habitaciones")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response habitacionesGET(@QueryParam("token1") String token1,@QueryParam("token2") String token2){
    return Response.ok(
                respuestaHabitaciones(token1,token2), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/habitacionesPanel")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response habitacionesPanelGET(@QueryParam("token") String token){
    return Response.ok(
                respuestaHabitacionesPanel(token), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/dispositivos")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response dispositivosGET(@QueryParam("token1") String token1, @QueryParam("token2") String token2){
    return Response.ok(
                respuestaDispositivos(token1, token2), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/dispositivosPanel")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response dispositivosPanelGET(@QueryParam("token") String token){
    return Response.ok(
                respuestaDispositivosPanel(token), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/topicos")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response topicosGET(@QueryParam("token1") String token1, @QueryParam("token2") String token2){
    return Response.ok(
                respuestaTopicos(token1, token2), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/topicosPanel")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response topicosPanelGET(@QueryParam("token") String token){
    return Response.ok(
                respuestaTopicosPanel(token), 
                MediaType.APPLICATION_JSON).build();
    }
    
    
    
    private String respuestaLogin(String usuario, String contrasena) {
        
        try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("SELECT "+usuarios.idTabla+
                    " FROM "+usuarios.nombreTabla+" WHERE "+usuarios.usuario+"=? AND "+usuarios.password+"=?")){
            pst.setString(1, usuario);
            pst.setString(2, contrasena);
            ResultSet rs= pst.executeQuery();
            
            if(rs.next()) {
            return "{\"login\": \""+rs.getString(1)+"\"}";
            }   
            else{
                return "{\"login\": \"0\"}";
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return "{\"login\": \"-1\"}";
        
    }
    
    private String respuestaLoginPanel(String usuario, String contrasena) {
        
        try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("SELECT "+contrato.idTabla+" FROM "+contrato.nombreTabla+
                    " WHERE "+contrato.numeroContrato+"=? AND "+contrato.claveContrato+"=? AND "
                    + contrato.estadoServicio+" = ?");
            PreparedStatement pstActivar= con.prepareStatement("call activarContrato(?)")
                            )
        {
            
                    pst.setString(1, usuario);
                    pst.setString(2, contrasena);
                    pst.setString(3, "inactivo");
                    ResultSet rs= pst.executeQuery();

                    if(rs.next()) {

                    //**Se actualiza el estado del contrato al ser activado en una tablet
                    pstActivar.setString(1, rs.getString(1));
                    pstActivar.execute();

                     return "{\"login\": \""+rs.getString(1)+"\"}";
                    }   
                    else{
                        return "{\"login\": \"0\"}";
                    }
        
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return "{\"login\": \"-1\"}";
        
    }
    
    private String respuestaInmueblesPanel(String token){
      JSONArray json= null;      
                
     try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaInmueblesPanel(?)")){
            pst.setString(1, token);
            ResultSet rs= pst.executeQuery();
            json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                
                 for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getString(column_name));
                 }
                 
                 json.put(obj);
            }            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return json.toString();
    }
    
    private String respuestaActualizacionesPanel(String token) {
        
        try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call actualizacionesPanel(?)"))
        {
            
                    pst.setString(1, token);
                    ResultSet rs= pst.executeQuery();

                    if(rs.next()) {

                     return "{\"actualizacion\": \""+rs.getString(1)+"\"}";
                    }   
                    else{
                        return "{\"actualizacion\": \"-1\"}";
                    }
        
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return "{\"actualizacion\": \"-1\"}";
        
    }

    
    private String respuestaInmuebles(String token1, String token2){
        
     JSONArray json= null;      
                
     try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaInmuebles(?,?)")){
            pst.setString(1, token1);
            pst.setString(2, token2);
            ResultSet rs= pst.executeQuery();
            json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                
                 for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getString(column_name));
                 }
                 
                 json.put(obj);
            }            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return json.toString();
        
    }
    
    private String respuestaHabitaciones(String token1, String token2){
     
    JSONArray json= null;        
        
     try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaAreas(?,?)")){
            pst.setString(1, token1);
            pst.setString(2, token2);
            ResultSet rs= pst.executeQuery();
            json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                
                 for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getString(column_name));
                 }
                 
                 json.put(obj);
            }            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return json.toString();
    }
    
    private String respuestaHabitacionesPanel(String token){
     
    JSONArray json= null;        
        
     try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaAreasPanel(?)")){
            pst.setString(1, token);
            ResultSet rs= pst.executeQuery();
            json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                
                 for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getString(column_name));
                 }
                 
                 json.put(obj);
            }            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return json.toString();
    }
    
    private String respuestaDispositivos(String token1,String token2){
    
        JSONArray json= null;        
        
        try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaDispositivos(?,?)")){
            pst.setString(1, token1);
            pst.setString(2, token2);
            ResultSet rs= pst.executeQuery();
            json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                
                 for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getString(column_name));
                 }
                 
                 json.put(obj);
            }            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
     
        return json.toString();
        
    }
    
    private String respuestaDispositivosPanel(String token){
    
        JSONArray json= null;        
        
        try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaDispositivosPanel(?)")){
            pst.setString(1, token);
            ResultSet rs= pst.executeQuery();
            json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                
                 for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getString(column_name));
                 }
                 
                 json.put(obj);
            }            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
     
        return json.toString();
        
    }
    
    private String respuestaTopicos(String token1, String token2){
    
        JSONArray json= null;        
        
        try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaTopicos(?,?)")){
            pst.setString(1, token1);
            pst.setString(2, token2);
            ResultSet rs= pst.executeQuery();
            json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                
                 for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getString(column_name));
                 }
                 
                 json.put(obj);
            }            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
     
        return json.toString();
        
    }
    
    private String respuestaTopicosPanel(String token){
    
        JSONArray json= null;        
        
        try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaTopicosPanel(?)")){
            pst.setString(1, token);
            ResultSet rs= pst.executeQuery();
            json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                
                 for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getString(column_name));
                 }
                 
                 json.put(obj);
            }            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
     
        return json.toString();
        
    }
    
}