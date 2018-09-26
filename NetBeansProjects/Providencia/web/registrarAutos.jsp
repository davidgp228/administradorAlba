<%-- 
    Document   : registrarAutos
    Created on : 02-may-2018, 11:42:22
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
    
    String auto=Consultas.getAttrib(request, "_buscarunidad");
    String []datosAuto=null;
    System.out.println("auto "+auto);
    if(!auto.equals("")){
    datosAuto= Consultas.getDatosAuto(auto);
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
     
    <body >
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
        
        <form action="registrarAutos.jsp" method="POST">
           <table class="table">
            <tr>
                <td><center>Seleccionar para editar o eliminar:</center></td>
                <td>
                    <select  onchange="this.form.submit()" id="idAuto" name="_buscarunidad" class="form-control">
                        <option value="">Seleccionar</option>     
                    <%
                        String idseleccionado=datosAuto==null?"":datosAuto[18];
                        String idautos=Consultas.getidAuto(idseleccionado); %>
                    <%=idautos%>
                    </select>
                </td>
            </tr>
        </table>
        </form>
        
        <form action="registrarAutos" method="post" id="form">
            
            <input type="hidden" name="accion" id="idaccion" value="insertarAuto">  
            <input type="hidden" name="_idauto" id="idauto" value="">  
            
            <table class="table">
                <tr>
                    <td width="80%">
                       <center><p><font size="5" color="#0033cc">Datos del vehìculo</font></p> </center>  
                    </td>
                     <td width="20%">
                     <input type="text" class="form-control" name="_ID" value="<%=datosAuto==null?"":""+datosAuto[7]%>" placeholder="ID" required>
                     </td>
                </tr>
            </table>   
          
         <table class="table">
             <tr>
                 <td>
                     <center>
                         <input type="text" class="form-control" name="_vehiculo"  placeholder="Vehìculo" value="<%=datosAuto==null?"":""+datosAuto[0]%>" required>
                     </center>
                  </td>
                  <td>
                     <center>
                         <input type="text" class="form-control" name="_placas" placeholder="Placas" value="<%=datosAuto==null?"":""+datosAuto[1]%>" required>
                     </center>
                  </td>            
             </tr>
             <tr>
                  <td>
                     <center>
                         <input type="text" class="form-control" name="_marca" placeholder="Marca" value="<%=datosAuto==null?"":""+datosAuto[2]%>" required>
                     </center>
                  </td>
                   <td>
                     <center>
                         <input type="text" class="form-control" id="idmodelo" name="_modelo"  placeholder="Modelo" value="<%=datosAuto==null?"":""+datosAuto[3]%>" required>
                     </center>
                  </td>
             </tr>
               <tr>
                  <td>
                     <center>
                         <input type="text" class="form-control" name="_clasevehicular" placeholder="Clase vehicular" value="<%=datosAuto==null?"":""+datosAuto[4]%>" required>
                     </center>
                  </td>
                   <td>
                     <center>
                     <input type="text" class="form-control" name="_NIV"  placeholder="NIV" value="<%=datosAuto==null?"":""+datosAuto[5]%>" required>
                     </center>
                  </td>
             </tr>
             <tr>
                 <td>
                    <center>
                    <input type="text" class="form-control" name="_numeromotor"  placeholder="Numero de motor" value="<%=datosAuto==null?"":""+datosAuto[6]%>" required>
                    </center>
                 </td>
                
             </tr>
         </table>
         
          <center><p><font size="5" color="#0033cc">Especificaciones</font></p> </center> 
          <table class="table">
           <tr>
               <th><center>Cilindros:</center></th>
               <th><center>Combustible:</center></th>
               <th><center>Ejes:</center></th>
           </tr>
           <tr>
               <td>
                   <select class="form-control" name="_cilindros"> 
                       <% 
                       int cilindro=(datosAuto==null?0:Integer.parseInt(datosAuto[8]));     
                       for(int c=1; c<=4; c++){
                       if(cilindro==c){%>
                       <option selected ><%=c%></option>
                       <%}
                       else{%>
                       <option ><%=c%></option>
                       <%}}%>
                   </select>
               </td>
                <td>
                    <select class="form-control" id="idcombustible" name="_combustible">
                       <%String combustible=datosAuto==null?"":datosAuto[9]; %> 
                       <option <%= combustible.equals("Gasolina")?"selected":""%> >Gasolina</option>
                       <option <%= combustible.equals("Diesel")?"selected":""%>>Diesel</option>
                       <option <%= combustible.equals("Gas")?"selected":""%>>Gas</option>
                       <option <%= combustible.equals("Electrico")?"selected":""%>>Electrico</option>
                       <option <%= combustible.equals("Electrico/Gasolina")?"selected":""%>>Electrico/Gasolina</option>
                   </select>
               </td>
                <td>
                  
                   <select class="form-control" name="_ejes">
                       <% int eje=(datosAuto==null?0:Integer.parseInt(datosAuto[10])); 
                       for(int c=1; c<=6; c++){
                       if(eje==c){ %>
                       <option selected><%=c%></option>
                       <%}else{%>
                       <option><%=c%></option>
                       <%}}%>
                   </select>
               </td>
           </tr>
            <tr>
               <th><center>Uso:</center></th>
               <th><center>Origen del vehìculo:</center></th>
               <th><center>Clase:</center></th>
           </tr>
           <tr>
               <td>
                   <select class="form-control" name="_uso">
                       <%String uso=datosAuto==null?"":datosAuto[11]; %>
                       <option <%= uso.equals("Particular")?"selected":""%>>Particular</option>
                       <option <%= uso.equals("Usos multiples")?"selected":""%>>Usos multiples</option>
                       <option <%= uso.equals("Carga")?"selected":""%>>Carga</option>
                   </select>
               </td>
                <td>
                   <select class="form-control" name="_origen">
                       <%String origen=datosAuto==null?"":datosAuto[12]; %>
                       <option <%= origen.equals("Nacional")?"selected":""%>>Nacional</option>
                       <option <%= origen.equals("Extranjero")?"selected":""%>>Extranjero</option>
                   </select>
               </td>
               <td>
                   <select class="form-control" name="_clase">
                       <%String clase=datosAuto==null?"":datosAuto[13]; %>
                       <option <%= clase.equals("Automovil")?"selected":""%>>Automovil</option>
                       <option <%= clase.equals("Camion")?"selected":""%>>Camion</option>
                       <option <%= clase.equals("Autobùs")?"selected":""%>>Autobùs</option>
                       <option <%= clase.equals("Remolque")?"selected":""%>>Remolque</option>
                       <option <%= clase.equals("Tracto-camion")?"selected":""%>>Tracto-camion</option>
                       <option <%= clase.equals("Camioneta")?"selected":""%>>Camioneta</option>
                       <option <%= clase.equals("Motocicleta")?"selected":""%>>Motocicleta</option>
                   </select>
               </td>
           </tr>
           <tr>
                <th><center>Tipo:</center></th>
           </tr>
           <tr>
                <td>
                   <select class="form-control" name="_tipo">
                       <%String tipo=datosAuto==null?"":datosAuto[14]; %>
                       <option <%= tipo.equals("Crossover (CUV)")?"selected":""%>>Crossover (CUV)</option>
                       <option <%= tipo.equals("Hatchback")?"selected":""%>>Hatchback</option>
                       <option <%= tipo.equals("Sedán")?"selected":""%>>Sedán</option>
                       <option <%= tipo.equals("SUV")?"selected":""%>>SUV</option>
                       <option <%= tipo.equals("Camioneta Pickup")?"selected":""%>>Camioneta Pickup</option>
                       <option <%= tipo.equals("Wagon")?"selected":""%>>Wagon</option>
                   </select>
               </td>
           </tr>
           
       </table>
          
         <center><p><font size="5" color="#0033cc">Capacidad</font></p> </center> 
          <table class="table">
              <tr>
                  <th><center>Litros:</center></th>
                  <th><center>Toneladas:</center></th>
                  <th><center>Personas:</center></th>
              </tr>
              <tr>
                  <td>
                     
                      <select class="form-control" name="_litros">
                          <%int litros=(datosAuto==null?0:Integer.parseInt(datosAuto[15]));
                             for(int c=0; c<=10000; c+=500 ){
                             if(litros==c){%>
                             <option selected><%=c%></option>
                             <%}else{%>
                              <option ><%=c%></option>
                          <%}}%>
                      </select>
                  </td>
                  <td>
                      
                      <select class="form-control" name="_toneladas">
                           <% int toneladas=(datosAuto==null?0:Integer.parseInt(datosAuto[16]));
                           for(int c=0; c<=38; c++){
                            if(toneladas==c){%>
                            <option selected ><%=c%></option>
                           <%}else{%>
                           <option ><%=c%></option>
                          <%}}%>
                      </select>
                  </td>
                  <td>
                      
                      <select class="form-control" name="_personas">
                           <% int personas=(datosAuto==null?0:Integer.parseInt(datosAuto[17]));
                            for(int c=0; c<=40; c++){
                            if(personas==c){ %>
                                <option selected><%=c%></option>
                            <%}else{%>
                                <option><%=c%></option>
                            <%}}%>
                      </select>
                  </td>
              </tr>
          </table>
          
          <center><p><font size="5" color="#0033cc">Datos del propietario</font></p> </center>  
          <table class="table">
              <tr>
                  <td>
                       <input type="text" class="form-control" name="_nombre" value="<%=datosAuto==null?"":""+datosAuto[19]%>"  placeholder="Nombre" required>
                  </td>
                  <td>
                       <input type="text" class="form-control" name="_apellidopaterno" value="<%=datosAuto==null?"":""+datosAuto[20]%>" placeholder="Apellido paterno" required>
                  </td>
                  <td>
                       <input type="text" class="form-control" name="_apellidomaterno" value="<%=datosAuto==null?"":""+datosAuto[21]%>" placeholder="Apellido materno" required>
                  </td>
              </tr>
          </table>
          </form>
          
            <form action="registrarAutos.jsp" method="post" id="form2">
               <%datosAuto=null;%>
            </form>
                      
          <center>
              <button id="btnguardar" form="form" class="btn btn-primary" >Guardar</button>
              <button id="btnguardar" onclick="actualizarAuto()" class="btn btn-primary" >Actualizar</button>
              <button id="btnguardar" onclick="eliminarAuto()" class="btn btn-primary" >Eliminar</button>
              <button id="btncancelar" form="form2" class="btn btn-danger" >Cancelar</button>
          </center>
                      
    </div>
                   
                    
    <!-- /#wrapper -->
    <script type="text/javascript" src="js/registrarAutos.js"></script>
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


