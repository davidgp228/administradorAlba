/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var i;
var permisos="";
var ids=["#idauto","#idconductor","#idhorario","#idmonitoreo","#iddispositivos","#idrutas"];

function editarPermisos(permisos){
        
    var res = permisos.split(",");
 
    //** Recorrer el arreglo y poner checket a los elementos
    for(i=0; i<res.length; i++){
        
        if(res[i]=='Administrador'){
            $( "#idtodos" ).prop( "checked","checked");
            permisoTodos();
            break;
        }
        else if(res[i]=='mapa'){
            $( "#idrutas" ).prop( "checked","checked");
        }
         else if(res[i]=='autos'){
            $( "#idauto" ).prop( "checked","checked");
        }
         else if(res[i]=='dispositivos'){
            $( "#iddispositivos" ).prop( "checked","checked");
        }
         else if(res[i]=='conductores'){
            $( "#idconductor" ).prop( "checked","checked");
        }
         else if(res[i]=='horarios'){
            $( "#idhorario" ).prop( "checked","checked");
        }
         else if(res[i]=='monitoreo'){
            $( "#idmonitoreo" ).prop( "checked","checked");
        }  
    }
}

function permisoTodos(){
    
    if($('#idtodos').is(":checked")){
        
        //** Desabilitar todos
        for(i=0;i<ids.length;i++ ){
            $( ids[i] ).prop( "disabled", true );
        }
        
        //** Seleccionar todos
        for(i=0;i<ids.length;i++ ){
            $( ids[i] ).prop( "checked","checked");
        }
        
    }
    else{
        
        for(i=0;i<ids.length;i++ ){
            $( ids[i] ).prop( "disabled", false );
        }
        
        
        for(i=0;i<ids.length;i++ ){
            $( ids[i] ).prop( "checked","");
        }
    }
}

function eliminarUsuario(){
     if($( "#idusuario" ).val()===""){
        alert('No se ha seleccionado usuario a eliminar');
        return;
    }
    
    
    var confirmar= confirm("¿Realmente desea eliminar este usuario?");
    
    if(confirmar){
        $("#idusuarioenviar").val($("#idusuario").val());
        $("#idaccion").val("eliminarUsuario");
        $("#form").submit();
    }
}

function actualizarUsuario(){
    if($( "#idusuario" ).val()==="")
    {
        alert('No se ha seleccionado usuario a eliminar');
        return;
    }
    
        permisos="";
        
        if($('#idtodos').is(":checked"))
        {
            
            permisos=$('#idtodos').val();
            
        }
        else
        {
            
            for(i=0; i<ids.length;i++){
                if($(ids[i]).is(":checked")){
                    permisos+=$(ids[i]).val()+",";
                }
            }
            
        }
    
        if(permisos==="")
        {
            alert("No se han asignado permisos");
        }
        else if($("#idnombre").val()===""){
            alert("Asignar nombre");
        }
        else if($("#idnombreusuario").val()===""){
            alert("Asignar nombre de usuario");
        }
        else if($("#idnuevacontrasena").val()===""){
            alert("Asignar contraseña");
        }
        else if($("#idconfirmarcontrasena").val()===""){
            alert("Confirmar contraseña");
        }
        else if($("#idnuevacontrasena").val()!==$("#idconfirmarcontrasena").val()){
            alert("Las contraseñas no coinciden");
        }
        else
        {
            
            var confirmar= confirm("¿Los datos son correctos?");

            if(confirmar){
                $("#idpermisosenviar").val(permisos);
                $("#idusuarioenviar").val($("#idusuario").val());
                $("#idaccion").val("actualizarUsuario");
                $("#form").submit();
            }
         }
}

function insertarUsuario(){
        permisos="";
        
        if($('#idtodos').is(":checked"))
        {
            
            permisos=$('#idtodos').val();
            
        }
        else
        {
            
            for(i=0; i<ids.length;i++){
                
                if($(ids[i]).is(":checked")){
                    permisos+=$(ids[i]).val()+",";
                }
            }
            
        }
        
        if(permisos===""){
            alert("No se han asignado permisos");
        }
        else if($("#idnombre").val()===""){
            alert("Asignar nombre");
        }
        else if($("#idnombreusuario").val()===""){
            alert("Asignar nombre de usuario");
        }
        else if($("#idnuevacontrasena").val()===""){
            alert("Asignar contraseña");
        }
        else if($("#idconfirmarcontrasena").val()===""){
            alert("Confirmar contraseña");
        }
        else if($("#idnuevacontrasena").val()!==$("#idconfirmarcontrasena").val()){
            alert("Las contraseñas no coinciden");
        }
        else
        {
    
            var confirmar= confirm("¿Los datos son correctos?");
            if(confirmar){
                    $("#idpermisosenviar").val(permisos);
                    $("#form").submit();
            }
            
        }
}
