/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.upload;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author parth
 */
public class MySpecialListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // On Application Startup, please…

        // Usually I'll make a singleton in here, set up my pool, etc.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // On Application Shutdown, please…

        // 1. Go fetch that DataSource
        Context initContext;
        try {
            initContext = new InitialContext();
Context envContext  = (Context)initContext.lookup("java:/comp/env");
        DataSource datasource = (DataSource)envContext.lookup("jdbc/database");


        } catch (NamingException ex) {
            Logger.getLogger(MySpecialListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // 2. Deregister Driver
        try {
            java.sql.Driver mySqlDriver = DriverManager.getDriver("jdbc:mysql://localhost:3306/project_web");
            DriverManager.deregisterDriver(mySqlDriver);
        } catch (SQLException ex) {
            logger.info("Could not deregister driver:".concat(ex.getMessage()));
        } 

        // 3. For added safety, remove the reference to dataSource for GC to enjoy.
        //  dataSource = null;
    }




    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
   // </editor-fold>

    /**
     *
     * @param sce
     */



}
