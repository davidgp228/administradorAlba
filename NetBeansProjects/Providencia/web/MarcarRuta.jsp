<%-- 
    Document   : MarcarRuta
    Created on : 24-ago-2017, 10:24:50
    Author     : mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
       <!-- Bootstrap CSS -->    
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="css/elegant-icons-style.css" rel="stylesheet" />
    <link href="css/font-awesome.min.css" rel="stylesheet" />    
    <!-- full calendar css-->
    <link href="assets/fullcalendar/fullcalendar/bootstrap-fullcalendar.css" rel="stylesheet" />
	<link href="assets/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" />
    <!-- easy pie chart-->
    <link href="assets/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen"/>
    <!-- owl carousel -->
    <link rel="stylesheet" href="css/owl.carousel.css" type="text/css">
	<link href="css/jquery-jvectormap-1.2.2.css" rel="stylesheet">
    <!-- Custom styles -->
	<link rel="stylesheet" href="css/fullcalendar.css">
	<link href="css/widgets.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/style-responsive.css" rel="stylesheet" />
	<link href="css/xcharts.min.css" rel=" stylesheet">	
	<link href="css/jquery-ui-1.10.4.min.css" rel="stylesheet">
    <!-- =======================================================
        Theme Name: NiceAdmin
        Theme URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
        Author: BootstrapMade
        Author URL: https://bootstrapmade.com
    ======================================================= -->
    <title>Accessing arguments in UI events</title>
    <script src="js/jquery.js"></script>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
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
     <input type="hidden" id="coordenadas" value="100" >
      <table id="idtabla">
          <thead>
              
              <tr width="100%">
                  <td><center> <input  type="file" id="file-input" /></center></td>
                  <td> <center>
                  <button id="btneliminar" class="btn btn-danger" onclick="EliminarCoordenadas()">Eliminar ultima coordenada</button>
                  <a href="" id="link" download="contenido.txt">DESCARGAR COORDENADAS</a></center></td>
              </tr>
          </thead>
          <tbody>
              <tr >
                  <td colspan="3"> <div id="map"></div></td>
              </tr>
          </tbody>
          <thead>
              <tr>
                  <th colspan="3"><center>Coordenadas </center></th>
              </tr>
          </thead>
          <tbody>
              <tr>
                  <td colspan="3"><center><label id="lbldatos">---- </label></center> </td>           
              </tr>
         
                  
          </tbody>
      </table>   
     <h3>Contenido del archivo:</h3>
     <pre id="contenido-archivo"></pre>  
     <script>
      var array=[];  
      var dato="",datoenviar="";
      var data = [];
      var map ;
      var flightPath ;
      var marker;
      var markers=[];   
         
    //------LECTURA TXT     
       var datostxt;
        function leerArchivo(e) {
        var archivo = e.target.files[0];
        if (!archivo) {
          return;
        }
        var lector = new FileReader();
        lector.onload = function(e) {
          var contenido = e.target.result;
          datostxt=""+contenido;//Parsse contenido del txt a Stirng
          datostxt =datostxt.replace(/\n/g, ' '); //Se quitan los saltos de linea
          var split = datostxt.split(" "); var i;
          
          if(array.length>1){
            flightPath.setMap(null);//Se borra el objeto anterior 
             for (var j = 0; j < markers.length; j++) {
              markers[j].setMap(null);
              }
            array=[];
            data=[];
            markers=[];
           }
          for(i=0; i<split.length;i++ ){
          
          if(split[i]!=""){
              console.log("val="+split[i]);    
          var split2=split[i].split(",");  
          array.push(split2[0]+","+split2[1]); 
          data.push({lat: split2[0]*1, lng: split2[1]*1});   
          marker = new google.maps.Marker({
          position: {lat: split2[0]*1, lng: split2[1]*1},
          map: map
          });
          markers.push(marker);
          setMapOnAll(map);
      }
          }
           var i; dato=""; datoenviar="";
        
        for( i=0; i<array.length; i++){
          dato=dato+array[i]+"<br\>";
          datoenviar=datoenviar+array[i]+"\r\n";
        }
        document.getElementById('lbldatos').innerHTML = dato;
          
           flightPath = new google.maps.Polyline({
          path:  data,
          geodesic: true,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 2
        });
        flightPath.setMap(map);
         
          mostrarContenido(contenido);
        };
        lector.readAsText(archivo);
        }

        function mostrarContenido(contenido) {
          var elemento = document.getElementById('contenido-archivo');
          elemento.innerHTML = contenido;
        }

        document.getElementById('file-input').addEventListener('change', leerArchivo, false);
//---------FIN LECTURA TXT

      
     
      function initMap() {
          map = new google.maps.Map(document.getElementById('map'), {
          zoom: 20,
          center: {lat: 19.17342842523956, lng: -98.23343753814697 },
          
        });

        map.addListener('click', function(e) {
          placeMarkerAndPanTo(e.latLng, map);
        });
      }
       
      function placeMarkerAndPanTo(latLng, map) {     
         array.push(latLng.lat()+","+latLng.lng()); 
         data.push({lat: latLng.lat(), lng: latLng.lng()});
          
        var i; dato=""; datoenviar="";
        
        for( i=0; i<array.length; i++){
          dato=dato+array[i]+"<br\>";
          datoenviar=datoenviar+array[i]+"\r\n";
        }
        document.getElementById('lbldatos').innerHTML = dato;
        if(array.length>1)
            flightPath.setMap(null);//Se borra el objeto anterior 
        flightPath = new google.maps.Polyline({
          path:  data,
          geodesic: true,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 2
        });
        flightPath.setMap(map);
        
        marker = new google.maps.Marker({
          position: latLng,
          map: map
        });
        markers.push(marker);
        setMapOnAll(map);
      }
      
      
      function EliminarCoordenadas(){
          clearMarkers();
          if(array.length<=0){
               alert("Sin coordenadas");  return;   
          } 
          
         array.pop();
         data.pop();
         markers.pop();
         
         //Datos de la lista
         var i;
         dato="";
         datoenviar="";
         for(i=0; i<array.length;i++){
            dato=dato+array[i]+"<br\>"; 
            datoenviar=datoenviar+array[i]+"\r\n";
         }
         document.getElementById('lbldatos').innerHTML = dato;
          
         //Pililineas 
          flightPath.setMap(null);
          flightPath = new google.maps.Polyline({
          path:  data,
          geodesic: true,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 2
          });

        flightPath.setMap(map); 
        
        //Marcadores cargar otra vez
            for (var i = 0; i < markers.length; i++) {
             markers[i].setMap(map);
            }
      }
      
      function setMapOnAll(map) {
      for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(map);
      }
                                }
      
      function GuardarCoordenadas(){
       if(array.length<=0){
               alert("Sin coordenadas");  return;   
          } 
          var txt = document.getElementById('txt');
          txt.value =  dato;
           this.href = 'data:text/plain;charset=utf-8,'
            + encodeURIComponent(txt.value);
          download="contenido.txt";

      }
      
      window.onload = function() {
            // ontengo el contenido del textarea
            //var txt = document.getElementById('txt');
            //txt.value =  'hello';

            // se genera y descarga el archivo
            document.getElementById('link').onclick = function(code) {
            this.href = 'data:text/plain;charset=utf-8,'
            + encodeURIComponent(datoenviar);
            };
            };
      // Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
  setMapOnAll(null);
}

    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0ppATA8sAqduBh10Gus822FkD887KhIM&callback=initMap">
    </script>
  </body>
</html>
