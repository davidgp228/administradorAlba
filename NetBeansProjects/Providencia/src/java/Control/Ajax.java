/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mac
 */
@WebServlet(name = "Ajax", urlPatterns = {"/Ajax"})
public class Ajax extends HttpServlet {

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
                HttpSession sesion=request.getSession();
                int fkruta=0;
                String imei="0";
                String consulta = request.getParameter("consulta");
                String dato = request.getParameter("coordenadas");

                String fecha = request.getParameter("fecha");
                String nombreruta= request.getParameter("nombreruta");
                String[] cercos = request.getParameterValues("cercos[]");
                String[] rutas=request.getParameterValues("rutas[]");


                imei = request.getParameter("imei");
                fkruta= Consultas.parseint(request.getParameter("idruta"));

                System.out.println("Consulta= " + consulta);
                
                  switch(consulta){
                    case "GuardarCoordenadas": 
                        GuardarCoordenadas(dato);
                    break;
                    case "ActualizarMarcador": 
                     // out.print(Eventos.ActualizarMarcador(imei,13));
                        out.print(Eventos.ActualizarMarcadorHaversine(""+imei,fkruta,30));
                    break;
                    case "filtrarFechas": 
                      out.print(Eventos.ActualizarMultiplesMarcadores(fecha, ""+imei));
                      break;
                    case "insertarCercos": 
                      for (String cerco1 : cercos) {
                        String[] split = cerco1.split(" ");

                        System.out.println("Insercion "+cerco.insertarCerco(fkruta, split[0] , split[1], split[2]));
                      }
                     break;
                    case "getcoordenadasrutas":
                        out.print(Consultas.getcoordenadasRutas(fkruta));
                        break;
                    case "insertarRutas":
                        Consultas.insertarRutas(nombreruta,sesion.getAttribute("fkempresa").toString(), rutas);
                        break;
                    case "eliminarRuta":
                         out.print(Consultas.eliminarRuta(fkruta));
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

    public static void GuardarCoordenadas(String coordenadas) throws IOException{
     String ruta = "/Users/mac/Documents/Coordenadas.txt";
        File archivo = new File(ruta);
        BufferedWriter bw;
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(coordenadas);
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(coordenadas);
        }
        bw.close();
    }
}