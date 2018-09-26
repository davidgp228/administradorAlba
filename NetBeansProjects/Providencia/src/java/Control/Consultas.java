/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mac
 */
public class Consultas {
    
    private static String imeis=null;
    private static String rutas=null;
    private static String autosid=null;
    
    //** Cadenas de permisos
    private static final String mapa=        " <li> <a href=\"index.jsp\"> Crear ruta </a> </li>";
    private static final String autos=       " <li> <a href=\"registrarAutos.jsp\"> Registrar vehiculos </a> </li>";
    private static final String dispositivos=" <li> <a href=\"registrarDispositivos.jsp\"> Registrar dipositivos </a> </li>";
    private static final String conductores= " <li> <a href=\"registroConductores.jsp\"> Registrar conductores </a> </li>";
    private static final String horarios=    " <li> <a href=\"crearRuta.jsp\"> Horarios </a> </li>";
    private static final String monitoreo=   " <li> <a href=\"Eventos.jsp\"> Monitoreo </a> </li>";
    private static final String usuarios=    " <li> <a href=\"registrarUsuarios.jsp\"> Usuarios </a> </li>";
    private static final String empresa=     " <li> <a href=\"registrarEmpresa.jsp\"> Empresa </a> </li>";
    
    public static String estados[]={"Aguascalientes","Baja California","Baja California Sur","Campeche","Chiapas","Chihuahua",			
    "Ciudad de México","Coahuila","Colima","Durango","Guanajuato","Guerrero","Hidalgo","Jalisco","México","Michoacán",	
    "Morelos","Nayarit","Nuevo León","Oaxaca","Puebla","Querétaro","Quintana Roo","San Luis Potosí","Sinaloa","Sonora",				
    "Tabasco","Tamaulipas","Tlaxcala","Veracruz","Yucatán","Zacatecas"};
    
    public static String menu(int userID){
        String request="";
        String permiso="";
        
        try(Connection con= Conexion.getdatasource().getConnection();
           PreparedStatement pst= con.prepareStatement("SELECT Permiso FROM Permisos WHERE fkAdministrador=?")){
           pst.setInt(1, userID);
           
           ResultSet rs= pst.executeQuery();
           
               rs.next();
           
               permiso=rs.getString(1);
                
               
                switch (permiso) {
                    case "Administrador":
                        request = "index.jsp";
                        break;
                    case "mapa":
                        request = "index.jsp";
                        break;
                    case "autos":
                        request="registrarAutos.jsp";
                        break;
                    case "dispositivos":
                        request="registrarDispositivos.jsp";
                        break;
                    case "conductores":
                        request="registroConductores.jsp";
                        break ;
                    case "horarios":
                        request="crearRuta.jsp";
                        break;
                    case "monitoreo":
                        request="Eventos.jsp";
                        break;
                    default:
                        
                        break;
                }
           
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }

    public static String Permisos(int userID){
        
        String request="";
        String permiso="";
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("SELECT Permiso FROM Permisos WHERE fkAdministrador=?")){
            pst.setInt(1, userID);
            
            ResultSet rs= pst.executeQuery();
            
            consulta:
            while (rs.next()) {
                permiso=rs.getString(1);
                
                switch (permiso) {
                    case "Administrador":
                        request = (mapa+autos+dispositivos+conductores+horarios+monitoreo+usuarios+empresa);
                        break consulta;
                    case "mapa":
                        request+=mapa;
                        break;
                    case "autos":
                        request+=autos;
                        break;
                    case "dispositivos":
                        request+=dispositivos;
                        break;
                    case "conductores":
                        request+=conductores;
                        break;
                    case "horarios":
                        request+=horarios;
                        break;
                    case "monitoreo":
                        request+=monitoreo;
                        break;
                    default:
                        break;
                }
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }

    public static String[] getDatosEmpresa(String idEmpresa){
        String []request= new String[4];
        
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("SELECT * FROM Empresa WHERE IdEmpresa=?")){
            pst.setString(1, idEmpresa);
            ResultSet rs= pst.executeQuery();
            
            rs.next();
            
            request[0]= rs.getString(1);
            request[1]= rs.getString(2);
            request[2]= rs.getString(3);
            request[3]= rs.getString(4);
            
            
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return request;
    }
    
    public static String getIMEI(){
    
        System.out.print("Consultando imei");
        
        try( Connection con= Conexion.getdatasource().getConnection();
            // PreparedStatement pst= con.prepareStatement("SELECT DISTINCT IMEI FROM Marcadores"))
               PreparedStatement pst= con.prepareStatement("SELECT IMEI,Nombre FROM Dispositivos"))
        {
             ResultSet rs=pst.executeQuery();
             
             imeis="";
             String s,s2;
             while(rs.next()){
                 
             s=rs.getString(1);
             s2=rs.getString(2);
             
             //imeis+="<option >"+s+"</option> \n";
               imeis+="<option value='"+s+"'>"+s2+"</option> \n";
             }
            
        }   catch (SQLException ex) {    
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }    
        
        
        return imeis;
    }
    
     public static String getRutas(String fkempresa){
     
         
         try (Connection con= Conexion.getdatasource().getConnection();
             PreparedStatement pst= con.prepareStatement("SELECT ID,Nombre FROM Rutas WHERE fkEmpresa=?")){
             pst.setString(1, fkempresa);
             ResultSet rs=pst.executeQuery();
             
             rutas="";
             String s, s2;
             while(rs.next()){
             s=rs.getString(1);
             s2=rs.getString(2);
             rutas+="<option value='"+s+"'>"+s2 +"</option> \n";
             }
         
         }catch(Exception e){
         
         }
         return rutas;
     }
     
     public static String getUsuarios(String id, String fkempresa ){
         String request="";
         
         try(Connection con= Conexion.getdatasource().getConnection();
             PreparedStatement pst= con.prepareStatement("SELECT IdAdministrador, Nombre FROM Administrador WHERE fkEmpresa=?;")){
             pst.setString(1, fkempresa);
             ResultSet rs=pst.executeQuery();
             
             String s,s2;
             
             while(rs.next()){
                s=rs.getString(1);
                s2=rs.getString(2);
                
                if(s.equals(id))
                    request+="<option value='"+s+"' selected>"+s2+"</option>";
                else
                    request+="<option value='"+s+"'>"+s2+"</option>";
             }
             
         
         }catch(Exception e){
             e.printStackTrace();
         }
         
         return request;
     }
     
     public static String[] getDatosUsuario(String idUsuario){
         String request[]= new String [6];
         
         try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("SELECT * FROM Administrador WHERE IdAdministrador=?");
            PreparedStatement pstpermisos= con.prepareStatement("SELECT Permiso FROM Permisos WHERE fkAdministrador=?")){
            pst.setString(1, idUsuario);
             
            ResultSet rs= pst.executeQuery();
            rs.next();
             
            request[0]= rs.getString(1);//** idAdministrador
            request[1]= rs.getString(2);//** fkempresa
            request[2]= rs.getString(3);//** Nombre
            request[3]= rs.getString(4);//** Usuario
            request[4]= rs.getString(5);//** Password
            
            //** Obteniendo permisos del usuario
             pstpermisos.setString(1, idUsuario);
             ResultSet rs2= pstpermisos.executeQuery();
             String permisos="";
             while(rs2.next()){
                 permisos+=rs2.getString(1)+",";
             }
         
             request[5]= permisos;
             
         }catch(Exception e){
             e.printStackTrace();
         }
         
         return request;
     }
     
     public static String getDispositivos(String id, String fkempresa){
        String request="";
         
        try(Connection con= Conexion.getdatasource().getConnection();
            PreparedStatement pst= con.prepareStatement("SELECT d.IdDispositivo, d.FkAuto, d.IMEI, d.Nombre, d.Clave "
                    + "FROM Autos a INNER JOIN Dispositivos d ON a.IdAuto= d.FkAuto WHERE a.fkEmpresa=?")){
            pst.setString(1, fkempresa);
            ResultSet rs= pst.executeQuery();
            String s,s2,s3,s4,s5; 
            while(rs.next()){
                s=rs.getString(1);
                s2=rs.getString(2);
                s3=rs.getString(3);
                s4=rs.getString(4);
                s5=rs.getString(5);
                
                if(s.equals(id))
                     request+="<option value='"+s+"' selected>"+s4+"</option>";
                else
                     request+="<option value='"+s+"'>"+s4+"</option>";
            }
         
        }catch(Exception e){
             e.printStackTrace();
        }
         
         return request;
     }
     
     
      public static String[] getDatosDispositivo(String idseleccionado){
       String request[]= new String [5];
       
       try(Connection con= Conexion.getdatasource().getConnection();
           PreparedStatement pst= con.prepareStatement("SELECT * FROM Dispositivos WHERE IdDispositivo=?")){
           pst.setString(1, idseleccionado);
            
           ResultSet rs= pst.executeQuery();
           rs.next();
           
           request[0]= rs.getString(1);
           request[1]= rs.getString(2);
           request[2]= rs.getString(3);
           request[3]= rs.getString(4);
           request[4]= rs.getString(5);
           
       }catch(Exception e){
       e.printStackTrace();
               }
       
       return request;
      }
     
     public static String getUnidadesNoAsignadas(){
         String request="";
         
         try(Connection con = Conexion.getdatasource().getConnection();
             PreparedStatement pst=con.prepareStatement("SELECT IdAuto,Vehiculo,ID FROM Autos WHERE Asignado=0 ");){
             ResultSet rs= pst.executeQuery();
             
             while(rs.next()){
                 request+="<option value='"+rs.getString(1)+"'>"+rs.getString(2)+"-"+rs.getString(3)+"</option>";
             }
                     
         }catch(Exception e){
             e.printStackTrace();
         }
         
         return request;
     }
     
     public static String[] getDatosAuto(String id){
     String []request= new String [22];
         try(Connection con= Conexion.getdatasource().getConnection();
             PreparedStatement pst= con.prepareStatement("SELECT a.Vehiculo,a.Placas, a.Marca,a.Modelo, a.ClaseVehicular," +
"                     a.NIV,a.NumeroMotor, a.ID,a.Cilindros, a.Combustible, a.Ejes, a.Uso, a.OrigenVehiculo, " +
"                     a.Clase,a.Tipo,a.Litros,a.Toneladas, a.Personas,a.IdAuto, pa.Nombre,pa.ApellidoPaterno,pa.ApellidoMaterno " +
"                     FROM Autos a INNER JOIN PropietarioAuto pa ON a.IdAuto=pa.FkAuto " +
"                     WHERE a.IdAuto=?;")){
         
             pst.setString(1, id);
             ResultSet rs= pst.executeQuery();
               rs.next();
               request[0]=rs.getString(1);
               request[1]=rs.getString(2);
               request[2]=rs.getString(3);
               request[3]=rs.getString(4);
               request[4]=rs.getString(5);
               request[5]=rs.getString(6);
               request[6]=rs.getString(7);
               request[7]=rs.getString(8);
               request[8]=rs.getString(9);//cilindros
               request[9]=rs.getString(10);//Combustible
               request[10]=rs.getString(11);//Ejes
               request[11]=rs.getString(12);//Uso
               request[12]=rs.getString(13);//origen vehiculo
               request[13]=rs.getString(14);//Clase
               request[14]=rs.getString(15);//Tipo
               request[15]=rs.getString(16);//Litros
               request[16]=rs.getString(17);//Toneladas
               request[17]=rs.getString(18);//Personas
               request[18]=rs.getString(19);//ID Auto
               request[19]=rs.getString(20);//Nombre propietario
               request[20]=rs.getString(21);//Apellido paterno Propietario
               request[21]=rs.getString(22);//Apellido materno Propietario
               
             
         }catch(Exception e){
           e.printStackTrace();
         }
         System.out.println("Datos obtenidos "+request[0]);
     return request;
     }
     
      public static String[] getDatosConductor(String id){
      String []request= new String [22];
         try(Connection con= Conexion.getdatasource().getConnection();
             PreparedStatement pst= con.prepareStatement("SELECT * FROM Conductores WHERE IdConductor=?")){
         
             pst.setString(1, id);
             ResultSet rs= pst.executeQuery();
               rs.next();
               request[0]=rs.getString(1);//IdConductor
               request[1]=rs.getString(2);//ID
               request[2]=rs.getString(3);//Nombre
               request[3]=rs.getString(4);//ApellidoPaterno
               request[4]=rs.getString(5);//ApellidoMaterno
               request[5]=rs.getString(6);//Dia
               request[6]=rs.getString(7);//Mes
               request[7]=rs.getString(8);//Año
               request[8]=rs.getString(9);//Edad
               request[9]=rs.getString(10);//Sexo
               request[10]=rs.getString(11);//Numero licencia
               request[11]=rs.getString(12);//Tipo sangre
               request[12]=rs.getString(13);//Donador
               request[13]=rs.getString(14);//Calle
               request[14]=rs.getString(15);//NumeroExterior
               request[15]=rs.getString(16);//NumeroInerior
               request[16]=rs.getString(17);//CodigoPostal
               request[17]=rs.getString(18);//Colonia
               request[18]=rs.getString(19);//Municipio
               request[19]=rs.getString(20);//Estado
               request[20]=rs.getString(21);//Telefono
               request[21]=rs.getString(22);//Correo
               
             
         }catch(Exception e){
           e.printStackTrace();
         }
      return request;
      }
     
     public static String getidConductor(String idseleccionado, String fkEmpresa){
         String request="";
         
         try(Connection con = Conexion.getdatasource().getConnection();
             PreparedStatement pst= con.prepareStatement("SELECT IdConductor, Nombre, ApellidoPaterno,"
                     + "ApellidoMaterno FROM Conductores WHERE fkEmpresa=?")){
             pst.setString(1, fkEmpresa);
             ResultSet rs= pst.executeQuery();
             
             String s, s2, s3,s4;
             while(rs.next()){
                 s=rs.getString(1);
                 s2=rs.getString(2);
                 s3=rs.getString(3);
                 s4=rs.getString(4);
                if(idseleccionado.equals(s))
                request+="<option  value='"+s+"' selected>"+s2 +" "+s3+" "+s4+"</option> \n";
                else
                request+="<option value='"+s+"'>"+s2 +" "+s3+" "+s4+"</option> \n";
             }
         
         }catch(Exception e){
             e.printStackTrace();
         }
         
         return request;
     }
     
     public static String getidAuto(String idseleccionado){
         autosid="";
         try(Connection con= Conexion.getdatasource().getConnection();
             PreparedStatement pst=con.prepareStatement("select IdAuto, Vehiculo,ID from Autos;");){
             ResultSet rs=pst.executeQuery();
             String s, s2, s3;
             while(rs.next()){
                s=rs.getString(1);
                s2=rs.getString(2);
                s3=rs.getString(3);
                if(idseleccionado.equals(s))
                autosid+="<option  value='"+s+"' selected>"+s2 +"-"+s3+"</option> \n";
                else
                autosid+="<option value='"+s+"'>"+s2 +"-"+s3+"</option> \n";
             }
         
         }catch(Exception e){
           e.printStackTrace();
         }
     
         return autosid;
     }
     
     /**
      * 
     * @param fkruta
     * Retorna las coordenadas de la rutas creadas para visulizarlas en el mapa
     * @return JSONObject 
      */
     public static JSONObject getcoordenadasRutas(int fkruta){
         System.out.println("Fk ruta "+fkruta);
     JSONObject obj= new JSONObject();
     
     try(Connection con=Conexion.getdatasource().getConnection();
          PreparedStatement pst= con.prepareStatement("select Latitud, Longitud from RutaMarcadores where FkRuta = ?")){
          pst.setInt(1, fkruta);
          ResultSet rs= pst.executeQuery();
                    
          while(rs.next()){
               obj.accumulate("Lat",rs.getString("Latitud") );
               obj.accumulate("Lon",rs.getString("Longitud") );
          }
     
     }catch(Exception e){}
     System.out.print(">> JSON"+obj);
     return obj;
     }
    
     public static void insertarRutas(String nombre, String fkempresa, String[] coordenadas){
         String fkruta="";
         
     try(Connection con = Conexion.getdatasource().getConnection();
          PreparedStatement pstinsertarruta= con.prepareStatement("INSERT INTO Rutas (Nombre,Clave,fkEmpresa) "
                  + "VALUES (?,?,?);");
          PreparedStatement pstconsultarid= con.prepareStatement("SELECT MAX(ID) FROM Rutas;");
          PreparedStatement pstinsertarcoordenadas= con.prepareStatement("INSERT INTO RutaMarcadores"
                  + "(Latitud,Longitud, FkRuta) VALUES (?,?,?);")){
         
         pstinsertarruta.setString(1, nombre);
         pstinsertarruta.setString(2, "Default");
         pstinsertarruta.setString(3, fkempresa);
         pstinsertarruta.execute();
         
         ResultSet rs= pstconsultarid.executeQuery();
         rs.next();
         fkruta= rs.getString(1);
         
         for(String coordenada: coordenadas){
             String[] split = coordenada.split(",");
         
             pstinsertarcoordenadas.setString(1, split[0]);
             pstinsertarcoordenadas.setString(2, split[1]);
             pstinsertarcoordenadas.setString(3, fkruta);
             pstinsertarcoordenadas.execute();
         }
             
     }catch(Exception e){
     }
     }
     
     public static boolean eliminarRuta(int id){
         boolean request=false;
            
            try(Connection con= Conexion.getdatasource().getConnection();
                PreparedStatement pstEliminarCercos = con.prepareStatement("DELETE FROM RutaCercos WHERE FkRuta=?;");
                PreparedStatement pstEliminarMarcadores = con.prepareStatement("DELETE FROM RutaMarcadores WHERE FkRuta=?;");
                PreparedStatement pstEliminarRura= con.prepareStatement("DELETE FROM Rutas WHERE ID=?") ){
                
                pstEliminarCercos.setInt(1, id);
                pstEliminarCercos.execute();
                
                pstEliminarMarcadores.setInt(1, id);
                pstEliminarMarcadores.execute();
                
                pstEliminarRura.setInt(1, id);
                pstEliminarRura.execute();
                
                request = true;
            
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
      public static int parseint(String parse){
          int value=0;
                try{
                value=Integer.parseInt(parse);
                }catch(Exception e){}
          return value;
      }
}
