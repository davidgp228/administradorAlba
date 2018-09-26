<%-- 
    Document   : asignarRutas
    Created on : 18-abr-2018, 17:52:18
    Author     : mac
--%>

<%@page import="Control.Consultas" %>
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
                    <li>
                        <a href="cerco.jsp"> Crear cerco </a>
                    </li>
                     <li class="sidebar-brand">
                        <a href="asignarRutas.jsp"> Asignar rutas </a>
                    </li>
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->
            
            <!-- /.navbar-collapse -->
        <div id="page-wrapper">
            
            <center><h2><label >ASIGNAR RUTAS</label></h2> </center>    
            <br>
            <table class="table">
                <tr>
                    <th><center><label>SELECCIONAR RUTA:</label></center></th>
                    <th><center><label>ASIGNAR VEHìCULOS:</label></center></th>
                </tr>
                <tr>
                    <td align="center">
                    <center><select class="form-control" id="ruta">
                            <option>Seleccionar</option>
                            <%String rutas= Consultas.getRutas();%>
                            <%=rutas%>
                        </select></center> 
                    </td>
                    <td>
                    <center><select class="form-control" multiple id="imei">
                            <% String imei= Consultas.getIMEI(); %>
                            <%= imei %>
                        </select></center> 
                    </td>
                </tr>
                
            </table>
            
            <br>
            <center><button class="btn btn-primary"> GUARDAR </button></center> 
            <br><br>
                 
     <script type="text/javascript" src="js/cerco.js"></script>
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

