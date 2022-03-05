/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author al
 */
public class ServletManejoLogueoUsuario2 extends HttpServlet {

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
            
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */            
           
            /*Inicializar variables */
            String urlDB = "jdbc:mysql://localhost:3306/farmaciaOnline?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String usuarioDB = "root";
            String psswdDB = "";
            String driverDB = "com.mysql.cj.jdbc.Driver";

            /* Paso4) Conexión a la base de datos */
            Connection conexionDB = null;

            //Intentar conectar a BD y ejecutar las operaciones
            try {
                Class.forName(driverDB);
                conexionDB = DriverManager.getConnection(urlDB, usuarioDB, psswdDB);

                //Datos entrantes el formulario
                /* Paso 1) Obtener los datos del formulario */
                String usuario = request.getParameter("usuario");                
                String contrasenia = request.getParameter("password");
                //Primero checkeo si el usuario existe
                PreparedStatement stmnt = conexionDB.prepareStatement("SELECT usuario, password FROM usuarios WHERE usuario = ? AND password = ?");
                stmnt.setString(1, usuario);
                stmnt.setString(2, contrasenia);
                ResultSet rs = stmnt.executeQuery();
                
                //Si encuentra algun usuario
                if (rs.next()) {           
                        HttpSession session = request.getSession(true);
                        session.setAttribute("nombreUsuario", usuario);
                        //Validar credenciales para admin
                        if (rs.getString(1).equals("admin") && rs.getString(2).equals("1234")){
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");                       
                                out.println("</head>");
                                out.println("<body style=\"background-image:url('imgs/fondo_generico.jpg'); background-position: center;\">"); 
                                out.println("<div style=\"margin-left: 25%; margin-top: 25%; margin-right: 25%; border-style: double; border-radius: 20px\" >");
                                out.println("<h1 style=\"text-align: center;\">Bienvenido Administrador!</h1>");                   
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>\n");
                                out.println("<p style=\"text-align: center;\"> Espere unos instantes, será redirigido...</p>\n");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                            //Validar credenciales usuario
                        }else{
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");                       
                                out.println("</head>");
                                out.println("<body style=\"background-image:url('imgs/fondo_generico.jpg'); background-position: center;\">"); 
                                out.println("<div style=\"margin-left: 25%; margin-top: 25%; margin-right: 25%; border-style: double; border-radius: 20px;\" >"); //align=\"center\"
                                out.println("<h1 style=\"text-align: center;\" > Bienvenido "+ usuario + "!</h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaPrincipalUsuario.jsp'>\n");
                                out.println("<p style=\"text-align: center;\"> Espere por favor, será redireccionado en 5 segundos...</p>\n");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                       }
                //Si no hay alguna coincidencia, mostrar pagina error
                } else{
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");                       
                        out.println("</head>");
                        out.println("<body style=\"background-image:url('imgs/fondo_error_1.png'); background-position: center;\">"); 
                        out.println("<div style=\"margin: 25% auto; border-style: double; border-radius: 20px;\" >");
                        out.println("<h1 style=\"text-align: center;\">Error en el Logueo!</h1>");
                        out.println("<h1 style=\"text-align: center;\"> :( </h1>");
                        out.println("<h2 style=\"text-align: center;\">Usuario "+ usuario +" inexistente o password incorrecta!</h2>");
                        out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaLogueoUsuario.jsp'>");
                        out.println("<p style=\"text-align: center;\">Espere, será redirigido en 5 segundos...</p>");
                        out.println("</div>");
                        out.println("</body>");
                        out.println("</html>");
                  }

                //Cerrar flujo Statement
                stmnt.close();          
            
        //Captura de cualquier otro error (Conexion BDs, etc)
        }catch (ClassNotFoundException | SQLException ex) {            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");            
            out.println("</head>");
            out.println("<body style=\"background-image:url('imgs/fondo_error_1.png'); background-position: center;\">"); 
            out.println("<div style=\"margin: 25% auto; border-style: double; border-radius: 20px;\" >");
            out.println("<h1 style=\"text-align: center;\">Error en la conexión! No se pudo comunicar con el servidor...</h1>");
            out.println("<h2 style=\"text-align: center;\">Detalles: " + ex.toString() + "</h2>");            
            out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaLogueoUsuario.jsp'>\n");
            out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>\n");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
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
