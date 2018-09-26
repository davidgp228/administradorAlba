/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

/**
 *
 * @author mac
 */
public  class ubicacion {
      static  Rectangle2D marcador,area;
        
        //** Propiedades del rectangulo
        static double[] marcadorSuperior  = new double[2];
        static double[] marcadorMedio     = new double[2];
        static double[] marcadorInferior  = new double[2];
        static double anchoArea=0;
        static double altoArea=0;
        
        //** Propiedades del marcador
        static double anchoMarcador=0.001;
        static double altoMarcador=0.001;
        
        
       /**
        * 
        * @param latitud; tipo coordenada, ubicacion actual del marcador.
        * @param longitud: tipo coordenada, ubicacion actual del marcador.
        * @param marcadorSuperior : esquina(latitud, longitud) superior del area
        * @param marcadorMedio : esquina(latitud, longitud) media del area
        * @param marcadorInferior : esquina(latitud, longitud) inferior del area
        * @return boolean 
        * Valida que el auto(Marcador) se encuentre en alguna de las areas proporvionada por la base de datos
        * Determinada por 3 marcadores con sus repectivas latitudes y longitudes
        */ 
       public static boolean validarUbicacion(double latitud, double longitud, double marcadorSuperior[],double marcadorMedio[], double marcadorInferior[]){
       boolean request=false;
       
       //**Area asignar 
       ubicacion.marcadorSuperior=marcadorSuperior.clone();
       ubicacion.marcadorMedio=marcadorMedio.clone();
       ubicacion.marcadorInferior=marcadorInferior.clone();    
       
       anchoArea=calcularDistancia(ubicacion.marcadorSuperior[0],ubicacion.marcadorSuperior[1],ubicacion.marcadorMedio[0],ubicacion.marcadorMedio[1]);
       altoArea=calcularDistancia(ubicacion.marcadorMedio[0],ubicacion.marcadorMedio[1],ubicacion.marcadorInferior[0],ubicacion.marcadorInferior[1]);
        
        if(comparar(latitud,longitud,ubicacion.marcadorMedio[0],ubicacion.marcadorMedio[1])){
         request=true;
        }
              
       return request;
       } 
       
        /**
         * 
         * @param lat1
         * @param lon1
         * @param lat2
         * @param lon2
         * @return 
         * Comparar la ubicacion del auto(marcador) con una determinada area
         */
       public static boolean comparar(double lat1,double lon1, double lat2, double lon2){
    
        boolean res= false;
        marcador= new  Rectangle2D.Double(lat1,lon1,anchoMarcador,altoMarcador);
        System.out.println("punto ="+marcador.getBounds2D());
        area=new  Rectangle2D.Double(lat2,lon2,anchoArea,altoArea);
        System.out.println("area ="+area.getBounds2D());
                
        if(area.intersects(marcador))
        {
            //Colisiono
            System.out.println("Colisionaron");
            res=true;
        }
        else
        {
            System.out.println("No Colisionaron");
        }
        
        return res;
    }
       
       /**
        * 
        * @param x1
        * @param y1
        * @param x2
        * @param y2
        * @return 
        * Calcula la distancia entre dos coordenadas, para obtener la longitud de la linea
        */
      public static double calcularDistancia(double x1, double y1, double x2, double y2){
        double value, elevarX, elevarY, restaY , restaX, sumaXY;
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
      
}
