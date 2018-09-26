<%-- 
    Document   : index
    Created on : 24-ago-2017, 9:54:40
    Author     : mac
--%>

<%@page import="Control.Consultas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<% 
    System.out.println("Consultando... index");
    HttpSession sesion;
    sesion=null;
    sesion=request.getSession();
    
    int userId;
    if(sesion==null){
    System.out.println("No ha iniciado sesion");
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('No ha iniciado sesion');");
                     out.println("</script>");
    response.sendRedirect("Login.jsp");
    return;
    }
    else{
    if(sesion.getAttribute("userId")==null){
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('El usuario no existe');");
                     out.println("</script>");
        response.sendRedirect("Login.jsp");
        System.out.println("Sesion userId no existe");
        return;
    }
        userId=Integer.parseInt(sesion.getAttribute("userId").toString());
        if(userId==-1){
            System.out.println("sesion2");
            out.println("<script type=\"text/javascript\">");
                     out.println("alert('Inicio de sesion invalida');");
                     out.println("</script>");
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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
       height: 550px;
       width: 100%;
       margin-top: 20px;
       margin-bottom: 10px;
      }
      #idtabla {
        padding: 5,5,5,5; width:100%; height: 100%;
      }
    
      #btneliminar {
         margin-right: 20px;
      }
      /* Optional: Makes the sample page fill the window. */
      
     
      *{
        box-sizing: border-box;
      }
      .floating-panel {
        position: absolute;
        top: 200px;
        left: 43%;
        z-index: 5;
       /* background-color: #fff;*/
       /* padding: 5px;*/
       /* border: 1px solid #999;*/
        text-align: center;
        line-height: 30px;
        padding-left: 10px;
      }
     /* Use a media query to add a break point at 800px: */
        @media screen and (max-width:800px) {
          .floating-panel {
            top: 240px;  
            left: 35%;
          }
        }
   
    </style>
  
    
    </head>
     
    <body>
       <div id="wrapper">
           
       
        <!-- Navigation -->
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
            
            <!-- Asignar permisos a los usuarios-->
            
            <ul class="sidebar-nav">
                
                <%= Consultas.Permisos(Integer.parseInt(sesion.getAttribute("userId").toString())) %>
                
                  <!--  <li >
                        <a href="#"> </a>
                    </li>
                   
                    <li class="sidebar-brand">
                        <a href="index.jsp"> Crear mapa </a>
                    </li>
                   
                    <li >
                        <a href="registrarAutos.jsp"> Registrar unidades </a>
                    </li>
                    <li >
                        <a href="registrarDispositivos.jsp"> Registrar dipositivos </a>
                    </li>
                     <li >
                        <a href="registroConductores.jsp"> Registrar conductores </a>
                    </li>
                    <li >
                        <a href="Eventos.jsp"> GPS </a>
                    </li>
                    <li >
                        <a href="crearRuta.jsp"> Crear rutas </a>
                    </li>
                    <li >
                        <a href="cerco.jsp"> Crear cerco </a>
                    </li>
                     <li >
                        <a href="asignarRutas.jsp"> Asignar rutas </a>
                    </li>-->
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->
            
            <!-- /.navbar-collapse -->
        <div id="page-wrapper">
           <input type="hidden" id="coordenadas" value="100" >
           <table id="idtabla">
               <thead>
                      <td  style="padding-bottom:10px" colspan="2"><center><font size="4" color="#0033cc">Agregar rutas</font></center> </td>
               </thead>
            <thead> 
            <td style="padding-bottom:10px" colspan="2">
                 <center>
                     <input type="text" class="form-control" id="idnombreruta"  placeholder="Nombre de la ruta" required>
                 </center>
            </td>
           </thead>
          <thead>
          <td>
          <center>
              <button class="btn btn-primary" onclick="insertarCoordenadas()"><i class="fa fa-cloud-upload"></i> Guardar en BD</button>
              <img id="idcargando" src="images/loader.gif" alt="Cargando" style="display:none;width:10px;height:10px;">
          </center>
          </td>
          <td> 
            <center>
              <a id="link" class="btn btn-primary"> <i class="fa fa-download"></i> Descargar txt</a>
            </center>
          </td>
          </thead>
          <tbody>
              <tr >
                  <td colspan="3">
                      <div class="floating-panel">
                          <button id="btneliminar" class="btn btn-danger" onclick="EliminarCoordenadas()"><i class="glyphicon glyphicon-remove-sign"></i> Remover linea</button>
                      </div>
                       <div id="map"> </div>
                  </td>
              </tr>
          </tbody>
          <thead>
            <td style="padding-bottom:10px" colspan="2"><center><font size="4" color="#0033cc">Eliminar rutas</font></center> </td>
          </thead>
          <tbody>
                <td style="padding-bottom:20px" >
                    <select class="form-control" id="idruta" >
                    <option value="">Seleccionar ruta</option>
                    <% String rutas=Consultas.getRutas(sesion.getAttribute("fkempresa").toString()); %>
                    <%=rutas%>
                    </select>
                    <center> <img id="idcargandorutas" src="images/loader.gif" alt="Cargando" style="display:none;width:10px;height:10px;"></center>
                </td>
                <td>
                <center> <button class="btn btn-danger" onclick="eliminarRutas()">Eliminar</button></center>
                </td>
          </tbody>
          <thead>
              <tr>
                  <th style="display:none" colspan="3"><center>Coordenadas </center></th>
              </tr>
          </thead>
          <tbody>
              <tr>
                  <td colspan="3"><center><label style="display:none" id="lbldatos">---- </label></center> </td>           
              </tr>                  
          </tbody>
      </table>   
                    
                    
     <center><font size="4" color="#0033cc">Cargar archivo txt</font></center>    
     <center><input  type="file"  class="form-control-file" id="file-input" /></center>
     <h5>Contenido del archivo:</h5>
     <pre id="contenido-archivo"></pre> 
          
     <script type="text/javascript" src="js/MarcarRuta.js"></script>
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
