var sexo = document.getElementsByName('_radiosexo');
var donador = document.getElementsByName('_radiodonador');
var confirmar;

function guardarConductor(){
    
    if($("#idestado").val()===""||$("#idruta").val()===""||$("#idvehiculo").val()===""){
        alert("Faltan seleccionar datos");
        return;
    }
    
    seleccionarDonador();
    
    document.getElementById("form").submit();
}

function actualizarConductor(){
      if($( "#idConductor" ).val()===""){
        alert('No se ha seleccionado conductor a eliminar');
        return;
    }
    
    confirmar= confirm("¿Realmente desea actualizar la informacion?");
    seleccionarDonador();
    if(confirmar){
        $("#idconductor").val($( "#idConductor" ).val());
        $("#idaccion").val("actualizarConductor");
        document.getElementById("form").submit();
    }
    
}

function eliminarConductor(){
    if($( "#idConductor" ).val()===""){
        alert('No se ha seleccionado conductor a eliminar');
        return;
    }
    
    confirmar = confirm("¿Realmente deseea eliminar los datos?");
    
    if(confirmar){
     //El select no regresa value por eso se asigna en hidden esta fuera del form
    $("#idconductor").val($( "#idConductor" ).val());
    $("#idaccion").val("eliminarConductor");
    document.getElementById("form").submit();
    }
}

function seleccionarDonador(){
    
    for (var i = 0, length = sexo.length; i < length; i++)
    {
        if (sexo[i].checked)
        {
        $("#idsexo").val(sexo[i].value);
         break;
        }
    }
    
    for (var i = 0, length = donador.length; i < length; i++)
    {
        if (donador[i].checked)
        {
        $("#iddonador").val(donador[i].value);
         break;
        }
    }
    
}
