<%-- 
    Document   : registroConductores
    Created on : 03-may-2018, 19:54:24
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
    String tiposangre[]={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    
    String conductor=Consultas.getAttrib(request, "_buscarconductor");
    String []datosConductor=null;
    System.out.println("conductor "+conductor);
    if(!conductor.equals("")){
    datosConductor= Consultas.getDatosConductor(conductor);
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
           <form action="registroConductores.jsp" method="POST">
           <table class="table">
            <tr>
                <td><center>Seleccionar para editar o eliminar:</center></td>
                <td>
                    <select  onchange="this.form.submit()" id="idConductor" name="_buscarconductor" class="form-control">
                        <option value="">Seleccionar</option>     
                    <%
                        String idseleccionado=datosConductor==null?"":datosConductor[0];
                        String idautos=Consultas.getidConductor(idseleccionado,sesion.getAttribute("fkempresa").toString()); %>
                    <%=idautos%>
                    </select>
                </td>
            </tr>
        </table>
        </form>
        
        <form action="registroConductores" method="post" id="form">  
            
            <input type="hidden" name="accion" id="idaccion" value="insertarConductor">  
            <input type="hidden" name="_idconductor" id="idconductor" value="">  
            <input type="hidden" name="_sexo" id="idsexo" value="">  
            <input type="hidden" name="_donador" id="iddonador" value=""> 
            
        <table class="table">
            <tr>
                <td width="80%" >
            <center><font size="5" color="#0033cc">Datos del conductor</font></center>   
                </td>
                <td width="20%" >
                    <input type="text" class="form-control" name="_id"  placeholder="ID" value="<%=datosConductor==null?"":""+datosConductor[1]%>" required>
                </td>
            </tr>
        </table>
        
        <table class="table">
            <tr>
                <td colspan="3">
                    <input type="text" class="form-control" name="_nombre" value="<%=datosConductor==null?"":""+datosConductor[2]%>" placeholder="Nombre(s)" required>
                </td>
                <td>
                    <input type="text" class="form-control" name="_apellidopaterno" value="<%=datosConductor==null?"":""+datosConductor[3]%>" placeholder="Apellido paterno" required>
                </td>
                <td colspan="2">
                    <input type="text" class="form-control" name="_apellidomaterno" value="<%=datosConductor==null?"":""+datosConductor[4]%>" placeholder="Apellido materno" required>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                     <center><font size="3" color="#0033cc">Fecha de nacimiento:</font></center>  
                </td>
                 <td>
                     <center><font size="3" color="#0033cc">Edad:</font></center>  
                </td>
                <td colspan="2">
                    <center><font size="3" color="#0033cc">Sexo:</font></center> 
                </td>
              
            </tr>
            <tr>
                <td>
                    <select name="_dia" class="form-control">
                        <%
                        String dia=datosConductor==null?"":""+datosConductor[5];    
                        for(int i=1; i<=31;i++){ 
                        if (dia.equals(""+i)){                   
                        System.out.println("Entro al if "+i);%>
                        <option selected><%=i%></option>
                        <%}else{%>
                        <option><%=i%></option>
                        <%}}%>
                    </select>
                </td>
                <td>
                    <select name="_mes" class="form-control">
                        <%
                        String mes=datosConductor==null?"":""+datosConductor[6];
                        for(int i=1; i<=12;i++){
                        if(mes.equals(""+i)){%>
                        <option selected><%=i%></option>
                        <%}
                        else{%>
                        <option><%=i%></option>
                        <%}}%>
                    </select>
                </td>
                <td>
                    <!--<input type="number" class="form-control" name="_anio"  placeholder="  " required>-->
                    <select class="form-control" name="_anio">
                        <%
                        String anio=datosConductor==null?"":""+datosConductor[7];
                        for(int i=1950; i<=2018;i++){
                        if(anio.equals(""+i)){%>
                        <option selected><%=i%></option>
                        <%}else{%>
                        <option><%=i%></option>
                        <%}}%>
                    </select>
                </td>
                
                <td>
                   <input type="number" class="form-control" name="_edad"  placeholder="  " value="<%=datosConductor==null?"":datosConductor[8]%>" required>
                </td>
                <td>
                    <center>
                    <div class="form-check">
                    <input class="form-check-input" type="radio" id="idsexo" value="M" name="_radiosexo" <%=datosConductor==null?"checked":(datosConductor[9].equals("M")?"checked":"")%> >
                    <label class="form-check-label" for="exampleRadios1">M </label>
                    </div>
                    </center>
                </td>
                <td>
                    <center>
                    <div class="form-check">
                    <input class="form-check-input" type="radio" id="idsexo" value="H" name="_radiosexo" <%=datosConductor==null?"checked":(datosConductor[9].equals("H")?"checked":"")%>  >
                    <label class="form-check-label" for="exampleRadios1">H </label>
                    </div>
                    </center>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                     <center><font size="3" color="#0033cc">Num. licencia:</font></center> 
                </td>
                <td>
                     <center><font size="3" color="#0033cc">Tipo de sangre:</font></center> 
                </td>
                <td colspan="2">
                     <center><font size="3" color="#0033cc">Donador:</font></center> 
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <input type="text" class="form-control" name="_numerolicencia"  placeholder="  " value="<%=datosConductor==null?"":datosConductor[10]%>" required>
                </td>
                <td>
                    <select name="_tiposangre" class="form-control">
                        <%for(String tipo: tiposangre){
                            if(tipo.equals(datosConductor==null?"":datosConductor[11])){
                        %>
                        <option selected><%=tipo%></option>
                        <%}else{%>
                        <option><%=tipo%></option>
                        <%}}%>
                    </select>
                </td>
                  <td>
                    <center> 
                    <div class="form-check">
                    <input class="form-check-input" type="radio" name="_radiodonador" id="exampleRadios2" value="Si" <%=datosConductor==null?"checked":(datosConductor[12].equals("Si")?"checked":"")%>>
                    <label class="form-check-label" for="exampleRadios2">Si</label>
                    </div>
                    </center> 
                </td>
                <td>
                    <center>
                    <div class="form-check">
                    <input class="form-check-input" type="radio" name="_radiodonador" id="exampleRadios2" value="No" <%=datosConductor==null?"checked":(datosConductor[12].equals("No")?"checked":"")%>>
                    <label class="form-check-label" for="exampleRadios2">No</label>
                    </div>
                    </center>
                </td>
            </tr>
        </table>
                    
         <center><p><font size="5" color="#0033cc">Direcciòn</font></p> </center>  
         <table class="table">
             <tr>
                 <td>
                     <input type="text" class="form-control" name="_calle"  placeholder="Calle" value="<%=datosConductor==null?"":datosConductor[13]%>" required>
                 </td>
                  <td>
                     <input type="text" class="form-control" name="_numeroexterior"  placeholder="Num. exterior" value="<%=datosConductor==null?"":datosConductor[14]%>" >
                 </td>
                  <td>
                     <input type="text" class="form-control" name="_numerointerior"  placeholder="Num. interior" value="<%=datosConductor==null?"":datosConductor[15]%>">
                 </td>
             </tr>
             <tr>
                   <td>
                     <input type="text" class="form-control" name="_codigopostal"  placeholder="Codigo postal" required value="<%=datosConductor==null?"":datosConductor[16]%>">
                 </td>
                  <td>
                     <input type="text" class="form-control" name="_colonia"  placeholder="Colonia o localidad" value="<%=datosConductor==null?"":datosConductor[17]%>">
                 </td>
                  <td>
                     <input type="text" class="form-control" name="_municipio"  placeholder="Municipio" value="<%=datosConductor==null?"":datosConductor[18]%>">
                 </td>
             </tr>
                <tr>
                   <td>
                       <select id="idestado" name="_estado" class="form-control">
                        <option value="">Seleccionar estado</option>    
                       <%    
                       String estado=datosConductor==null?"":""+datosConductor[19];    
                       for (String edo: Consultas.estados){
                       if(edo.equals(estado)){%>
                       <option selected=""><%=edo%></option>
                       <%}else{%>
                       <option><%=edo%></option>
                       <%}}%>
                       </select>
                 </td>
                  <td>
                     <input type="text" class="form-control" name="_telefono"  placeholder="Telefono" value="<%=datosConductor==null?"":datosConductor[20]%>" >
                 </td>
                  <td>
                     <input type="text" class="form-control" name="_correo"  placeholder="Email" value="<%=datosConductor==null?"":datosConductor[21]%>">
                 </td>
             </tr>
         </table>
         
         <%--
         <center><p><font size="5" color="#0033cc">Asignar vehìculo y ruta</font></p> </center> 
         <table class="table">
             <tr>
                 <td><center><font size="3" color="#0033cc">Vehìculos:</font></center> 
                </td>
                 <td><center><font size="3" color="#0033cc">Rutas:</font></center> 
                       </td>      
             </tr>
             <tr>
                <td width="50%"><select id="idvehiculo" name="_fkvehiculo" class="form-control">
                        <option value="">Seleccionar</option>
                         <%String vehiculos=Consultas.getUnidadesNoAsignadas();%>
                         <%=vehiculos%>
                </select></td>
                <td width="50%"><select id="idruta" name="_fkruta" class="form-control">
                        <option value="">Seleccionar</option>
                        <%String rutas=Consultas.getRutas();%>
                        <%=rutas%>
                </select></td>
             </tr>
         </table>--%>
         </form> 
           <center>
              <button id="btnguardar" onclick="guardarConductor()" class="btn btn-primary" >Guardar</button>
              <button id="btnguardar" onclick="actualizarConductor()" class="btn btn-primary" >Actualizar</button>
              <button id="btnguardar" onclick="eliminarConductor()" class="btn btn-primary" >Eliminar</button>
              <button type="submit" id="btncancelar" class="btn btn-danger" onclick="recargarPagina()">Cancelar</button>
          </center>
    </div>
           </div>        
                    
    <!-- /#wrapper -->
    <script type="text/javascript" src="js/registrarConductores.js"></script>
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


