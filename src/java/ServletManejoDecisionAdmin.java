/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author al
 */
public class ServletManejoDecisionAdmin extends HttpServlet {

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
            
            String eleccionAdmin = request.getParameter("accion"); 
            
            try (PrintWriter out = response.getWriter()) {                          
            
                //Se switchea el parametro 
                switch (eleccionAdmin) {
                    case ("agregar"):  //ALTAS & BAJAS 
                        response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaAltaProducto.jsp");
                        break;
                    case ("Eliminar"): //Se elimina por ID producto
                        response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaBajaProducto.jsp");
                        break;
                    case ("Modificar"):
                        response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaModificarProducto.jsp");
                        break;
                    case ("ConsultarTablaProductos"):
                        response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaConsultaTablaProductosAdmin.jsp");
                        break;
                    case ("ConsultarTablaVentas"):
                        response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaConsultarTablaVentas.jsp");
                        break;
                    case ("ConsultarTablaUsuarios"):
                        response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaConsultarTablaUsuarios.jsp");
                        break;
                }           
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
