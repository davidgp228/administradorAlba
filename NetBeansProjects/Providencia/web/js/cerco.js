var map ;
 var data = [];
 var flightPath ;
 var marker;
 var markers=[]; 
 var cercos=[];//Se almacenan las longitudes y latitudes de los 4 marcadores
 var marcadoresCerco="";
 var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
 var labelIndex = 0;
 
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
 
    function initMap() 
    {       
            limpiarVariables();
            cercos=[];
            
            markerArray = [];
            
            directionsService = new google.maps.DirectionsService;
            directionsDisplay = new google.maps.DirectionsRenderer;
             // Instantiate an info window to hold step text.
            stepDisplay = new google.maps.InfoWindow;
            
            map = new google.maps.Map(document.getElementById('map'), {
                zoom: 17,
                center: {lat: 19.17342842523956, lng: -98.23343753814697 }
            });
            directionsDisplay.setMap(map);
            
            map.addListener('click', function(e) {
             
                
                 placeMarkerAndPanTo(e.latLng, map);
            });
   }
   
   function insertarCerco(){
       
       
       if(data.length===0){
           alert('Ningun cerco nuevo>>');
           return;
       }
       
       var confirmar=confirm("Confirmar insercion del cerco");
       if(confirmar){
            cercos.push(marcadoresCerco);
            limpiarVariables();
       }
       
   }
   
   function insertarBD(){
     var e = document.getElementById("idruta");
     var fkruta = e.options[e.selectedIndex].value;
       
       if(cercos.length===0){
           
           alert('No se ha agregado ningun cerco');
           return;
           
       }
       
       if(fkruta===''){
           alert('Seleccionar ruta');
           return;
       }
       
       var confirmar= confirm('Esta seguro que los datos son correctos');
       if(confirmar){
       $.get('Ajax',{consulta:'insertarCercos',cercos:cercos, idruta:fkruta},function (data){
           initMap();
       });
        }
   }
   
   function borrarCerco(){
            
            if(markers.length===0){
                alert('Mapa vacio');
                return;
            }
 
            limpiarMarcadores();
            limpiarPolilineas();
            limpiarVariables();
   }
   
   function placeMarkerAndPanTo(latLng, map) {     
            
            if(data.length<=1){
                data.push({lat: latLng.lat(), lng: latLng.lng()});
                marcadoresCerco=marcadoresCerco+""+latLng.lat()+","+latLng.lng()+" ";
                marcarPolilineas();
                crearMarcador(latLng);
            }
            else if(data.length===2){
                data.push({lat: latLng.lat(), lng: latLng.lng()});
                marcadoresCerco=marcadoresCerco+""+latLng.lat()+","+latLng.lng();
                data.push(data[0]);
                marcarPolilineas();
                crearMarcador(latLng);                
            }
           
      }
      
      function crearMarcador(latLng){
            marker = new google.maps.Marker({
              position: latLng,
              label: labels[labelIndex++ % labels.length],
              map: map
            });
            markers.push(marker);
      }
      
      //** Cargar ruta seleccionada
        var seleccionarruta = document.getElementById('idruta');
        seleccionarruta.addEventListener('change',
        function(){ 
        var selectedOption = this.options[seleccionarruta.selectedIndex];
        idruta= selectedOption.value;
        
        if(idruta===''){
            return;
        }
        
        $("#idcargando").css("display","block");
        
         // First, remove any existing markers from the map.
        for (var i = 0; i < markerArray.length; i++) {
          markerArray[i].setMap(null);
        }
        
       
         $.get( 'Ajax',{consulta:'getcoordenadasrutas', idruta:idruta }, function(data){
            if(data==='{}'){
                 alert("!! Ninguna coordenada");
                 return;
             }
             
            content = JSON.parse(data);
            LAT=content.Lat;
            LON=content.Lon;
           
            
         for (var i = 1; i < LAT.length-1; i++) {
          
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
            showSteps(response, markerArray, stepDisplay, map);
         
          $("#idcargando").css("display","none");
          } else {
            window.alert('Petición de indicaciones fallidas debido a ' + status);
          }
        });

           }); 
        
        });
        
        function showSteps(directionResult, markerArray, stepDisplay, map) {
        // For each step, place a marker, and add the text to the marker's infowindow.
        // Also attach the marker to an array so we can keep track of it and remove it
        // when calculating new routes.
        var myRoute = directionResult.routes[0].legs[0];
        for (var i = 0; i < myRoute.steps.length; i++) {
          var marker = markerArray[i] = markerArray[i] || new google.maps.Marker;
          marker.setMap(map);
          marker.setPosition(myRoute.steps[i].start_location);
          attachInstructionText(
              stepDisplay, marker, myRoute.steps[i].instructions, map);
        }
      }
      
      function attachInstructionText(stepDisplay, marker, text, map) {
        google.maps.event.addListener(marker, 'click', function() {
          // Open an info window when the marker is clicked on, containing the text
          // of the step.
          stepDisplay.setContent(text);
          stepDisplay.open(map, marker);
        });
        }
      
      function setMapOnAll(map) {
        for (var i = 0; i < markers.length; i++) {
          markers[i].setMap(map);
        }
      }

      function limpiarMarcadores() {
        setMapOnAll(null);
      }
      
      function marcarPolilineas(){
          if(data.length>1)
             flightPath.setMap(null);//Se borra el objeto anterior 
          
             flightPath = new google.maps.Polyline({
              path:  data,
              geodesic: true,
              strokeColor: '#ffff00',
              strokeOpacity: 1.0,
              strokeWeight: 5
            });
            flightPath.setMap(map);
      }
      
      function limpiarPolilineas(){
            flightPath.setMap(null);//Se borra el objeto anterior 
      }
      
      function limpiarVariables(){
            data = [];
            markers=[]; 
            marcadoresCerco="";
            labelIndex = 0;
      }
      
       

