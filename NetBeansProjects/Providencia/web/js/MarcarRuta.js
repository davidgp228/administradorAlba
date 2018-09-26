      var array=[];  
      var dato="",datoenviar="";
      var data = [];
      var map ;
      var LAT = [];
      var LON = [];
      var obtenerRuta = [];
      var flightPath ;
      var marker;
      var markers=[];   
      var bounds; 
      
      //---Almacenar informacion en la base de datos
      
      function insertarCoordenadas(){
          if(array.length===0){
              alert('No se han agregado coordenadas');
              return;
          }
          else if($("#idnombreruta").val()===""){
              alert('Ingresar nombre de la ruta');
              return;
          }
          
          $("#idcargando").css("display","block");
           $.get('Ajax',{consulta:'insertarRutas',rutas:array, nombreruta:$("#idnombreruta").val()},function (data){
                $("#idnombreruta").val("");  
                alert('Coordenadas insertadas correctamente');
                location.reload();
           });
        }
         
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
          datostxt =datostxt.replace(/\r/g, ""); //Se reemplaza el retorno de carro para evitar doble salto de linea
          datostxt =datostxt.replace(/\n/g, " "); //Se quitan los saltos de linea
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
          datoenviar=datoenviar+array[i];
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
          zoom: 16,
          center: {lat: 19.17342842523956, lng: -98.23343753814697 },
          
        });

          //bounds =new google.maps.LatLngBounds();
        map.addListener('click', function(e) {
          placeMarkerAndPanTo(e.latLng, map);
        });
      }
       
      function placeMarkerAndPanTo(latLng, map) {     
         array.push(latLng.lat()+","+latLng.lng()); 
         data.push({lat: latLng.lat(), lng: latLng.lng()});
         
        /* bounds.extend(latLng);
        map.fitBounds(bounds);*/
          
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
                
                if(datoenviar===""){
                    alert("Ningun dato para descargar");
                    document.getElementById('link').removeAttribute("download");
                    document.getElementById('link').removeAttribute("href");
                }
                else{
                    document.getElementById('link').download="Coordenadas.txt";
                    this.href = 'data:text/plain;charset=utf-8,'
                    + encodeURIComponent(datoenviar);
                }
            
            };
            };
            
     //***********Eliminar rutas *********************
   
      function eliminarRutas(){
          if($("#idruta").val()===""){
              alert("No se ha seleccionado ruta a eliminar");
              return
          }
          
          var confirmar= confirm("Realmente desea aliminar esta ruta");
          
          if(confirmar){
              
               $.get('Ajax',{consulta:'eliminarRuta',idruta:$("#idruta").val()},function (data){
                $("#idnombreruta").val("");  
                
                if(data==='true'){
                    alert('Ruta eliminada correctamente');
                }
                else{
                    alert('No se ha podido eliminar la ruta');
                }
                
                location.reload();
           });
          }
      }
      
      //*********** Mostrar ruta seleccionada *************************
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
        $("#idcargandorutas").css("display","block");
       
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
           $("#idcargandorutas").css("display","none");
           
           }); 
        
        });
      
      // Removes the markers from the map, but keeps them in the array.
        function clearMarkers() {
          setMapOnAll(null);
        }



