/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author al
 */
@WebServlet(name = "ServletManejoRegistro", urlPatterns = {"/ServletManejoRegistro"})
public class ServletManejoRegistro extends HttpServlet {

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
            
            /*Inicializar variables */                
            String urlDB = "jdbc:mysql://localhost:3306/farmaciaOnline?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String usuarioDB = "root";
            String psswdDB = "";
            String driverDB = "com.mysql.cj.jdbc.Driver";

            /* Paso4) Conexión a la base de datos */
            Connection conexionDB = null;          
                
            try (PrintWriter out = response.getWriter()) {             
                Class.forName(driverDB);
                conexionDB = DriverManager.getConnection(urlDB, usuarioDB, psswdDB);

                //Datos entrantes el formulario
                /* Paso 1) Obtener los datos del formulario */
                String usuario = request.getParameter("usuario");
                String contrasenia = request.getParameter("password");
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");

                PreparedStatement stmnt = conexionDB.prepareStatement("SELECT * FROM usuarios WHERE usuario = ?");
                stmnt.setString(1, usuario);
                ResultSet rs = stmnt.executeQuery();
                
                //Si Result Set tiene algo signfica que encontro el usuario
                if (rs.next()) {          
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Registro Fallido</title>");            
                    out.println("</head>");
                    out.println("<body style=\"background-image:url('imgs/fondo_error.png'); background-size: 100%;\">");
                    out.println("<div align=\"center\" style=\" margin: 25% auto;\">");
                    out.println("<h1>Error en el registro!</h1>");
                    out.println("<h2>:(</h2>");
                    out.println("<h3>Usuario: " + usuario + " ya está en uso!</h3>");
                    out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaRegistroUsuario.jsp'>");
                    out.println("<p> Espere por favor, será redireccionado en 5 segundos...</p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    
                //CASO CONTRARIO: No existe usuario
                }else{
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Registro Exitoso</title>");            
                    out.println("</head>");
                    out.println("<body style=\"background-image:url('imgs/fondo_bienvenida3.jpg');\">");
                    out.println("<div align=\"center\" style=\" margin: 25% auto;\">");                
                    out.println("<h1>Registro exitoso!</h1>");
                    out.println("<h2> :) </h2>");
                    out.println("<h3>Gracias por registrarte: " + usuario + "!</h3>");
                    out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaConsultaProducto.jsp'>");
                    out.println("<p> Espere por favor, será redireccionado en 5 segundos...</p>");
                    out.println("</div>");
                }
                //Cerrar flujo Statement
                stmnt.close();   
                
            //Captura de cualquier otro error (del Servlet, Conexion a BDs, etc)
            }catch(IOException | ClassNotFoundException | NumberFormatException | SQLException  e){
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Registro Fallido</title>");            
                    out.println("</head>");
                    out.println("<body style=\"background-image:url('imgs/fondo_error.png'); background-size: 100%;\">");
                    out.println("<div align=\"center\" style=\" margin: 25% auto;\">");
                    out.println("<h1>Ha ocurrido un error en el proceso!</h1>");
                    out.println("<h2>" + e.toString() + "</h2>");
                    out.println("<h2>:(</h2>");                
                    out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaRegistroUsuario.jsp'>");
                    out.println("<p> Espere por favor, será redireccionado en 5 segundos...</p>");
                    out.println("</div>");
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
