/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.barcvilla.controller;

import ar.com.barcvilla.ejb.OrderProcessorCMTBean;
import com.apress.ejb.chapter07.entities.CartItem;
import com.apress.ejb.chapter07.entities.CustomerOrder;
import com.apress.ejb.chapter07.entities.Individual;
import com.apress.ejb.chapter07.entities.OrderItem;
import com.apress.ejb.chapter07.entities.Wine;
import com.apress.ejb.chapter07.entities.test.PopulateDemoData;
import com.apress.ejb.chapter07.util.Ini;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC
 */
@WebServlet(name = "OrderCTManagement", urlPatterns = {"/OrderCTManagement"})
public class OrderCTManagement extends HttpServlet {
    
    @EJB
    OrderProcessorCMTBean orderProcessorCMT;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        OutputStream rOut = response.getOutputStream();
        PrintStream out = new PrintStream(rOut);
        try
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrderProcessorCMTClient</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderProcessorCMTClient at " + request.getContextPath() + "</h1>");
            
            // creamos y persistimos un conjunto de entidades JPA, poblando la BD con datos
            /**
            out.println("<h2>Poblando la BD con datos de Demostracion...");
            PopulateDemoData.resetData("Chapter08-TransactionSamples-JTA", out);
            out.println("hecho</h2>");
            */
            Ini.loadProperties();
            
            //Filtramos los datos mediante la eliminacion de cualquier cliente existente con el email wineapp@yahoo.com
            // o si esta definido en el archivo user.properties.
            // La primera llamada a un metodo transaccional sobre OrderProcessorBMT iniciara una transaccion
            out.println("<h2>Filtrando los Datos demo");
            System.out.println(orderProcessorCMT.initialize());
            out.println("done</h2>");
            
            // Creamos un cliente y le adicionamos algunos CartItems con sus vinos asociados
            Individual customer = new Individual();
            customer.setFirstName("Transaction");
            customer.setLastName("Head");
            customer.setEmail(Ini.TO_EMAIL_ADDRESS);
            
            for(int i = 0; i < 5; i++)
            {
                final Wine wine = new Wine();
                wine.setCountry("United States");
                wine.setDescription("Delicious wine");
                wine.setName("Xacti");
                wine.setRegion("Dry Creek Valley");
                wine.setRetailPrice(new Float(20.00D + i));
                wine.setVarietal("Zinfandel");
                wine.setYear(2000 + i);
                orderProcessorCMT.persistEntity(wine);
                
                final CartItem cartItem = new CartItem();
                cartItem.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                cartItem.setCustomer(customer);
                cartItem.setQuantity(12);
                cartItem.setWine(wine);
                
                customer.addCartItem(cartItem);
            }
            
            /**
             * Almacenamos al Cliente, confiamos en la configuracion en cascada para almacenar todas las entidades
             * CartItems relacionados al cliente. Luego de la llamada, la instancia del cliente tendra un ID que 
             * fue asignados por el contenedor ejb cuando fue persistido
             */
            orderProcessorCMT.persistEntity(customer);
            
            // creamos un CustomerOrder y creamos un OrderItems para el CartItems
            final CustomerOrder customerOrder = orderProcessorCMT.createCustomerOrder(customer);
            
            out.println("<h2>Recuperando Customer Order Items...");
            for(OrderItem orderItem : customerOrder.getOrderItemList())
            {
                final Wine wine = orderItem.getWine();
                out.println(wine.getName() + " with ID: " + wine.getId());
            }
            
            out.println("done </h2>");
            
            out.println("</body>");
            out.println("</html>");
        }
        finally
        {
            rOut.close();
            out.close();
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
