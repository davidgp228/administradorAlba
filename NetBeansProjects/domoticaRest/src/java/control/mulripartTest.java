/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;


/**
 *
 * @author mac
 */
public class mulripartTest {
    
    public static void main(String[] args) throws Exception 
    {
      /* String responseMessageFromServer = null;
       String responseString = null;
        
        final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

        final FileDataBodyPart filePart = new FileDataBodyPart("file", new File("/Users/mac/Downloads/como-programar-en-c-segunda-edicion-deitel.pdf"));
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.field("foo", "bar").bodyPart(filePart);

        final WebTarget target = client.target("http://localhost:8080/domoticaRest/domoticaRest/restapi/pdf");
        //final WebTarget target = client.target("http://albadti2018.ddns.net/domoticaRest/domoticaRest/restapi/pdf");
        final Response response = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
        
        System.out.println("Datos>>> "+ multipart.getMediaType());

         
        // get response message
        responseMessageFromServer = response.getStatusInfo().getReasonPhrase();
        System.out.println("ResponseMessageFromServer: " + responseMessageFromServer);
            
        // get response string
        responseString = response.readEntity(String.class);
        System.out.println("responseString : " + responseString);

        formDataMultiPart.close();
        multipart.close();
        response.close();*/
        command();
    }
    
    public static void command(){
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec("rm /Users/mac/Downloads/MyPngImageFile.png");
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
    }
    
    /**
     * uploads image file using the input HTTP URL
     * @param httpURL
     * @param filePath
     * @param filename
     * @return
     * @throws Exception
     */
    public static String testUploadService(String httpURL, File filePath)  throws Exception {
 
        // local variables
        ClientConfig clientConfig = null;
        Client client = null;
        WebTarget webTarget = null;
        Invocation.Builder invocationBuilder = null;
        Response response = null;
        FileDataBodyPart fileDataBodyPart = null;
        FormDataMultiPart formDataMultiPart = null;
        int responseCode;
        String responseMessageFromServer = null;
        String responseString = null;
 
        try{
            // invoke service after setting necessary parameters
            clientConfig = new ClientConfig();
            clientConfig.register(MultiPartFeature.class);
            client =  ClientBuilder.newClient(clientConfig);
            webTarget = client.target(httpURL);
 
            // set file upload values
            fileDataBodyPart = new FileDataBodyPart("uploadFile", filePath, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            formDataMultiPart = new FormDataMultiPart();
            formDataMultiPart.bodyPart(fileDataBodyPart);
 
            // invoke service
            invocationBuilder = webTarget.request();
            //          invocationBuilder.header("Authorization", "Basic " + authorization);
            response = invocationBuilder.post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA));
 
            // get response code
            responseCode = response.getStatus();
            System.out.println("Response code: " + responseCode);
 
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + responseCode);
            }
 
            // get response message
            responseMessageFromServer = response.getStatusInfo().getReasonPhrase();
            System.out.println("ResponseMessageFromServer: " + responseMessageFromServer);
 
            // get response string
            responseString = response.readEntity(String.class);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally{
            // release resources, if any
            fileDataBodyPart.cleanup();
            formDataMultiPart.cleanup();
            formDataMultiPart.close();
            response.close();
            client.close();
        }
        return responseString;
    }
    
}
