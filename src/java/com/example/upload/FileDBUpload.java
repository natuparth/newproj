/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.upload;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 import java.lang.String;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.*;  
import javax.servlet.http.*;  
import javax.servlet.RequestDispatcher; 
//@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class FileDBUpload extends HttpServlet {
     
    // database connection settings
    private String dbURL = "jdbc:mysql://localhost:3306/project_web";
    private String dbUser = "root";
    private String dbPass = "";
     
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets values of text fields
        String up1 = request.getParameter("num");
        PrintWriter out1 = response.getWriter();
        out1.print(up1);
        InputStream inputStream = null; // input stream of the upload file
         
 
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("attach");
        String dispo=filePart.getHeader("Content-Disposition");
        String filename=dispo.replaceFirst("(?i)^.*filename=\"([^\"]+)\".*$","$1");
        out1.println(filename);        
         
        if (filePart != null) {
       //   out1.print("abcdefgh");            
// prints out some information for debugging
            
            out1.println(filePart.getSize());
            out1.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
           // out1.print(inputStream);
           
        }
         
        Connection conn = null; // connection to the database
        String message = null;  // message will be sent back to client
         
        try {
            // connects to the database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
 
            // constructs SQL statement
            String sql = "INSERT INTO upload (upn,filename,upf) values(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(up1));
            statement.setString(2, filename);
            //  statement.setString(2, lastName);
                 if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
                 statement.setBlob(3, inputStream);
                 out1.println(inputStream);
            }
 
            // sends the statement to the database server
            int row = statement.executeUpdate();
            
         
            
            //if (row > 0) {
         out1.println("printed");
       // response.sendRedirect("welcome.jsp");
     request.setAttribute("number",up1);
    request.setAttribute("filename",filename);
 RequestDispatcher rd = getServletContext().getRequestDispatcher("/welcome.jsp");
           
        rd.forward(request, response);
      // RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");
      // rd.forward(request,response); 
       //request.getRequestDispatcher("welcome.jsp").forward(request, response);
        
            //}
            
        } 
       catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } 
        finally {
            out1.println("ldfjkb");
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // sets the message in request scope
      //      request.setAttribute("Message", message);
             
            // forwards to the message page
    //        getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
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
 @Override
    protected void service(HttpServletRequest request, HttpServletResponse   response) throws ServletException, IOException {
        doPost(request, response);
}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
