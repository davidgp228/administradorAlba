 //** Cargar ruta seleccionada
       /* var seleccionarauto= document.getElementById('idAuto');
        seleccionarauto.addEventListener('change',
        function(){
        alert("Ha cambiado");
        location.reload();
        });*/

function recargarPagina(){
    /*Recargamos desde caché*/
    //location.reload();
    /*Forzamos la recarga*/
    location.reload(true);
}

function eliminarAuto(){

    if($( "#idAuto" ).val()===""){
        alert('No se ha seleccionado auto a eliminar');
        return;
    }
    
    var confirmar = confirm("Realmente desea eliminar los datos");
    
    if(confirmar){
        //El select no regresa value por eso se asigna en hidden
        $("#idauto").val($( "#idAuto" ).val());
        $("#idaccion").val("eliminarAuto");
        document.getElementById("form").submit();
    }
}

function actualizarAuto(){
    if($( "#idAuto" ).val()===""){
        alert('No se ha seleccionado auto');
        return;
    }
    
    var confirmar = confirm("Realmente desea actualizar los datos");
    
    if(confirmar){
        //El select no regresa value por eso se asigna en hidden
        $("#idaccion").val("actualizarAuto");
        $("#idauto").val($( "#idAuto" ).val());
        document.getElementById("form").submit();  
    }
    
}


