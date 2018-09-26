/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONObject;
import org.json.JSONException;
/**
 *
 * @author mac
 */
public class haversine {
    static JSONObject obj;
    
    public static void main (String[] args) throws JSONException{
    
    cargarRuta();
    System.out.println("Dato: "+obj);
    System.out.print("Latitude: " + obj.getJSONArray("Lat").length() + ", Longitude: " + obj.getJSONArray("Lon").get(0) + "\n");
    System.out.println("Metodo 1: La distancia entre a y b es: " + calculateDistanceInKilometer(19.0521716,-98.2093703,19.0572725,-98.2064628) + " Km");
    System.out.println("Metodo 2: La distancia entre a y b es: " + calculaDistanciaMetros(19.0543013,-98.2081472,19.0535711,-98.2067632) + " m");
    System.out.println("La distancia de normal a linea entre a y b es: " + normalToOrthodromic(19.0521716,-98.2093703,19.0572725,-98.2064628,19.0535711,-98.2067632) + " m");
    System.out.println("Desviacion: " + desviacionDeRuta(obj,19.192229724646864,-98.24085162341845) + " m.");
  }
    
    public static void cargarRuta(){
    obj = new JSONObject();
        try(Connection con= Conexion.getdatasource().getConnection();
             PreparedStatement pst= con.prepareStatement("SELECT Latitud,Longitud FROM RutaMarcadores WHERE FkRuta = 21;")){
             ResultSet rs= pst.executeQuery();
             
             while (rs.next()){
                  obj.accumulate("Lat", new Double(rs.getString(1)));
                  obj.accumulate("Lon", new Double(rs.getString(2)));
             }
            
        
        }catch (Exception e){
        
        }
    }
    
    
  public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371.0;
  public static double getMinValue(double[] array) {
    double minValue = array[0];
    for (int i = 1; i < array.length; i++) {
        if (array[i] < minValue) {
            minValue = array[i];
        }
    }
    return minValue;
}
  public static double calculateDistanceInKilometer(double userLat, double userLng,
    double venueLat, double venueLng) {

      double latDistance = Math.toRadians(userLat - venueLat);
      double lngDistance = Math.toRadians(userLng - venueLng);

      double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
        * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

      double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

      return AVERAGE_RADIUS_OF_EARTH_KM * c;
  }
  public static double calculaDistanciaMetros(double dLatA, double dLngA,
    double dLatB, double dLngB) {

      double LatA = Math.toRadians(dLatA);
      double LatB = Math.toRadians(dLatB);
      double LngA = Math.toRadians(dLngA);
      double LngB = Math.toRadians(dLngB);

      double partialUnitDist = Math.sin(LatA) * Math.sin(LatB) + Math.cos(LatA)
      * Math.cos(LatB) * Math.cos(LngA-LngB);

      double unitDist = Math.acos(partialUnitDist);

      return AVERAGE_RADIUS_OF_EARTH_KM * unitDist * 1000;
  }

  public static double calculaAnguloEntrePuntos(double dLatA, double dLngA,
    double dLatB, double dLngB) {

      double LatA = Math.toRadians(dLatA);
      double LatB = Math.toRadians(dLatB);
      double LngA = Math.toRadians(dLngA);
      double LngB = Math.toRadians(dLngB);

      double partialUnitDist = Math.sin(LatA) * Math.sin(LatB) + Math.cos(LatA)
      * Math.cos(LatB) * Math.cos(LngA-LngB);

      double unitDist = Math.acos(partialUnitDist);

      return unitDist;
  }

  public static double normalToOrthodromic(double dLatA, double dLngA,
    double dLatB, double dLngB, double dLatNormal, double dLngNormal){

      double c = calculaAnguloEntrePuntos( dLatA, dLngA, dLatB, dLngB);
      double cp = calculaAnguloEntrePuntos( dLatA, dLngA, dLatNormal, dLngNormal);
      double cpp = calculaAnguloEntrePuntos( dLatB, dLngB, dLatNormal, dLngNormal);
      double Ap = Math.acos((Math.cos(cpp)-Math.cos(cp)*Math.cos(c))/(Math.sin(cp)*Math.sin(c)));
      double Bp = Math.acos((Math.cos(cp)-Math.cos(cpp)*Math.cos(c))/(Math.sin(cpp)*Math.sin(c)));
      
      double dNormal;
      
      if (Ap > Math.PI/2){
          dNormal = cp;   
          return dNormal * AVERAGE_RADIUS_OF_EARTH_KM * 1000;
      }
      
      if(Bp > Math.PI/2){
         dNormal = cpp;
         return dNormal * AVERAGE_RADIUS_OF_EARTH_KM * 1000;
      }
      
      else{
          dNormal = Math.asin(Math.sin(cp)*Math.sin(Ap));
          return dNormal * AVERAGE_RADIUS_OF_EARTH_KM * 1000;
      }

  }
  
  public static double desviacionDeRuta(JSONObject rutaObj, double dLatN, double dLonN) throws JSONException{
    
    int puntos = rutaObj.getJSONArray("Lat").length();
    double derivaMinima = 0;
    
    if (puntos == 1){
        derivaMinima = calculaDistanciaMetros(rutaObj.getJSONArray("Lat").getDouble(0),rutaObj.getJSONArray("Lon").getDouble(0), dLatN, dLonN);
    }
    else{
        double[] derivas = new double[puntos-1];
    
        for (int i = 0; i < puntos-1; i++){
            derivas[i] = normalToOrthodromic(rutaObj.getJSONArray("Lat").getDouble(i), rutaObj.getJSONArray("Lon").getDouble(i),
                    rutaObj.getJSONArray("Lat").getDouble(i+1), rutaObj.getJSONArray("Lon").getDouble(i+1),
                    dLatN, dLonN);
            System.out.println("derivada ["+i+"] = LAT 1 "+rutaObj.getJSONArray("Lat").getDouble(i)+ " LON 1: "+rutaObj.getJSONArray("Lon").getDouble(i)
            +" LAT 2: "+ rutaObj.getJSONArray("Lat").getDouble(i+1)+" LON 2: "+rutaObj.getJSONArray("Lon").getDouble(i+1));
            System.out.println("Resultado= "+derivas[i]);
        
        }
        derivaMinima = getMinValue(derivas.clone()); 
        System.out.println("derivada minima= "+derivaMinima);
    }
    
    
    return derivaMinima;      
  }

}
