<%-- 
    Document   : registrarUsuarios
    Created on : 28-may-2018, 11:23:08
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
       
           <form action="registrarUsuarios.jsp" method="POST">
        <table class="table">
            <tr>
                <td><center>Seleccionar para editar o eliminar:</center></td>
                <td>
                    <select id="idusuario"  onchange="this.form.submit()" id="idbuscardispositivo" name="_buscarusuario" class="form-control">
                        <option value="">Seleccionar</option>    
                           <% 
                            String idSeleccionado= datosUsuario==null?"":datosUsuario[0];
                            String dispositivos =Consultas.getUsuarios(idSeleccionado,sesion.getAttribute("fkempresa").toString()); %>
                           <%= dispositivos%>
                    </select>
                </td>
            </tr>
        </table>
        </form>
           
        <form id="form" action="registroUsuarios" method="POST">    
            
            <input id="idaccion" name="accion" type="hidden" value="insertarUsuario">
            <input id="idpermisosenviar" name="_permisos" type="hidden" value="">
            <input id="idusuarioenviar" name="_idusuario" type="hidden">
            
           <center><font size="5" color="#0033cc">Datos del usuario</font></center> 
           <table class="table">
               <tr>
                   <td>
                       <center><font size="3" color="#0033cc">Nombre:</font></center> 
                   </td>
                   <td>
                       <center><font size="3" color="#0033cc">Usuario</font></center> 
                   </td>
               </tr>
               <tr>
                   <td>
                       <input id="idnombre" name="_nombre" class="form-control" type="text" required  value="<%=datosUsuario==null?"":datosUsuario[2]%>">
                   </td>
                   <td>
                       <input id="idnombreusuario" name="_nombreusuario" class="form-control" type="text" required value="<%=datosUsuario==null?"":datosUsuario[3]%>">
                   </td>
               </tr>
                <tr>
                   <td>
                       <center><font size="3" color="#0033cc">Contraseña:</font></center> 
                   </td>
                   <td>
                       <center><font size="3" color="#0033cc">Confirmar contraseña</font></center> 
                   </td>
               </tr>
               <tr>
                   <td>
                       <input id="idnuevacontrasena" name="_nuevacontrasena" required class="form-control" type="password" value="<%=datosUsuario==null?"":datosUsuario[4]%>">
                   </td>
                   <td>
                       <input id="idconfirmarcontrasena" name="_confirmarcontrasena" required class="form-control" type="password" value="<%=datosUsuario==null?"":datosUsuario[4]%>">
                   </td>
               </tr>
               
           </table>
  
           <center><font size="5" color="#0033cc">Asignar permisos</font></center>  
           <input id="idpermisos" type="hidden" value="<%=datosUsuario==null?"":datosUsuario[5]%>">
           <table class="table table-bordered">
                <thead class="alert alert-info">
                 <tr>
                  <th>Permisos</th>
                  <th>Permitir/Denegar</th>
                  </tr>
                </thead>
               <tr>
                  <td><font size="3" color="#0033cc">Administrador</font></td>
                  <td><input id="idtodos" type="checkbox" value="Administrador" onchange="permisoTodos()" class="form-control"></td>
               </tr>
               <tr>
                  <td><font size="3" color="#0033cc">Rutas</font></td>
                  <td><input id="idrutas" value="mapa" type="checkbox" class="form-control"></td>
               </tr>
               <tr>
                  <td><font size="3" color="#0033cc">Autos</font></td>
                  <td><input id="idauto" value="autos" type="checkbox" class="form-control"></td>
               </tr>
               <tr>
                  <td><font size="3" color="#0033cc">Dispositivos</font></td>
                  <td><input id="iddispositivos" value="dispositivos" type="checkbox" class="form-control"></td>
               </tr>
               <tr>
                  <td><font size="3" color="#0033cc">Conductores</font></td>
                  <td><input id="idconductor" value="conductores" type="checkbox" class="form-control"></td>
               </tr>
               <tr>
                  <td><font size="3" color="#0033cc">Horarios</font></td>
                  <td><input id="idhorario" value="horarios" type="checkbox" class="form-control"></td>
               </tr>
               <tr>
                  <td><font size="3" color="#0033cc">Monitoreo</font></td>
                  <td><input id="idmonitoreo" value="monitoreo" type="checkbox" class="form-control"></td>
               </tr>
              
           </table>
           </form> 
            <%if(datosUsuario!=null){%> 
                    <script>
                        editarPermisos($("#idpermisos").val());
                    </script>
            <%}%>
            
         <br>
           <center>
              <button id="btnguardar" onclick="insertarUsuario()" class="btn btn-primary" >Guardar</button>
              <button id="btnactualzar" onclick="actualizarUsuario()" class="btn btn-primary" >Actualizar</button>
              <button id="btneliminar" onclick="eliminarUsuario()" class="btn btn-primary" >Eliminar</button>
              <button type="submit" id="btncancelar" class="btn btn-danger" onclick="recargarPagina()">Cancelar</button>
          </center>
                     
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



