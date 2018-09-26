      var Latitud=19.17319149058359, Longitud=-98.23301902972162;
      var myLatLng;
      var map;
      var tiempo=10000;
      var inter;
      var imeiselect='Seleccionar';
      var image = 'images/coche.png';
      //**Cargar multiples marcadores
    
      var fecha,hora;
      var markers = [] ;
      var poli= [];
      var flightPath ;
      
       //** Variables cargar ruta
    var idruta;
    var waypts = [];
    var content;
    var LAT = [];
    var LON = [];
    var directionsService;
    var directionsDisplay;
    var stepDisplay;
    var markerArray;

      function initMap() {
       obtenercoordenadas();
         //Variable para crear rutas
        directionsService = new google.maps.DirectionsService;
        directionsDisplay = new google.maps.DirectionsRenderer;
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 18,
          center: {lat: Latitud, lng: Longitud}
        });
        
        directionsDisplay.setMap(map);
        
          markerunico = new google.maps.Marker({
          position: {lat: Latitud, lng: Longitud},
          map: map,
          icon:image,
          title: 'Coordenads'
        });  
        
        //Click para cargar rutas
        document.getElementById('submit').addEventListener('click', function() {
          calculateAndDisplayRoute(directionsService, directionsDisplay);
        });
      }
      
      function obtenercoordenadas(){
          $.get('Ajax',{consulta:'ActualizarMarcador'},function(data){
          var datos= ""+data;
          var split= datos.trim().split(",");
          Latitud=split[1]*1;//Multiplicar por 1 para volver numero
          Longitud=split[2]*1;
      }); 
      }
      
      //**Metodo para trazar ruta
      function calculateAndDisplayRoute(directionsService, directionsDisplay) {
        var waypts = [];
        var checkboxArray = document.getElementById('waypoints');
        for (var i = 0; i < checkboxArray.length; i++) {
          if (checkboxArray.options[i].selected) {
            waypts.push({
              location: checkboxArray[i].value,
              stopover: true
            });
          }
        }

        directionsService.route({
          origin: document.getElementById('start').value,
          destination: document.getElementById('end').value,
          waypoints: waypts,
          optimizeWaypoints: true,
          travelMode: 'DRIVING'
        }, function(response, status) {
          if (status === 'OK') {
            directionsDisplay.setDirections(response);
            var route = response.routes[0];
            var summaryPanel = document.getElementById('directions-panel');
            summaryPanel.innerHTML = '';
            // For each route, display summary information.
            for (var i = 0; i < route.legs.length; i++) {
              var routeSegment = i + 1;
              summaryPanel.innerHTML += '<b>Ruta numero : ' + routeSegment +
                  '</b><br>';
              summaryPanel.innerHTML += route.legs[i].start_address + ' a ';
              summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
              summaryPanel.innerHTML += route.legs[i].distance.text + '<br><br>';
            }
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      }  
      
      function                                                                                                                                                                                                                                 ActualizarMarcador(){
          
        if(markers.length>0){
            //limpiar priemro los marcadores del mapa y despues el arreglo
            clearMarkers();
            markers = [];
        }
    
        if(poli.length>0){
            //**Borrar primero el arreglo
            poli= [] ;
            flightPath.setMap(null);
        }
        
        if(idruta===''){
            alert('Seleccione ruta');
            return;
        }
          
        if(imeiselect===''){
            alert('Seleccione imei');   
            return;
        }
      
      $.get('Ajax',{consulta:'ActualizarMarcador', imei:imeiselect, idruta:idruta},function(data){
          
          var datos= ""+data;
          
          if(datos==='')
          {
              alert('Ninguna coordenada para mostrar');
              return;
          }
              
          
          var split= datos.trim().split(",");
          Latitud=split[1]*1;//Multiplicar por 1 para volver numero
          Longitud=split[2]*1;
          markerunico.setPosition( {lat: Latitud, lng: Longitud});
          markerunico.setTitle(Latitud+","+Longitud);
          map.setCenter({lat: Latitud, lng: Longitud});
          
          if(split[3]==='false' && !$('#myModal').is(':visible')){
                 
                 $("#myModal").modal();
                 $("#txtalert").text("Limite de ruta superado");
                           
          }
          
          //*** Actualizar estadisticas------------------------------------
          //alert('Fecha '+split[7]);
          $("#idvelocidad").val(split[4]);
          $("#idrpm").val(split[5]);
          $("#idtemperatura").val(split[6]+'°C');
          $("#idfechaauto").val(split[7]);
          
      });    
      
      }
      
      
        var obtenerRuta = [];
        //** Cargar ruta seleccionada*******************************
        var seleccionarruta = document.getElementById('idruta');
        seleccionarruta.addEventListener('change',
        function(){ 
            
        var selectedOption = this.options[seleccionarruta.selectedIndex];
        idruta= selectedOption.value; //** Se obtiene el valor del option
        
        
        if(obtenerRuta.length>0){
            obtenerRuta=[]; //** Limpiar arreglo
            flightPath.setMap(null); //** Remover las polilineas en caso de que existan
        }
            
        if(idruta===''){
            return;
        }
        
        //** Mostrar barra de carga
        $("#idcargando").css("display","block");
       
         $.get( 'Ajax',{consulta:'getcoordenadasrutas', idruta:idruta }, function(data){
             
            if(data==='{}'){
                 alert("!! Ninguna coordenada");
                 $("#idcargando").css("display","none");
                 return;
             }
             
            content = JSON.parse(data);
            LAT=content.Lat;
            LON=content.Lon;
            
            for (var i = 0; i < LAT.length; i++) {
                obtenerRuta.push({lat: LAT[i]*1, lng: LON[i]*1});
            }   
              
            flightPath = new google.maps.Polyline({
             path:  obtenerRuta,
             geodesic: true,
             strokeColor: '#3AE4F7',
             strokeOpacity: 1.0,
             strokeWeight: 4
           });
           flightPath.setMap(map);
           map.setCenter({lat: LAT[0]*1, lng: LON[0]*1});
           
           //** Ocultar barra de carga
           $("#idcargando").css("display","none");
           
        /* for (var i = 1; i < LAT.length-1; i++) {
          
            waypts.push({
              location: new google.maps.LatLng(LAT[i],LON[i]),
              stopover: true
            });
          
        }   
           
          var start = new google.maps.LatLng(LAT[0], LON[0]);
          var end = new google.maps.LatLng(LAT[LAT.length-1], LON[LAT.length-1]);
           
          directionsService.route({ 
          origin: start,
          destination: end,
          waypoints: waypts,
          optimizeWaypoints: true,
          travelMode: 'DRIVING'
          }, function(response, status) {
          if (status === 'OK') {
            alert('Coordenadas cargadas correctamente');
            directionsDisplay.setDirections(response);
         
          $("#idcargando").css("display","none");
          } else {
            window.alert('Petición de indicaciones fallidas debido a ' + status);
          }
        });*/

           }); 
        
        });
      
     //**Seleccionar tiempo       
     var select = document.getElementById('idtiempo');
     select.addEventListener('change',
     function(){
         //** Validaciones
        if(imeiselect==='Seleccionar'){
        alert('Seleccione imei')  
        return;
        }
        //** Fin de validaciones 
         
     var selectedOption = this.options[select.selectedIndex];
     tiempo= selectedOption.value;
     
     if(tiempo==='0'){
     clearInterval(inter); 
     alert('Se ha detenido el servicio');
     return
     }
     
     alert('Actualizacion cada '+tiempo+' segundos.');
     tiempo= selectedOption.value * 1000;
     clearInterval(inter);
     inter= setInterval(function(){ActualizarMarcador()}, tiempo);   
     });
     
     //**Seleccionar imei
     var selectimei=document.getElementById('idimei');
     
     selectimei.addEventListener('change',
             function(){
                 
             var selectedoption=  this.options[selectimei.selectedIndex];  
             imeiselect=selectedoption.value;
            // console.log("Imei select "+ imeiselect);
            
             });
     
      var marcadores = [
        ['León', 19.17342, -98.233559],
        ['Salamanca', 19.173656, -98.233383],
        ['Zamora', 19.173605, -98.233437]
      ];     
      
      
    
      
      function filtrarFecha(){          
        if(markers.length>0){
         //limpiar priemro los marcadores del mapa y despues el arreglo
        clearMarkers();
        markers = [];
        }
      if(poli.length>0){
        //**limpiar primero el arreglo y despues el mapa  
        poli= [] ;
        flightPath.setMap(null);
      }
       
        fecha=document.getElementById("idfecha").value+" ";
        hora=document.getElementById("idhora").value+":"+document.getElementById("idminuto").value+":00";
        fecha+=hora;
         if(imeiselect==='Seleccionar'){
        alert('Seleccione imei')  
        return
        }
        else if(fecha==""){
            alert("No se ha seleccionado fecha");
            return;
        }
           $.get( 'Ajax',{consulta:'filtrarFechas', imei:imeiselect, fecha:fecha }, function(data){
            content = JSON.parse(data);
            LAT=content.Lat;
            LON=content.Lon;
            var marker, i;
            for (i = 0; i < LAT.length; i++) {     
                
            marker = new google.maps.Marker({
            position: new google.maps.LatLng(LAT[i], LON[i]),
            title:'Latitud '+LAT[i]+' longitud '+LON[i]+' i '+i,
            map: map
            });
            poli.push({lat: LAT[i]*1, lng: LON[i]*1});
            markers.push(marker); 
            map.setCenter({lat: LAT[i], lng:LON[i]});
             //alert("Loading.. "+poli.length);
           }
      
          if($('#idpoli').is(':checked')){
            flightPath = new google.maps.Polyline({
            path:  poli,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2
              });
            flightPath.setMap(map);      
          }
         
           
           });   
      }
      
      function filtro(){
           if($('#idfiltro').is(':checked')){
                                $("#filtrar").css("display","block");
                            }
                            else{
                                $("#filtrar").css("display","none");
                            }
      }
      
     function contarmarcadores(map) {
        for (var i = 0; i < markers.length; i++) {
          markers[i].setMap(map);
        }
      }
      
      function clearMarkers() {
        contarmarcadores(null);
      }