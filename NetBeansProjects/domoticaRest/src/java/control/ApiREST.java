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

import javax.ws.rs.QueryParam;
import org.json.JSONArray;
import org.json.JSONObject;
import clases.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/restapi")
public class ApiREST {
    
    @GET
    @Path("/descargar/imagen")
    @Produces({"image/png", "image/jpg", "image/gif"})
    public Response downloadImageFile(@QueryParam("ruta") String ruta) {
 
        // set file (and path) to be download
        File file = new File(ruta);        
 
        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"MyPngImageFile.jpg\"");
        return responseBuilder.build();
    }
    
@POST
@Path("/pdf/")
@Consumes({MediaType.MULTIPART_FORM_DATA})
public Response uploadPdfFile(@FormDataParam("file") InputStream fileInputStream,
                              @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception
{
    
    System.out.println("Datos>>> "+ fileMetaData);
    
    		// check if all form parameters are provided
		if (fileInputStream == null || fileMetaData == null)
			return Response.status(400).entity("Datos de formulario inválidos").build();
   
    //**Ruta Original server
    String UPLOAD_PATH = "/domoticaFiles/";
    
    //**Ruta Test server
    //String UPLOAD_PATH = "/Users/mac/Documents/";
    File RUTA_FINAL;
    try
    {
        int read = 0;
        byte[] bytes = new byte[1024];
 
        RUTA_FINAL= new File(UPLOAD_PATH + fileMetaData.getFileName());
        OutputStream out = new FileOutputStream(RUTA_FINAL);
        
        while ((read = fileInputStream.read(bytes)) != -1)
        {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    } catch (IOException e)
    {
        System.out.println("Error while uploading file. Please try again !!");
         return Response.ok(
                respuestaFail(), 
                MediaType.APPLICATION_JSON).build();
    }
    //return Response.ok("Datos cargados exitosamente !!").build();
     return Response.ok(
                "{\"update\": \""+RUTA_FINAL.getAbsolutePath()+"\"}", 
                MediaType.APPLICATION_JSON).build();
}
    
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response login(@QueryParam("usuario") String usuario, @QueryParam("contrasena") String contrasena,
            @QueryParam("identificador") String identificador, @QueryParam("tipo") String tipo, 
            @QueryParam("modelo") String modelo, @QueryParam("versionSO") String versionSO){
    return Response.ok(
                respuestaLogin(usuario, contrasena, identificador, tipo,  modelo, versionSO ), 
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
    @Path("/alarma")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response alarmaGET(@QueryParam("token") String token){
    return Response.ok(
                respuestaAlarma(token), 
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
    @Path("/estadoMovil")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response estadoMovil(@QueryParam("token") String token){
    return Response.ok(
                respuestaEstadoMovil(token), 
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
    @Path("/actualizarInmueble")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response actualizarInmueble(@QueryParam("token1") String token1, @QueryParam("token2") String token2
    ,@QueryParam("token3") String token3){
    return Response.ok(
                respuestaInmueblesActualizar(token1, token2, token3), 
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
    
    
    
    private String respuestaLogin(String usuario, String contrasena, String identificador,String tipo, String modelo,String versionSO) {
        
        try(Connection con= conexion.getdatasource().getConnection();
                PreparedStatement pst= con.prepareStatement("call validarUsuario(?,?)");
                PreparedStatement insertarMovil= con.prepareStatement("call insertarMovil(?,?,?,?,?,?)")
            )
        {
            pst.setString(1, usuario);
            pst.setString(2, contrasena);
            ResultSet rs= pst.executeQuery();
            
            if(rs.next()) {
                
                //***Se inserta informacion del smarphone en la bse de datos
                insertarMovil.setString(1, identificador);
                insertarMovil.setString(2, tipo);
                insertarMovil.setString(3, modelo);
                insertarMovil.setString(4, versionSO);
                insertarMovil.setString(5, "activo");
                insertarMovil.setString(6, rs.getString(1));
                insertarMovil.execute();
                
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
           // PreparedStatement pst= con.prepareStatement("SELECT "+contrato.idTabla+" FROM "+contrato.nombreTabla+
             //       " WHERE "+contrato.numeroContrato+"=? AND "+contrato.claveContrato+"=? AND "
               //     + contrato.estadoServicio+" = ?");
            PreparedStatement pst = con.prepareStatement("call validarContrato(?,?,?)");
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
    
    private String respuestaEstadoMovil(String token) {
        
        try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call estadoMovil(?)");
            )
        {
            
            pst.setString(1, token);
            ResultSet rs= pst.executeQuery();
            
            if(rs.next()) {
                return "{\"estado\": \""+rs.getString(1)+"\"}";
            }   
            else{
                return "{\"estado\": \"0\"}";
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return "{\"estado\": \"-1\"}";
        
    }
    
    private String respuestaInmueblesActualizar(String token,String token2,String token3){
        
        System.out.println("Datos "+token);
                
     try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call ModificarArea(?,?,?)")){
            pst.setString(1, token);
            pst.setString(2, token2);
            pst.setString(3, token3);
            
            pst.execute();
            return "{\"actualizacion\": 1 }";
            
                    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return "{\"actualizacion\": 0 }";
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
 
     private String respuestaAlarma(String token){
        
     JSONArray json= null;      
                
     try(Connection con= conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("call consultaAlarma(?)")){
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
    
    private String respuestaOK(){
        return "{\"update\": \"1\"}";
    }
    private String respuestaFail(){
        return "{\"update\": \"-1\"}";
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