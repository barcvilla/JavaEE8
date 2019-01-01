/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public final class Ini implements Serializable {

    public static  String FROM_EMAIL_ADDRESS;
    public static  String TO_EMAIL_ADDRESS;

    private static Properties properties = new Properties();
    
    private static String getFileName() {
        String base = null;
        base = System.getProperty("user.home");

        if (base != null && !base.endsWith(File.separator)) {
            base += File.separator;
        }

        if (base == null) {
            base = "";
        }

        return base + "user.properties";
    }
    
    
    public static void loadProperties() {
        loadProperties(getFileName());
    }
    
    
    public static void loadProperties(String fileName) {
        FileInputStream fis = null;
        try {
            //fis = Ini.class.getClassLoader().getResourceAsStream("user.properties");
            fis = new FileInputStream(fileName);
            properties.load(fis);
            FROM_EMAIL_ADDRESS = properties.getProperty("from_email_address");
            TO_EMAIL_ADDRESS = properties.getProperty("to_email_address");
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(Ini.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
