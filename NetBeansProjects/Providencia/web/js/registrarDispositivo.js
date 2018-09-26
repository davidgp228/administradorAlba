/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function insertarDispositivo(){
    if($( "#idauto" ).val()===""){
        alert('No se ha asignado vehiculo');
        return;
    }
    else if($("#idimei").val()===""){
         alert('Ingresar imei');
        return;
    }
     else if($("#idnombre").val()===""){
         alert('Ingresar nombre');
        return;
    }
     else if($("#idclave").val()===""){
         alert('Ingresar clave');
        return;
    }
    document.getElementById("form").submit();
}

function eliminarDispositivo(){
    if($( "#iddispositivo" ).val()===""){
        alert('No se ha seleccionado dispositivo');
        return;
    }
    
    var confirmar= confirm("Realmente desea eliminar este dispositivo \n     (Se eliminar todas las coordenadas)");
    
    if(confirmar){
        $("#id").val($("#iddispositivo").val());
        $("#idaccion").val("eliminarDispositivo");
        document.getElementById("form").submit(); 
    }
    
}

function actualizarDispositivo(){
    if($( "#iddispositivo" ).val()===""){
        alert('No se ha seleccionado dispositivo');
        return;
    }
     
     var confirmar= confirm("Realmente desea actualizar los datos");
    
    if(confirmar){
        $("#id").val($("#iddispositivo").val());
        $("#idaccion").val("actualizarDispositivo");
        document.getElementById("form").submit();
    }
}

function recargarPagina(){
   location.replace("registrarDispositivos.jsp");
}