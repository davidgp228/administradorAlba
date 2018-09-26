var liarray=[];
var datosasignados=[];
var incrementador=0;

function agregar(){
    $(".agregar").toggle();
}



function insertarHorario(){
    
    var elementsLI = document.getElementsByTagName('dt');
    if($("#idruta").val()===""){
        alert("Seleccionar ruta");
        return;
    }
    else if(elementsLI.length===0){
        alert("Asignar conductores y unidades");
        return;
    }
     else if($("#idfechainicial").val()===""){
         alert("Asignar fecha");
        return;
    }
      else if($("#idfechafinal").val()===""){
         alert("Asignar fecha");
        return;
    }
    else if($("#idhorasalida").val()===""){
         alert("Asignar hora de salida");
        return;
    }
    else if($("#idhorallegada").val()===""){
         alert("Asignar hora de llegada");
        return;
    }
    
        var idruta=$("#idruta").val();
        var fechainicial=$("#idfechafinal").val();
        var fechafinal=$("#idfechafinal").val();
        var horasalida=$("#idhorasalida").val();
        var horallegada=$("#idhorallegada").val();
        var datos=[];
       
        for(var i = 0; i < elementsLI.length; ++i){
            datos.push(elementsLI[i].value);
        }
         alert("Datos insertados 2");
    
       $.get('Ajax',{consulta:'insertarHorario', datos:datos, idruta:idruta, fechainicial: fechainicial, fechafinal:fechafinal,
       horasalida: horasalida, horallegada:horallegada},function(data){
       
       alert("Datos insertados correctamente");
       /*Forzamos la recarga*/
       location.reload(true);
          
      });
}

function agregarDatos(){
     if($("#idauto").val()===""){
        alert("Seleccionar auto");
        return;
    }
    else if($("#idconductor").val()===""){
        alert("Seleccionar conductor");
        return;
    }
    
    datosasignados.push({Auto:$("#idauto :selected").text(), IdAuto: $("#idauto :selected").val(),
        Conductor: $("#idconductor :selected").text(),IdConductor:$("#idconductor :selected").val()});
    
    var node = document.createElement("dt");
    node.className = 'list-group-item';
    node.value=$("#idauto :selected").val()+"-"+$("#idconductor :selected").val();//Para realizar split de los 2 id's
    var textnode = document.createTextNode("Automovil: "+$("#idauto :selected").text()+" conductor: "+$("#idconductor :selected").text()+"  ");
    node.appendChild(textnode);
    node.insertAdjacentHTML('beforeend', "<button id='"+incrementador+"' onClick='eliminarDato(this.id)' >Eliminar</button>");
    liarray.push(node);
  
    for(var i=0;i<liarray.length;i++){
         if(liarray[i]!==null)
        document.getElementById("idlistadatos").appendChild(liarray[i]);
    }
   /* var value=$("#idauto :selected").val()+"-"+$("#idconductor :selected").val();
    var text="Automovil: "+$("#idauto :selected").text()+" conductor: "+$("#idconductor :selected").text();
    var element="<dt class='list-group-item' value='"+value+"'>"+text+"</dt>"
    alert("element "+element);
    document.getElementById("idlistadatos").insertAdjacentHTML('afterbegin', element);*/
    incrementador++;
    $("#idauto option:selected").remove();
    $("#idconductor option:selected").remove();
}

function eliminarDato(clicked_id)
{
    $('ul').empty(); //** Limpiar toda la lista
        
    liarray[(clicked_id*1)]= null;//** Asignar valor en nulo
    
    //** Asignar nuevamente los valores a los 2 select
    $("#idauto").append('<option value="' + datosasignados[(clicked_id*1)].IdAuto + '">' + datosasignados[(clicked_id*1)].Auto + '</option>');
    $("#idconductor").append('<option value="' + datosasignados[(clicked_id*1)].IdConductor + '">' + datosasignados[(clicked_id*1)].Conductor + '</option>');

    //** Cargar los elementos
    for(var i=0;i<liarray.length;i++)
    {
        if(liarray[i]!==null)
        document.getElementById("idlistadatos").appendChild(liarray[i]);
    }
    
    //** Volver a asignar elemntos al select
}

function seleccionarTipo(id_click){   
    $("#idnuevohorario").text("Nuevo horario "+$("#"+id_click).val());
    $("#idseleccionarunidad").css("display","none");
    $("#idhorarioparticular").css("display","block");
}
var split=[];
function cambiarColor(id){
    
    split=$("#"+id).val().split(",");
    
    
    if(split[1]==="disable"){
        $("#"+id).val(split[0]+",enable");
        $("#"+id).css('background-color','#ccffff');
    }
    else{
        $("#"+id).val(split[0]+",disable");
        $("#"+id).css('background-color','#ffffff');
    }
}