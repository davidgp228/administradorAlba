/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author mac
 */
public class conexion {
    
    private static BasicDataSource dataSource;
    public static String valueconecction="none";
    
    public static BasicDataSource getdatasource(){
        
        if(dataSource!=null) return dataSource;
        
        dataSource= new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
    try {    
            String ip=InetAddress.getLocalHost().getHostAddress();
            System.out.println("ip:"+ip);
            if(ip.equals("127.0.0.1")){
                dataSource.setPassword("Alba+2018");
                dataSource.setUrl("jdbc:mysql://albadti2018.ddns.net:3306/InnHogar?zeroDateTimeBehavior=convertToNull");
                System.out.println("real");
                valueconecction="real albadti2018.ddns.net";
            }else{
                System.out.println("pruebas");
                dataSource.setPassword("Alba+2018");
                dataSource.setUrl("jdbc:mysql://192.168.1.123:3306/InnHogar");
                valueconecction="pruebas 192.168.1.123";
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataSource;
    }
    
}
