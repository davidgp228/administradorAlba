<%-- 
    Document   : Eventos
    Created on : 28-ago-2017, 11:00:24
    Author     : mac
--%>

<%@page import="Control.Consultas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    HttpSession sesion=request.getSession();
    int userId;
    if(sesion==null){
    System.out.println("No ha iniciado sesion");
    response.sendRedirect("Login.jsp");
    return;
    }
    else{
    if(sesion.getAttribute("userId")==null){
        response.sendRedirect("Login.jsp");
        System.out.println("Sesion userId no existe");
        return;
    }
        userId=Integer.parseInt(sesion.getAttribute("userId").toString());
        if(userId==-1){
            System.out.println("sesion2");
            response.sendRedirect("Login.jsp");
            return ;
        }
    }
    
                response.setHeader("Cache-Control","no-cache");
                response.setHeader("Cache-Control","no-store");
                response.setHeader("Pragma","no-cache");
                response.setDateHeader ("Expires", 0);
                if(sesion.getAttribute("userId")==null){
                    response.sendRedirect(request.getContextPath() + "/Login.jsp");

                }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ALBA Geolocalizacion</title>
           <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="css/sb-admin.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <link rel="stylesheet" href="pick/css/pikaday.css">
        <script src="js/jquery.js"></script>
        
          <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/simple-sidebar.css" rel="stylesheet">
        
     <style>
           #right-panel {
        font-family: 'Roboto','sans-serif';
        line-height: 30px;
        padding-left: 10px;
      }

      #right-panel select, #right-panel input {
        font-size: 15px;
      }

      #right-panel select {
        width: 100%;
      }

      #right-panel i {
        font-size: 12px;
      }
      #map {
       height: 450px;
       width: 100%;
       margin-top: 20px;
       margin-bottom: 10px;
      }
       #directions-panel {
        margin-top: 10px;
        background-color: #FFEE77;
        padding: 10px;
      }
    </style>
  
    
    </head>
     
    <body>
       <div id="wrapper">

         <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
             <div class="navbar-header">
                 <a href="#menu-toggle" class="btn btn-secondary" id="menu-toggle">
            <img src="images/inicioblanco.png" width="30" height="30" border="0">
            </a>
            </div>
                       
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <%=sesion.getAttribute("nombre") %> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="Configuracion.jsp"><i class="fa fa-fw fa-gear"></i> Cambiar Contraseña</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                          <a href="Login?accion=cerrar"><i class="fa fa-fw fa-power-off"></i> Cerrar Sesión</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <center>
            <div class="nav collapse navbar-collapse">
                <img src="images/cancerberologo.png" width="50" height="50" border="0">
            </div>
           </center>
           </nav>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
               <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                   <%= Consultas.Permisos(Integer.parseInt(sesion.getAttribute("userId").toString())) %>
            </ul>
        </div>
            
        <!-- /#sidebar-wrapper -->
            <!-- /.navbar-collapse -->
    <div id="page-wrapper">
        
      
         <table class="table">
             
             <tr>                
                <td>
                    <select id="idruta" >
                    <option value="">Seleccionar ruta</option>
                    <% String rutas=Consultas.getRutas(sesion.getAttribute("fkempresa").toString()); %>
                    <%=rutas%>
                    </select>
                    <center> <img id="idcargando" src="images/loader.gif" alt="Cargando" style="display:none;width:10px;height:10px;"></center>
                </td>
                
                <td>
                      <center> 
                      <button class="btn btn-primary" onclick="ActualizarMarcador()">Actualizar mapa</button>
                      </center>
                </td>
                
                <td>
                <center>
                    
                </center>
                </td>
                
             </tr>
             
            <tr>
              <th>Empezar (s):</th>
              <th>IMEI:</th>
              
            </tr>
            <tr>
              <td>
                    <select  id="idtiempo" name="tiempo">
                    <option>0</option>  <option>5</option>
                    <option>10</option><option>15</option><option>30</option>
                    <option>60</option><option>120</option><option>240</option>
                    </select>
              </td>
              <td>
                     <select id="idimei" name="imei">
                     <option value="">Seleccionar</option>
                     <%String con=Consultas.getIMEI(); %>
                     <%= con %>
                     </select>
              </td>
             
            </tr>
        </table> 
                    
        
                        
    <div id="map"></div>
    
    <center><font size="4" color="#0033cc">Informacion del automovil</font></center> 
    <table class="table">
        <tr>
            <td> <center><font size="3" color="#0033cc">Velocidad</font></center>  </td>
            <td> <center><font size="3" color="#0033cc">RPM</font></center>  </td> 
        </tr>
        <tr>
            <td><input id="idvelocidad" name="_idvelocidad" type="text" class="form-control" value="0.0" disabled></td>    
            <td><input id="idrpm" name="_idrpm" type="text" class="form-control" value="0.0" disabled></td>    
        </tr>
        <tr>
            <td> <center><font size="3" color="#0033cc">Temperatura motor</font></center>  </td>
            <td> <center><font size="3" color="#0033cc">Fecha</font></center>  </td>
        </tr>
        <tr>
            <td><input id="idtemperatura" name="_idtemperatura" type="text" class="form-control" value="0.0 °C" disabled></td>    
            <td><input id="idfechaauto" name="_idfechaauto" type="text" class="form-control" value="00/00/00" disabled></td>
        </tr>
    </table>
    
    <center><font size="4" color="#0033cc">Buscar historicos</font></center> 
    <table class="table">
        <tr>
            <th>Filtrar</th>
            <th>Polilineas</th>
        </tr>
        
        <tr>
             <td>
                  <input class="form-control" id="idfiltro" type="checkbox" onchange="filtro()" >
              </td>
              <td>
                    <input class="form-control" type="checkbox" id="idpoli" name="poli" value="hora">
              </td>
        </tr>
        
    </table>
    
    
     <div id="filtrar" style="display:none">           
         <table class="table">
                    <tr>
                        <th>Fecha</th>
                        <th>Hora</th>
                        <th>Minutos</th>
                        <th>Confirmar</th>
                    </tr>
                    <tr>
                        <td>
                              <input id="idfecha" name="fecha" type="date" >
                        </td>
                        <td>
                        <select id="idhora" class="selectpicker">
                        <% for(int i=0; i<24;i++){ %>
                        <option> <%=i%> </option>
                        <%}%>
                        </select>
                        </td>
                        <td>
                        <select id="idminuto">
                        <% for(int i=0; i<60;i++){ %>
                        <option> <%=i%> </option>
                        <%}%>
                        </select>
                        </td>
                        <td>
                            <button class="btn btn-primary" onclick="filtrarFecha()">BUSCAR</button>
                        </td>
                    </tr>
        </table>
        </div>  
    
    <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">!Alerta el automovil esta fuera de ruta¡</h4>
        </div>
        <div class="modal-body">
            <label id="txtalert"></label>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        </div>
      </div>
      
    </div>
  </div>
    
    <!-- Aquí empieza la cabecera de la web 
    <div id="right-panel">
    <div>
    <b>Inicio de la ruta:</b>
    <select id="start">
      <option value="Tlaxcala de Xicohténcatl, Tlaxcala">Tlaxcala</option>
      <option value="Puebla, 72270 Puebla, Pue.">Puebla</option>
      <option value="Xicohtzinco, 90780 Tlax.">Xicohtzinco, Tlaxcala</option>
      <option value="Panzacola, Papalotla, Tlax.">Panzacola, Papalotla, Tlax.</option>
      <option value="Zacatelco, Tlaxcala">Zacatelco, Tlaxcala</option>
      <option value="Barrio de Guardia, 90750 Zacatelco, Tlax.">Barrio de Guardia</option>
    </select>
    <br>
    <b>Puntos intermedios:</b> <br>
    <i>(Ctrl+Click or Cmd+Click for multiple selection)</i> <br>
    <select multiple id="waypoints">
     <option value="Tlaxcala de Xicohténcatl, Tlaxcala">Tlaxcala</option>
      <option value="Puebla, 72270 Puebla, Pue.">Puebla</option>
      <option value="Xicohtzinco, 90780 Tlax.">Xicohtzinco, Tlaxcala</option>
      <option value="Panzacola, Papalotla, Tlax.">Panzacola, Papalotla, Tlax.</option>
      <option value="Zacatelco, Tlaxcala">Zacatelco, Tlaxcala</option>
      <option value="Barrio de Guardia, 90750 Zacatelco, Tlax.">Barrio de Guardia</option>
    </select>
    <br>
    <b>Destino de la ruta:</b>
    <select id="end">
      <option value="Puebla, 72270 Puebla, Pue.">Puebla</option>
      <option value="Tlaxcala de Xicohténcatl, Tlaxcala">Tlaxcala</option>
      <option value="Xicohtzinco, 90780 Tlax.">Xicohtzinco, Tlaxcala</option>
      <option value="Panzacola, Papalotla, Tlax.">Panzacola, Papalotla, Tlax.</option>
      <option value="Zacatelco, Tlaxcala">Zacatelco, Tlaxcala</option>
      <option value="Barrio de Guardia, 90750 Zacatelco, Tlax.">Barrio de Guardia</option>
    </select>
    <br>
    <center><input type="submit" id="submit" value="Trazar ruta"></center> 
    </div>
    <div id="directions-panel"></div>
    </div>-->
    
    <script type="text/javascript" src="js/Eventos.js"></script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0ppATA8sAqduBh10Gus822FkD887KhIM&callback=initMap">
    </script>        
    </div>
    </div>
                    
    <!-- /#wrapper -->
    
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
     <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Menu Toggle Script -->
    <script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    </script>

    </body>
</html>
