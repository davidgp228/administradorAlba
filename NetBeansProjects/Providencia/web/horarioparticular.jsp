<%-- 
    Document   : horarioparticular
    Created on : 30-ago-2018, 12:58:27
    Author     : mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Control.Consultas"%>
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
    
    String []datosUsuario=null;
    String usuario=Consultas.getAttrib(request, "_buscarusuario");
    
    if(!usuario.equals("")){
        datosUsuario= Consultas.getDatosUsuario(usuario);
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

        <link rel="stylesheet" href="pick/css/pikaday.css">
        <script src="js/jquery.js"></script>
        
          <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/simple-sidebar.css" rel="stylesheet">
        <script type="text/javascript" src="js/registrarUsuarios.js"></script>
        
         <style>
          .idtabla thead th{
             background: lightgray; 
            }
            .idtabla thead th:first-of-type{
            border-top-left-radius: 10px; 
             }

        .idtabla thead th:last-of-type{
            border-top-right-radius: 10px; 
            }
       </style>
        
    </head>
     
    <body >
        <div id="wrapper" >

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
       
       <center><font size="5" color="#0033cc">Horarios particulares</font></center>
       <br>
       
      
       
       <table id="idtabla" class="table"> 
           <thead> 
               <tr class="info"> 
                   <th scope="col">Fecha</th> 
                   <th scope="col">Conductor</th> 
                   <th scope="col">Auto</th> 
                   <th scope="col">Ruta</th> 
               </tr> 
           </thead> 
           <tbody> <tr> 
                   <th>
                       <center><font size="3" color="#0033cc">Desde</font></center>
                       <input id="idfechainicial" class="form-control" name="fechainicial" type="date" >
                       <center><font size="3" color="#0033cc">Hasta</font></center>
                       <input id="idfechafinal" class="form-control" name="fechafinal" type="date" >
                   </th> 
                   <th>
                       <select id="idConductor" size="6" name="_buscarconductor" class="form-control">   
                            <%
                                String idconductor=Consultas.getidConductor("",sesion.getAttribute("fkempresa").toString()); %>
                            <%=idconductor%>
                         </select>
                   </th> 
                   <th>
                        <select  id="idAuto" size="6" name="_buscarunidad" class="form-control">
                            <%
                                String idautos=Consultas.getidAuto(""); 
                            %>
                            <%=idautos%>
                         </select>
                   </th>
                   <th>
                       <select class="form-control" size="6" id="idruta" >
                            <% String rutas=Consultas.getRutas(sesion.getAttribute("fkempresa").toString()); %>
                            <%=rutas%>
                         </select>
                   </th> 
               </tr> 
           </tbody> 
       </table>
               
        <br>                 
        <center><button class="btn btn-primary">Guardar</button></center>
                         
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




