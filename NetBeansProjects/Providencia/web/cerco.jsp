<%-- 
    Document   : cerco
    Created on : 13-abr-2018, 10:36:57
    Author     : mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Control.Consultas" %>
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
                    <li >
                        <a href="#"> </a>
                    </li>
                    <li >
                        <a href="index.jsp"> Crear mapa </a>
                    </li>
                     <li >
                        <a href="registrarAutos.jsp" > Registrar unidades </a>
                    </li>
                     <li >
                        <a href="registroConductores.jsp"> Registrar conductores </a>
                    </li>
                    <li >
                        <a href="Eventos.jsp"> GPS </a>
                    </li>
                    <li>
                        <a href="crearRuta.jsp"> Crear rutas </a>
                    </li>
                    <li class="sidebar-brand">
                        <a href="cerco.jsp"> Crear cerco </a>
                    </li>
                     <li >
                        <a href="asignarRutas.jsp"> Asignar rutas </a>
                    </li>
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->
            
            <!-- /.navbar-collapse -->
        <div id="page-wrapper">
      
            <table class="table">
                <tr>
                    <th>borrar cerca</th>
                    <th>Resetear mapa</th>
                </tr>
                <tr>
                    <td>
                        <button class="btn btn-danger" onclick="borrarCerco()">Borrar</button>
                    </td>
                    <td>
                        <button class="btn btn-danger" onclick="initMap()">Restart</button>
                    </td>
                    <td>
                        
                    </td>
                </tr>
                <tr>
                    <th>Seleccionar ruta:</th>
                    <th>Cerco</th>
                    <th>Generar</th>
                </tr>
                <tr>
                    <td><select id="idruta">
                            <option value="">Seleccionar</option>
                            <%String rutas=Consultas.getRutas(); %>
                            <%=rutas%>
                        </select>
                        <center> <img id="idcargando" src="images/loader.gif" alt="Cargando" style="display:none;width:10px;height:10px;"></center>
                    </td>
                    <td>
                        <button class="btn btn-primary" onclick="insertarCerco()">Agregar</button>
                    </td>
                    <td>
                        <button class="btn btn-primary" onclick="insertarBD()">Guardar en base</button>
                    </td>
                </tr>
            </table>
            
            <div id="map"> </div>
     
     <script type="text/javascript" src="js/cerco.js"></script>
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
