<%-- 
    Document   : registrarDispositivos
    Created on : 19-may-2018, 12:42:53
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
    
    String dispositivo=Consultas.getAttrib(request, "_buscardispositivo");
    String []datosDispositivo=null;
    
    if(!dispositivo.equals("")){
    datosDispositivo= Consultas.getDatosDispositivo(dispositivo);
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
       
           <form action="registrarDispositivos.jsp" method="POST">
        <table class="table">
            <tr>
                <td><center>Seleccionar para editar o eliminar:</center></td>
                <td>
                    <select id="iddispositivo"  onchange="this.form.submit()" id="idbuscardispositivo" name="_buscardispositivo" class="form-control">
                        <option value="">Seleccionar</option>    
                        <% 
                            String idSeleccionado= datosDispositivo==null?"":datosDispositivo[0];
                            String dispositivos =Consultas.getDispositivos(idSeleccionado,sesion.getAttribute("fkempresa").toString()); %>
                        <%=dispositivos%>
                    </select>
                </td>
            </tr>
        </table>
          </form>  
                    
         <form action="registroDispositivos" method="post" id="form">
            <input type="hidden" id="idaccion" name="accion" value="insertarDispositivo">
            <input type="hidden" id="id" name="_id" value="">
         <center><font size="5" color="#0033cc">Datos del dispositivo</font></center>   
         <table class="table">
             <tr>
                 <td>
                     <center><font size="3" color="#0033cc">IMEI o serie:</font></center> 
                 </td>
                   <td>
                     <center><font size="3" color="#0033cc">Nombre:</font></center> 
                 </td>
                  
             </tr>
             <tr>
                 <td>
                     <input type="text" class="form-control" id="idimei" name="_imei" value="<%=datosDispositivo==null?"":datosDispositivo[2]%>"  placeholder="  " required>
                 </td>
                  <td>
                     <input type="text" class="form-control" id="idnombre" name="_nombre" value="<%=datosDispositivo==null?"":datosDispositivo[3]%>" placeholder="  " required>
                 </td>
             </tr>
             <tr>
                 <td>
                     <center><font size="3" color="#0033cc">Modelo:</font></center> 
                 </td>
                 <td>
                     <center><font size="3" color="#0033cc">Asignar automovil:</font></center> 
                 </td>
             </tr>
             <tr>
                  <td>
                     <input type="text" class="form-control" id="idclave" name="_clave" value="<%=datosDispositivo==null?"":datosDispositivo[4]%>" placeholder="  " required>
                 </td>
                 <td>
                      <select  id="idauto" name="_idauto"  class="form-control">
                        <option value="">Seleccionar</option> 
                         <%
                        String autoseleccionado= datosDispositivo==null?"":datosDispositivo[1];     
                        String idautos=Consultas.getidAuto(autoseleccionado); %>
                        <%=idautos%>
                    </select>
                 </td>
             </tr>
         </table>
         </form>
         
         <br>
           <center>
               <button id="btnguardar" onclick="insertarDispositivo()" class="btn btn-primary" >Guardar</button>
              <button id="btnactualzar" onclick="actualizarDispositivo()" class="btn btn-primary" >Actualizar</button>
              <button id="btneliminar" onclick="eliminarDispositivo()" class="btn btn-primary" >Eliminar</button>
              <button type="submit" id="btncancelar" class="btn btn-danger" onclick="recargarPagina()">Cancelar</button>
          </center>
         <br>
    </div>
           </div>        
                    
    <!-- /#wrapper -->
    <script type="text/javascript" src="js/registrarDispositivo.js"></script>
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


