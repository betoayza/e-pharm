import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author al
 */
public class ServletManejarTablaAdmin extends HttpServlet {

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
            
            //variables
            String urlDB = "jdbc:mysql://localhost:3306/farmaciaOnline?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String usuarioDB = "root";
            String psswdDB = "";
            String driverDB = "com.mysql.cj.jdbc.Driver";
            
            /* Conexión a la base de datos */
            Connection conexionDB = null;
        
        try (PrintWriter out = response.getWriter()) {            

            
            try {
                //Intentar conectar a BD y ejecutar las operaciones
                Class.forName(driverDB);
                conexionDB = DriverManager.getConnection(urlDB, usuarioDB, psswdDB);

                //Capturar decision del Admin
                String eleccionAdmin = request.getParameter("accion");

                switch (eleccionAdmin) {
                    
                    //CASO ALTA
                    case ("agregar"):
                        
                        try {
                        
                            //Paramatros del formulario
                            String nombreProducto = request.getParameter("nombre");
                            String categoria = request.getParameter("categoria");
                            int stock = Integer.parseInt(request.getParameter("stock"));
                            float precio = Float.parseFloat(request.getParameter("precio"));

                            //creo archivo para capturar imagen con ruta del disco
                            File file = new File("/home/al/Documentos/TPFinalProgra2Calvo/imagenesProductosTPFinal/" + request.getParameter("archivoImagen"));

                            //creo inputstream con dicho archivo                                
                            InputStream is = new FileInputStream(file);

                            /*Creación de SQL Statement */
                            PreparedStatement stmnt2 = conexionDB.prepareStatement("INSERT INTO productos (nombre, categoria, stock, precio, imagen) VALUES(?,?,?,?,?)");
                            stmnt2.setString(1, nombreProducto);
                            stmnt2.setString(2, categoria);
                            stmnt2.setInt(3, stock);
                            stmnt2.setFloat(4, precio);
                            stmnt2.setBinaryStream(5, is, (int) file.length());
                            //Ejecutar el Prepared Statement
                            stmnt2.executeUpdate();
                            //cerrar flujo Statement
                            stmnt2.close();                            
                           
                            //CREAR MENSAJE HTML EXITOSO
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Exito</title>");
                            out.println("</head>");
                            out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                            out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">"); 
                            out.println("<h1 style=\"text-align: center;\">Alta Exitosa!</h1>");
                            out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                            out.println("<p style=\"text-align: center;\">Será redirigido en 5 segundos...</p>");
                            out.println("</div>");
                            out.println("</body>");
                            out.println("</html>");
                            
                        //SI CAPTURA ALGUN ERROR
                        } catch (NumberFormatException | SQLException ex) {
                                //Captura de error por default para proseguir con el alta
                                //Siempre pasa primero por esta opcion
                                try {
                                    if (request.getParameter("checador").equals("formularioNoEnviado")) {
                                        out.println("<!DOCTYPE html>");
                                        out.println("<html>");
                                        out.println("<head>");
                                        out.println("<title>Proseguir</title>");
                                        out.println("</head>");
                                        out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                        out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                        out.println("<h1 style=\"text-align: center;\">Prosiguiendo con el alta...</h1>");
                                        out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAltaProducto.jsp'>");
                                        out.println("<p style=\"text-align: center;\">Está siendo redirigido en 5 segundos...</p>");
                                        out.println("</div>");
                                        out.println("</body>");
                                        out.println("</html>");
                                    }
                                    //caso contrario: captura error
                                } catch (Exception e) {
                                    //Captura de error general
                                    //hubo algun error con el alta
                                    out.println("<!DOCTYPE html>");
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>Error</title>");
                                    out.println("</head>");
                                    out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                    out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                    out.println("<h1 style=\"text-align: center;\">Ha ocurrido un error en el Alta!</h1>");
                                    out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>\n");
                                    out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>\n");
                                    out.println("</div>");
                                    out.println("</body>");
                                    out.println("</html>");
                                }
                        }

                        break;

                    //CASO BAJA: por ID producto
                    case ("eliminar"): 
                                             
                        //Intentar la baja
                        try {
//                           //buscar coincidencia de ID
                            Integer id_productoBaja = Integer.parseInt(request.getParameter("id_productoBaja")); //getParameter siempre devuelve String       
    //                      
                            /*Creación de SQL Statement */
                            PreparedStatement stmnt4 = conexionDB.prepareStatement("DELETE FROM productos WHERE id_producto = ?");
                            /* Ejecución de SQL Statement */
                            stmnt4.setInt(1, id_productoBaja);                            
                            //Ejecutar el Prepared Statement
                            Integer resultado = stmnt4.executeUpdate();
                            //'checador' es un validador para proseguir con la baja
                            String checador = request.getParameter("checador2");
                            
                            //si hay coincidencias
                            if (resultado > 0 && checador.equals("formularioEnviado")) {
                                //MOSTRAR MENSAJE HTML EXITOSO
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Exito</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                out.println("<h1 style=\"text-align: center;\">Baja Exitosa!</h1>");                                
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                                out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segs...</p>");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                                
                                //Cerrar flujo Statement                            
                                stmnt4.close();
                                
                            } else {
                                //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Baja Fallida</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                out.println("<h1 style=\"text-align: center;\">Producto no encontrado!</h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                                out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                            }
                        
                    } catch (NumberFormatException | SQLException ex) {
                        //Captura de error por defecto: proseguir con la baja
                        //Siempre pasa por este catch para proseguir
                        try {
                            if (request.getParameter("checador").equals("formularioNoEnviado")) {
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Proseguir</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                out.println("<h1 style=\"text-align: center;\">Prosiguiendo con la baja...</h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaBajaProducto.jsp'>\n");
                                out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>\n");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                            }
                            
                            //Captura de algun error en la baja
                        } catch (Exception e) {
                            //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Error</title>");
                            out.println("</head>");
                            out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                            out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                            out.println("<h1 style=\"text-align: center;\">Ha ocurrido un error en la Baja!</h1>");
                            out.println("<h2 style=\"text-align: center;\"> Detalles: " + e.toString() + "</h2>");
                            out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>\n");
                            out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>\n");
                            out.println("</div>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    }
                    break;

                    //CASO MODIFICAR
                    case ("modificar"):
                        
                        //intentar hacer la modificacion
                        try {
                            
                            //capturar parametros
                            Integer id_producto = Integer.parseInt(request.getParameter("id_producto"));
                            String nombreNuevo = request.getParameter("nombreNuevo");
                            String categoriaNueva = request.getParameter("categoriaNueva");
                            Integer stockNuevo = Integer.parseInt(request.getParameter("stockNuevo"));
                            float precioNuevo = Float.parseFloat(request.getParameter("precioNuevo"));
                            
                            //creo archivo para capturar parametro imagen con ruta del disco
                            File file = new File("/home/al/Documentos/TPFinalProgra2Calvo/imagenesProductosTPFinal/" + request.getParameter("archivoImagenNueva"));
                            //creo inputstream con dicho archivo                                
                            InputStream is = new FileInputStream(file);                            
                            
                            PreparedStatement stmntct = conexionDB.prepareStatement("SELECT * FROM productos WHERE id_producto = ?");
                            stmntct.setInt(1, id_producto);
                            //Ejecutar consulta
                            ResultSet rs = stmntct.executeQuery();
                            
                            //Si hay coincidencia de producto
                            if(rs.next()){
                                /*Creación de SQL Statement */
                                PreparedStatement stmnt3 = conexionDB.prepareStatement("UPDATE productos SET  nombre = ?, categoria = ?, stock = ?, precio = ?, imagen = ? WHERE id_producto = ?");
                                /* Ejecución de SQL Statement */
                                stmnt3.setString(1, nombreNuevo);
                                stmnt3.setString(2, categoriaNueva);
                                stmnt3.setInt(3, stockNuevo);
                                stmnt3.setFloat(4, precioNuevo);
                                stmnt3.setBinaryStream(5, is, (int) file.length());
                                stmnt3.setInt(6, id_producto);
                                
                                int resultado = stmnt3.executeUpdate();                                
                                String checador2 = request.getParameter("checador2");
                                
                                //si se mandaron datos y hay coincidencias
                                if ( (resultado > 0) && checador2.equals("formularioEnviado") ) {
                                    //MOSTRAR MENSAJE HTML EXITOSO
                                    out.println("<!DOCTYPE html>");
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>Exito</title>");
                                    out.println("</head>");
                                    out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                    out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                    out.println("<h1 style=\"text-align: center;\">Modificacion Exitosa!</h1>");
                                    out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                                    out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segs...</p>");
                                    out.println("</div>");
                                    out.println("</body>");
                                    out.println("</html>");
                                    
                                    //Cerrar flujo Statement
                                    stmnt3.close();
                                
                                //si se mandaron datos, pero no hay coincidencias
                                } else {   
                                    //intentar proseguir con la modificacion
                                    if (checador2.equals("formularioEnviado") && (resultado==0)) {
                                        out.println("<!DOCTYPE html>");
                                        out.println("<html>");
                                        out.println("<head>");
                                        out.println("<title>Error</title>");
                                        out.println("</head>");
                                        out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                        out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                        out.println("<h1 style=\"text-align: center;\">Datos incorrectos...</h1>");
                                        out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                                        out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                                        out.println("</div>");
                                        out.println("</body>");
                                        out.println("</html>");
                                    }
                                }
                                
                            //Si no hay coincidencia de producto, mostrar error
                            }else{
                                    out.println("<!DOCTYPE html>");
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>Error</title>");
                                    out.println("</head>");
                                    out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                    out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                    out.println("<h1 style=\"text-align: center;\">Modificacion Fallida! Datos incorrectos...</h1>");
                                    out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                                    out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                                    out.println("</div>");
                                    out.println("</body>");
                                    out.println("</html>");
                            }
                            
                    //CAPTURA ERRORES 
                    } catch (FileNotFoundException | NumberFormatException | SQLException ex) {   
                        //intentar proseguir con la modificacion
                        //Siempre pasa por esta opción
                        try{
                            String checador = request.getParameter("checador");                            
                            if (checador.equals("formularioNoEnviado")) {
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Proseguir</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                out.println("<h1 style=\"text-align: center;\">Prosiguiendo con la modificacion...</h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaModificarProducto.jsp'>");
                                out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                            }
                        //Captura Error en la modificacion 
                        //datos mal enviados
                        }catch (Exception e) {
                            //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Error</title>");
                            out.println("</head>");
                            out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                            out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                            out.println("<h1 style=\"text-align: center;\">Datos incorrectos...</h1>");
                            out.println("<h2 style=\"text-align: center;\"> Detalles: " + e.toString() + "</h2>");
                            out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>\n");
                            out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>\n");
                            out.println("</div>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    }
                    break;

                    case ("consultarTablaProductos"):                        
                        try {                           
                            /*Creación de SQL Statement */
                            PreparedStatement stmnt15 = conexionDB.prepareStatement("SELECT * FROM productos");
                            //Ejecutar Consulta                     
                            ResultSet rs15 = stmnt15.executeQuery(); //devuelve un objeto SQLServerResultSet .
                            
                            //Si hay algún producto
                            if(rs15 != null){   
                                                        
                                //Este RS lo seteamos como attributo de este Servlet
                                request.setAttribute("rsResultadoTablaProductos", rs15);
                                
    //                          //Creamos un RD para enviar este RS a la pagina  de muestra de la tabla
                                RequestDispatcher rd = request.getRequestDispatcher("paginaConsultaTablaProductosAdmin3.jsp");                                  
                                rd.forward(request, response);   
                                
                                //Cerrar flujos  
                                stmnt15.close();
                                rs15.close();
                            }                        
                            //si no hay productos disponibles
                            else{
                                //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Error</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                out.println("<h1 style=\"text-align: center;\">Por el momento no tenemos ningún producto disponible :( ...</h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                                out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                                //Cerrar flujos  
                                stmnt15.close();                           
                            }                             
                        //Captura de cualquier error en el proceso de consulta
                        } catch (IOException | SQLException | ServletException ex) {
                            //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Error</title>");
                            out.println("</head>");
                            out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                            out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                            out.println("<h1 style=\"text-align: center;\">Ha ocurrido un error en la consulta!</h1>");
                            out.println("<h2 style=\"text-align: center;\"> Detalles: " + ex.toString() + "</h2>");
                            out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                            out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                            out.println("</div>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                        break;

                    case ("consultarTablaVentas"):                        
                        try {                           
                            /*Creación de SQL Statement */
                            PreparedStatement stmnt9 = conexionDB.prepareStatement("SELECT * FROM ventas");
                            //Ejecutar Consulta                     
                            ResultSet rs9 = stmnt9.executeQuery();                                             
                            
                            //Si hay ventas realizadas
                            if(rs9 != null){                                                         
                                //Este RS lo seteamos como attributo de este Servlet
                                request.setAttribute("rsResultadoTablaVentas", rs9);                            
    //                          //Creamos un RD para enviar este RS a la pagina  de muestra de la tabla
                                RequestDispatcher rd = request.getRequestDispatcher("paginaConsultarTablaVentas.jsp");
                                //out.println("<h1>6</h1>");    
                                rd.forward(request, response);
                                //Cerrar flujos                         
                                stmnt9.close();
                                rs9.close();
                            }
                            //si no hay ventas realizadas
                            else{
                                //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Error</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                out.println("<h1 style=\"text-align: center;\">No hay ventas realizadas...</h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                                out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                                //Cerrar flujos                         
                                stmnt9.close();
                            }                        
                        //Captura de cualquier error en el proceso de consulta de ventas
                        } catch (IOException | SQLException | ServletException ex) {
                            //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Error</title>");
                            out.println("</head>");
                            out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                            out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                            out.println("<h1 style=\"text-align: center;\">Ha ocurrido un error!</h1>");
                            out.println("<h2 style=\"text-align: center;\"> Detalles: " + ex.toString() + "</h2>");
                            out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                            out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                            out.println("</div>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                        break;
                    
                    case ("consultarTablaUsuarios"):                        
                        try {                           
                            /*Creación de SQL Statement */
                            PreparedStatement stmnt10 = conexionDB.prepareStatement("SELECT * FROM usuarios");
                            //Ejecutar Consulta                     
                            ResultSet rs10 = stmnt10.executeQuery();   
                            
                            //Si hay algún usuario
                            if(rs10 != null){           
                                //Este RS lo seteamos como attributo de este Servlet
                                request.setAttribute("rsResultadoTablaUsuarios", rs10);                            
    //                          //Creamos un RD para enviar este RS a la pagina  de muestra de la tabla
                                RequestDispatcher rd = request.getRequestDispatcher("paginaConsultarTablaUsuarios.jsp");                               
                                rd.forward(request, response);
                                //Cerrar flujos                         
                                stmnt10.close();
                                rs10.close();
                            }
                            //si no hay usuarios
                            else{
                                //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Error</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                                out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                                out.println("<h1 style=\"text-align: center;\">Hasta el momento no hay ningún usuario :( ...</h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                                out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                                //Cerrar flujos                         
                                stmnt10.close();
                            }                        
                    //Captura de cualquier error en el proceso de consulta de usuarios
                    } catch (IOException | SQLException | ServletException ex) {
                        //EMITO MENSAJE ERROR Y REDIRIJO A PAGINA PRINCIPAL ADMIN
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Error</title>");
                        out.println("</head>");
                        out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                        out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                        out.println("<h1 style=\"text-align: center;\">Ha ocurrido un error en la consulta!</h1>");
                        out.println("<h2 style=\"text-align: center;\"> Detalles: " + ex.toString() + "</h2>");
                        out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                        out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                        out.println("</div>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                    break;
                    
                    case ("0"):
                        response.sendRedirect("/AplicacionWebFarmaciaAyza/paginaAdmin.jsp");
                        break;
                
                }//CIERRA SWITCH               

            //Captura de cualquier otro tipo de error en cuanto al Servlet
            //Caso de que usuario no exista o que un dato este incorrecto, la conexión a BD falló, etc            
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");
                out.println("</head>");
                out.println("<body style=\"background-image: url('imgs/fondo_generico.jpg');\">");
                out.println("<div style=\" margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\">");
                out.println("<h1 style=\"text-align: center;\">Error inesperado ha ocurrido!...</h1>");
                out.println("<h2 style=\"text-align: center;\"> Detalles: " + ex.toString() + "</h2>");
                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaAdmin.jsp'>");
                out.println("<p style=\"text-align: center;\"> Será redirigido en 5 segundos...</p>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    } //cierra metodo de procesamiento de solicitud
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
