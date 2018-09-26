<%-- 
    Document   : Configuracion
    Created on : 30-ago-2017, 13:41:25
    Author     : mac
--%>
<%@page import="Control.Eventos"%>
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
    <script src="js/jquery.js"></script>
        <title>JSP Page</title>
    </head>
         <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">                
                <img src="images/gpsimage.png" width="100" height="80">
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="index.jsp" class="dropdown-toggle" ><i class="glyphicon glyphicon-home"></i> </a>
                </li>
                
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <%=sesion.getAttribute("nombre") %> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        
                        <li class="divider"></li>
                        <li>
                            <a href="Login?accion=cerrar"><i class="fa fa-fw fa-power-off"></i> Cerrar Sesión</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            
        </nav>

        <div id="page-wrapper">
            <br>
            
            <div style="text-align: center">
                <%if(Eventos.getAttrib(request,"mensaje").equals("no")) {%>
                    <label style="color:red;">La contraseña anterior no coincide</label>                
                    <br>
                    <br>
                <%}else if(Eventos.getAttrib(request, "mensaje").equals("si")) {%>
                    <label style="color:green;">Contraseña Actualizada</label>       
                    <br>
                    <br>
                <%}%>
            </div>
            <div>
                <center> <h3>Cambiar informacion</h3></center>
                <br>
                <form action="Login" method="post" name="contra"> 
                    <input type="hidden" name="accion" value="cambio">
                    <table class="table">
                        <thead>
                        <tr>
                            <td><label>Nuevo nombe de usuario</label></td>
                            <td><label>Contraseña Actual</label></td>         
                        </tr> 
                        </thead>
                        <tbody>
                        <tr>
                            <td><input class="form-control" type="text"     name="_correo"  id="idcorreo" required="" value="<%= sesion.getAttribute("usuario") %>"></td>
                            <td><input class="form-control" type="password" name="_passOld" id="p1"       required=""></td>
                        </tr>  
                        </tbody>
                        <thead>
                         <tr>
                            <td><label>Nueva Contraseña</label></td>
                            <td><label>Confirmar Nueva Contraseña</label></td>                        
                        </tr>    
                        </thead>
                        <tbody>
                        <tr>
                            <td><input class="form-control"  type="password" name="_passNew" id="p2" required=""></td>
                            <td><input class="form-control"  type="password" name="_passNew2" id="p3" required=""></td>
                        </tr> 
                        </tbody>
                        
                    </table>
                    
                </form>
                <center><button class="btn btn-primary" id="boton">Guardar cambios</button></center>  
            </div>

        </div>
                    
        <script>
            $(document).ready(function() {
                
                $('#boton').click(function(){
                    if($('#p1').val()==$('#p2').val()){
                        alert('La Nueva Contraseña no puede ser igual a la anterior');                        
                    }
                    else if($('#p2').val()==""){
                        alert('La nueva Contraseña no puede estar vacia');
                        
                    }else if($('#p2').val()==$('#p3').val()){
                        document.contra.submit();          
                    }else {
                        alert('La Nueva Contraseña no Coincide');
                    }
                });

            });
        </script>
        <!-- /#page-wrapper -->

    
    <!-- /#wrapper -->

    <!-- jQuery -->
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>
</html>
