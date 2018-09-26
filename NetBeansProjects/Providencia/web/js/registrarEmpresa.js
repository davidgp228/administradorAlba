/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var result="";
$(window).load(function(){

 $(function() {
  $('#file-input').change(function(e) {
      addImage(e); 
     });

     function addImage(e){
        var file = e.target.files[0],
        imageType = /image.*/;

        if (!file.type.match(imageType))
         return;

        var reader = new FileReader();
        reader.onload = fileOnload;
        reader.readAsDataURL(file);
     }
  
     function fileOnload(e) {
        result=e.target.result;
       // $("#lbltext").text(result);
        $('#imgSalida').attr("src",result);
     }
     
    });
  });

    function actualizarEmpresa(){
       
       if($("#idnombre").val()===""){
           alert("Ingresar nombre de la empresa");
           return;
       }
       else{
           
           if(result !== ""){
               $("#idimagen").val(result);
           }
           
          $("#form").submit(); 
       }
    }
    
  


   