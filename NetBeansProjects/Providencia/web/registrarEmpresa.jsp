<%-- 
    Document   : registrarEmpresa
    Created on : 30-may-2018, 13:38:26
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
     String []datosempresa=null;
     datosempresa= Consultas.getDatosEmpresa(sesion.getAttribute("fkempresa").toString());
     
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
        <script type="text/javascript" src="js/registrarEmpresa.js"></script>
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
           
           <form id="form" action="registrarEmpresa" method="POST">
            <%String imagen=datosempresa==null?"":datosempresa[3];%>
            <input id="idimagen" name="_imagen" type="hidden" value="<%=imagen%>"> <!-- El valor de la imagen-->
            <input id="idaccion" name="accion"  type="hidden" value="actualizarEmpresa">
            
            <center><font size="5" color="#0033cc">Datos de la empresa</font></center> 
            
            <table class="table">
                <tr>
                    <td>
                        <center><font size="3" color="#0033cc">Nombre:</font></center> 
                    </td>
                    <td>
                        <center><font size="3" color="#0033cc">Descripcion:</font></center> 
                    </td>
                </tr>
                <tr>
                   <td>
                       <input id="idnombre" name="_nombre" class="form-control" type="text" required  value="<%=datosempresa==null?"":datosempresa[1]%>">
                   </td>
                   <td>
                       <input id="iddescripcion" name="_descripcion" class="form-control" type="text" required value="<%=datosempresa==null?"":datosempresa[2]%>">
                   </td>
               </tr>
               <tr>
                   <td colspan="2">
                        <center><font size="3" color="#0033cc">Logo de la empresa:</font></center> 
                    </td>
                  
                </tr>
                <tr>
                    <td height="130" colspan="2">
                        <center>
                              <center> <img id="imgSalida" width="120" height="120" src="<%=imagen%>" /></center>
                              <br>
                              <input name="file-input" id="file-input" type="file" />
                              <font size="2" >[ Max: 16 MB ]</font>
                        </center>
                    </td>
                </tr>
             
            </table>
            </form>
            <br>
            <center>
               <button id="btnactualzar" onclick="actualizarEmpresa()" class="btn btn-primary" >Actualizar</button>
               <button type="submit" id="btncancelar" class="btn btn-danger" onclick="recargarPagina()">Cancelar</button>
           </center>
            <br>
            
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
