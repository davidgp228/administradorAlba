<%-- 
    Document   : crearRuta
    Created on : 18-abr-2018, 18:50:22
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
    </head>
     
    <body>
       <div id="wrapper">

          <!-- Navigation -->
      <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation"  >
            <!-- Brand and toggle get grouped for better mobile display -->
             <div class="nav navbar-left top-nav">
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
            
            <div id="idseleccionarunidad">
                <center><p><font size="5" color="#0033cc" >Seleccionar la unidad para asignar horarios</font></p> </center> 
            <table class="table">
                <tr>
                    <td>
                <center><button id="idserviciospublicos" class="btn btn-default" value="servicios publicos" onclick="seleccionarTipo(this.id)">Servicios publicos</button> </center> 
                    </td>
                   
                </tr>
                <tr>
                     <td>
                <center><button id="idserviciosprivados" class="btn btn-default" value="servicios privados" onclick="seleccionarTipo(this.id)">Servicios privados</button> </center> 
                    </td>
                </tr>
                <tr>
                    <td>
                <center><button id="idparticulares" class="btn btn-default" value="particulares" onclick="seleccionarTipo(this.id)">   Particulares   </button> </center> 
                    </td>
                </tr>
                 <tr>
                    
                    <td>
                 <center><button id="idpaqueterias" class="btn btn-default" value="paqueterias" onclick="seleccionarTipo(this.id)">   Paqueterias   </button> </center> 
                    </td>
                </tr>
                 <tr>
                     <td colspan="2">
                 <center><button id="idagentesviajeros" class="btn btn-default" value="agentes viajeros" onclick="seleccionarTipo(this.id)">Agentes viajeros</button> </center> 
                    </td>
                </tr>
            </table>
            </div>
            
            <div id="idhorarioparticular" style="display: none">
                  <table class="table">
                <tr>
                    <td>
                         <center><p><font size="4" color="#0033cc">Seleccionar automovil</font></p> </center> 
                    </td>
                    <td>
                             <select id="idauto" class="form-control" name="_auto" >
                            <option value="">Seleccionar</option>
                            <%String idautos=Consultas.getidAuto("0"); %>
                            <%=idautos%>
                            </select>
                        </td>
                </tr>
            </table>
                            
            <center><p><font id="idnuevohorariosemanal" size="5" color="#0033cc">Asignar horario semanal</font></p> </center> 
                <table class="table">
                    <tr>
                        <td>
                            <center><p><font size="3" color="#0033cc">Dia</font></p> </center> 
                        </td>
                         <td>
                            <center><p><font size="3" color="#0033cc">Ruta</font></p> </center> 
                        </td>
                        <td colspan="2">
                            <center><p><font size="3" color="#0033cc">Hora salida/llegada</font></p> </center> 
                        </td>
                         <td>
                            <center><p><font size="3" color="#0033cc">Conductor</font></p> </center> 
                        </td>
                        <td>
                            <center><p><font size="3" color="#0033cc">Accion</font></p> </center> 
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <center><p><font size="3" color="#0033cc">Lunes</font></p> </center>
                        </td>
                        <td>
                             <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                            <%String rutas=Consultas.getRutas(sesion.getAttribute("fkempresa").toString()); %>
                             <%=rutas%>
                        </select>
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                             <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <% String idconductor=Consultas.getidConductor("0",sesion.getAttribute("fkempresa").toString()); %>
                             <%=idconductor%>
                        </select>
                        </td>
                        <td>
                        <center> <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button></center>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <center><p><font size="3" color="#0033cc">Martes</font></p> </center>
                        </td>
                         <td>
                             <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                             <%=rutas%>
                        </select>
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                         <td>
                             <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <%=idconductor%>
                        </select>
                        </td>
                        <td>
                        <center> <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button></center>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <center><p><font size="3" color="#0033cc">Miercoles</font></p> </center>
                        </td>
                         <td>
                             <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                             <%=rutas%>
                        </select>
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                          <td>
                             <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <%=idconductor%>
                        </select>
                        </td>
                        <td>
                        <center> <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button></center>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <center><p><font size="3" color="#0033cc">Jueves</font></p> </center>
                        </td>
                         <td>
                             <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                             <%=rutas%>
                        </select>
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                          <td>
                             <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <%=idconductor%>
                        </select>
                        </td>
                        <td>
                        <center> <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button></center>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <center><p><font size="3" color="#0033cc">Viernes</font></p> </center>
                        </td>
                         <td>
                             <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                             <%=rutas%>
                        </select>
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                          <td>
                             <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <%=idconductor%>
                        </select>
                        </td>
                        <td>
                        <center> <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button></center>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <center><p><font size="3" color="#0033cc">Sabado</font></p> </center>
                        </td>
                         <td>
                             <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                             <%=rutas%>
                        </select>
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                          <td>
                             <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <%=idconductor%>
                        </select>
                        </td>
                        <td>
                        <center> <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button></center>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <center><p><font size="3" color="#0033cc">Domingo</font></p> </center>
                        </td>
                         <td>
                             <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                             <%=rutas%>
                        </select>
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                          <td>
                             <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <%=idconductor%>
                        </select>
                        </td>
                        <td>
                        <center> <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button></center>
                        </td>
                    </tr>
                </table>
                 <center><p><font id="idnuevohorariosemanal" size="5" color="#0033cc">Programar salidas</font></p> </center> 
                 <table class="table">
                     <tr>
                         <td>
                             <center><p><font size="3" color="#0033cc">Fecha</font></p> </center>
                         </td>
                         <td colspan="2">
                             <center><p><font size="3" color="#0033cc">Hora salida/llegada</font></p> </center>
                         </td>
                         <td>
                             <center><p><font size="3" color="#0033cc">Ruta</font></p> </center>
                         </td>
                          <td>
                             <center><p><font size="3" color="#0033cc">Conductor</font></p> </center>
                         </td>
                          <td>
                             <center><p><font size="3" color="#0033cc">Accion</font></p> </center>
                         </td>
                     </tr>
                     <tr>
                         <td>
                             <input id="idfechaincial" class="form-control" type="date" name="_fechainicial"> 
                         </td>
                         <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                        <td>
                            <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                        </td>
                         <td>
                             <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                             <%=rutas%>
                        </select>
                        </td>
                         <td>
                             <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <%=idconductor%>
                        </select>
                        </td>
                        <td>
                        <center> <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button></center>
                        </td>
                     </tr>
                     
                 </table>
            </div>
            
            <div id="idCargarDatos" style="display:none ">
            <table class="table">
                <tr>
                    <td>
                         <center><p><font size="4" color="#0033cc">Seleccionar ruta</font></p> </center> 
                    </td>
                    <td>
                        <select class="form-control" id="idruta">
                            <option value="">Seleccionar</option>
                             <%=rutas%>
                        </select>
                    </td>
                </tr>
            </table>
                        
            <center><p><font id="idnuevohorario" size="5" color="#0033cc"></font></p> </center> 
            <table class="table">
                  <tr>
                      <td width="50%">
                         <center><p><font size="2" color="#0033cc">Fecha inicial</font></p> </center>  
                         <input id="idfechaincial" class="form-control" type="date" name="_fechainicial"> 
                      </td>
                      <td width="50%" colspan="2">
                        <center><p><font size="2" color="#0033cc">Hasta</font></p> </center> 
                        <input id="idfechafinal" class="form-control" type="date" name="_fechafinal">
                      </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <center><p><font size="2" color="#0033cc">Dias</font></p> </center> 
                        <center> <div class="form-check">
                        <input type="checkbox" name="_lunes" value="Bike">
                        <label class="form-check-label" >LUN </label>
                        <input type="checkbox" name="_martes" value="Bike">
                        <label class="form-check-label" >MAR </label>
                        <input type="checkbox" name="_miercoles" value="Bike">
                        <label class="form-check-label" >MIE </label>
                        <input type="checkbox" name="_jueves" value="Bike">
                        <label class="form-check-label" >JUE </label>
                        <input type="checkbox" name="_viernes" value="Bike">
                        <label class="form-check-label" >VIE </label>
                        <input type="checkbox" name="_sabado" value="Bike">
                        <label class="form-check-label"">SAB </label>
                        </div></center>
                    </td>
                </tr>
                <tr>
                     <td align="bottom">
                         <center><p><font size="2" color="#0033cc">Hora salida</font></p> </center> 
                        <input id="idhorasalida" class="form-control" type="time" name="_horasalida">
                    </td>
                    <td colspan="2">
                        <center><p><font size="2" color="#0033cc">Hora llegada</font></p> </center> 
                        <input id="idhorallegada" class="form-control" type="time" name="_horallegada">
                    </td>
                </tr>
                <tr>
                     <td>
                        <center><p><font size="2" color="#0033cc">Auto</font></p> </center>  
                        <select id="idauto" class="form-control" name="_auto" >
                            <option value="">Seleccionar</option>
                        <%=idautos%>
                        </select>
                    </td>
                    
                     <td>
                        <center><p><font size="2" color="#0033cc">Conductor</font></p> </center> 
                        <select id="idconductor" class="form-control" name="_conductor">
                            <option value="">Seleccionar</option>
                             <%=idconductor%>
                        </select>
                     </td>
                     <td colspan="2" align="center">
                       <center><p><font size="2" color="#0033cc">Agregar </font></p> </center>   
                       <button class="btn btn-default" src="images/add.png" onclick="agregarDatos()"> <img src="images/add.png" width="20" height="20" border="0"></button>
                    </td>
                </tr>
              
                <tr>
                    <td colspan="3" align="center">
                        <button class="btn btn-primary" onclick="insertarHorario()">Insertar</button>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="center">
                        <ul id="idlistadatos" class="list-group">
                        </ul>
                    </td>
                </tr>
            </table>           
                
            <center><p><font size="5" color="#0033cc">Horarios establecidos</font></p> </center> 
            <table class="table">
                <tr>
                     <td>
                         <center><p><font size="3" color="#0033cc">Fecha</font></p> </center> 
                    </td>
                     <td>
                         <center><p><font size="3" color="#0033cc">Hora salida</font></p> </center> 
                    </td>
                     <td>
                         <center><p><font size="3" color="#0033cc">Hora llegada</font></p> </center> 
                    </td>
                     <td>
                         <center><p><font size="3" color="#0033cc">Auto</font></p> </center> 
                    </td>
                    <td>
                         <center><p><font size="3" color="#0033cc">Conductor</font></p> </center> 
                    </td>
                    <td>
                         <center><p><font size="3" color="#0033cc">Accion</font></p> </center> 
                    </td>
                </tr>  
            </table>
             </div>
           
                        <!--
            <style>
            div.scrollmenu {
                background-color: #333;
                overflow: auto;
                white-space: nowrap;
            }

            div.scrollmenu a {
                display: inline-block;
                color: white;
                text-align: center;
                padding: 14px;
                text-decoration: none;
            }

            div.scrollmenu a:hover {
                background-color: #777;
            }
            </style>
            
           
                 <div class="scrollmenu">
                    <a href="#home">Home</a>
                    <a href="#news">News</a>
                    <a href="#contact">Contact</a>
                    <a href="#about">About</a>
                    <a href="#support">Support</a>
                    <a href="#blog">Blog</a>
                    <a href="#tools">Tools</a>  
                    <a href="#base">Base</a>
                    <a href="#custom">Custom</a>
                    <a href="#more">More</a>
                    <a href="#logo">Logo</a>
                    <a href="#friends">Friends</a>
                    <a href="#partners">Partners</a>
                    <a href="#people">People</a>
                    <a href="#work">Work</a>
                  </div>    -->
            
                        <table class="table">
                            <tr>
                                <td><p><font size="">Lunes</font></p></td>
                                <td><p><font size="">Martes</font></p></td>
                                <td><p><font size="">Miercoles</font></p></td>
                                <td><p><font size="">Jueves</font></p></td>
                                <td><p><font size="">Viernes</font></p></td>
                                <td><p><font size="">Sabado</font></p></td>
                                <td><p><font size="">Domingo</font></p></td>
                            </tr>
                            <tr>
                                <td colspan="2"> <center><button value="12:00 a 03:55,disable" id="1" class="btn btn-default btn-block" onclick="cambiarColor(this.id)"> 12:00 a 03:50</button></center></td>
                                <td></td>
                                <td></td>
                                <td> <center><button value="12:00 a 03:55,disable" id="2" class="btn btn-default btn-block" onclick="cambiarColor(this.id)"> 12:00 a 03:50</button></center></td>
                            </tr>
                            <tr>
                                <td> <center><button value="12:00 a 03:55,disable" id="4" class="btn btn-default btn-block" onclick="cambiarColor(this.id)"> 12:00 a 03:50</button></center></td>
                                <td colspan="2"> <center><button value="12:00 a 03:55,disable" id="3" class="btn btn-default btn-block" onclick="cambiarColor(this.id)"> 12:00 a 03:50</button></center></td>
                                <td> <center><button value="12:00 a 03:55,disable" id="5" class="btn btn-default btn-block" onclick="cambiarColor(this.id)"> 12:00 a 03:50</button></center></td>
                                <td></td>
                               <td colspan="2"> <center><button value="12:00 a 03:55,disable" id="6" class="btn btn-default btn-block" onclick="cambiarColor(this.id)"> 12:00 a 03:50</button></center></td>
                            </tr>
                            
                        </table>
                        
         <script type="text/javascript" src="js/crearRuta.js"></script> 
     
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
