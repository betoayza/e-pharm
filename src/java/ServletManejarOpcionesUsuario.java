/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author al
 */
@WebServlet(urlPatterns = {"/ServletManejarOpcionesUsuario"})
public class ServletManejarOpcionesUsuario extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        
        //Setear cómo va a ser el tipo de respuesta del servlet
        response.setContentType("text/html;charset=UTF-8");
        
        //Obtener el parametro accion de la solicitud
        String eleccionAdmin = request.getParameter("accion"); 
        
        /*Inicializar variables */
            String mensajeResultado = "Base de datos actualizada...";
            String urlDB = "jdbc:mysql://localhost:3306/farmaciaOnline?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String usuarioDB = "root";
            String psswdDB = "";
            String driverDB = "com.mysql.cj.jdbc.Driver";
            
            /* Paso4) Conexión a la base de datos */
            Connection conexionDB = null;
            
        try (PrintWriter out = response.getWriter()) {                 
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Eleccion Usuario</title>");            
            out.println("</head>");
            out.println("<body style=\"background-color: beige;\" >");                 
            
            Class.forName(driverDB);
            conexionDB = DriverManager.getConnection(urlDB, usuarioDB, psswdDB);
            
            //swichear la eleccion 
            switch (eleccionAdmin) {
                case ("verCompras"): 
                    //out.println("<h1>1</h1>"); 
                        try {     
                            //Recuperar Sesión
                            HttpSession session = request.getSession();
                            //Recuperar nombre de usuario de la sesión
                            String usuario = session.getAttribute("nombreUsuario").toString();
                            
                            /*Creación de SQL Statement */
                            PreparedStatement stmnt9 = conexionDB.prepareStatement("SELECT producto, monto, fechaHora FROM ventas WHERE usuario = ?");
                            stmnt9.setString(1, usuario);
                           
                            //Ejecutar Consulta                     
                            ResultSet rs9 = stmnt9.executeQuery();   
                            
                            //Si hay algo en el result set
                            if( rs9.next() ){   
                                                        
                                //Este RS lo seteamos como attributo de la solicitud
                                request.setAttribute("rsResultadoMisCompras", rs9);
                               
    //                          //Creamos un RD para enviar este RS a la pagina  de muestra de la tabla
                                RequestDispatcher rd = request.getRequestDispatcher("paginaConsultarMisComprasUsuario.jsp");
                               
                                //Enviar desde Servlet Actual la solicitud y la respuesta a 'paginaConsultarMisComprasUsuario.jsp'
                                rd.forward(request, response);
                                
                                //Cerrar flujos                         
                                stmnt9.close();
                                rs9.close();
                            }
                            //si no hay nada en rs
                            else{
                                //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL USUARIO
                                out.println("<h2>Todavia no ha realiza compra alguna :( ...</h2>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaPrincipalUsuario.jsp'>\n");
                                out.println("<p> Será redirigido en 5 segundos...</p>\n");
                            }                        

                        } catch (IOException | SQLException | ServletException ex) {
                            //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL USUARIO
                            out.println("<h1>Ha ocurrido un error la Consulta!</h1>");
                            out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaPrincipalUsuario.jsp'>\n");
                            out.println("<p> Será redirigido a pagina Admin en 5 segundos...</p>\n");
                        }
                    //response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaConsultarMisComprasUsuario.jsp");
                    break;
                    
                case ("buscarProducto"): 
                    response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaConsultaProducto.jsp");
                    break;   
                    
                case ("0"):
                    response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaPrincipalUsuario.jsp");
                    break;
                    
                default:
                    response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaPrincipalUsuario.jsp");
                    break;                   
            }
            out.println("</body>");
            out.println("</html>");
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
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServletManejarOpcionesUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ServletManejarOpcionesUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
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
