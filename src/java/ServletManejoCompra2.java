/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author al
 */
public class ServletManejoCompra2 extends HttpServlet {

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
            /* Conexión a la base de datos */
            Connection conexionDB = null;

        try (PrintWriter out = response.getWriter()) {
                 
            
                //conectar a BD
                Class.forName(driverDB);
                conexionDB = DriverManager.getConnection(urlDB, usuarioDB, psswdDB);

                //Obtener los parametros de la solicitud pasados por formulario con POST 
                String nombreProductoComprado = request.getParameter("nombreProductoComprado");
                String categoriaProductoComprado = request.getParameter("categoriaProductoComprado");
                float precioProductoComprado = Float.parseFloat(request.getParameter("precioProductoComprado"));
                int idProductoComprado = Integer.parseInt(request.getParameter("idProductoComprado"));
              
                
                //Consultar por existencia de producto
                //PreparedStatement stmnt = conexionDB.prepareStatement("SELECT nombre, categoria, precio FROM productos WHERE nombre = ? AND categoria = ? AND precio = ?");
                PreparedStatement stmnt = conexionDB.prepareStatement("SELECT * FROM productos WHERE id_producto = ?");
                stmnt.setInt(1, idProductoComprado);                
                
                //Ejecutar consulta por existencia de producto
                ResultSet rsProductoEncontrado = stmnt.executeQuery();                
                
                int stockProducto = 0;  
                
                if (rsProductoEncontrado.next()){
                    stockProducto =rsProductoEncontrado.getInt(4);
                }
                                                       
                //Si existe el producto buscado y su stock es mayor que 0
                //Funciona con "rs != null" y no con "rs.next()"
                if ( (rsProductoEncontrado != null) && (stockProducto > 0) ) {                        
                        
                        //Actualizar stock --> Nuevo Stock = Stock - 1                       
                        stockProducto--;                        
                        
                        //recupero sesión                        
                        HttpSession session = request.getSession(true);
                        //recuperar usuario
                        String nombreUsuario = session.getAttribute("nombreUsuario").toString();
                        
                        //Creo Fecha y Hora para la venta
                        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                        Date dateobj = new Date();
                        String fechaHora = df.format(dateobj);
                        
                        //1) Se agrega la compra a la tabla "ventas"
                        PreparedStatement stmnt7 = conexionDB.prepareStatement("INSERT INTO ventas (usuario, producto, monto, fechaHora) VALUES (?, ?, ?, ?)");
                        stmnt7.setString(1, nombreUsuario);
                        stmnt7.setString(2, nombreProductoComprado);
                        stmnt7.setFloat(3, precioProductoComprado);
                        stmnt7.setString(4, fechaHora);
                        //Ejecutar insercion de la venta en tabla "ventas"
                        int resultadoVenta = stmnt7.executeUpdate();
                        //cerrar flujo statement de la inserción de la venta
                        stmnt7.close();
                        
                        //2) Una vez hecha la insercion, actualiza stock (-1) en tabla "productos"
                        PreparedStatement stmnt8 = conexionDB.prepareStatement("UPDATE productos SET stock = ? WHERE id_producto = ?");
                        stmnt8.setInt(1, stockProducto);
                        stmnt8.setInt(2, idProductoComprado);
                        //ejecutar actualización de stock
                        int resultadoActStock = stmnt8.executeUpdate();
                        //cerrar flujo del statement de actualizacion de stock
                        stmnt8.close();                        
                        
                        //si se efectuó la venta y
                        //si se efectuó la actualización del stock
                        if ((resultadoVenta > 0) && (resultadoActStock > 0)) {                            
                            
                            //setear los atributos de la solicitud para mostrar el producto
                            request.setAttribute("nombreProductoComprado", nombreProductoComprado);
                            request.setAttribute("categoriaProductoComprado", categoriaProductoComprado);
                            request.setAttribute("precioProductoComprado", precioProductoComprado);
                            
                            //envia los datos para mostrar la compra efecutuada al cliente                                
                            RequestDispatcher rd = request.getRequestDispatcher("paginaMostrarCompraProducto.jsp");
                            rd.forward(request, response);                            
                        }
                    }//Caso contrario: no hay disponibilidad o no existe
                    else{
                            //no hay disponibilidad
                            if (stockProducto <= 0) {
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Sin disponibilidad</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image:url('imgs/fondo_error_1.png'); background-position: center;\">"); 
                                out.println("<div style=\"margin: 25% auto; width: 500px; border-style: double; border-radius: 20px\" >");
                                out.println("<h2 style=\"text-align: center;\">No hay disponibilidad del producto!</h2>");
                                out.println("<h1 style=\"text-align: center;\"> :( </h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaConsultaProducto.jsp'>");
                                out.println("<p style=\"text-align: center;\"> Espere por favor, será redireccionado en 5 segundos...</p>");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                            }//caso ultimo: la no existencia del producto
                            //En realidad no hace falta, porque el sistema muestra en la busqueda
                            //solo productos existentes en la BDs
                            else{
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Poducto No Existe</title>");
                                out.println("</head>");
                                out.println("<body style=\"background-image:url('imgs/fondo_error_1.png'); background-position: center;\">"); 
                                out.println("<div style=\"margin: 25% auto; width: 500px; border-style: double; border-radius: 20px;\" >");
                                out.println("<h2 style=\"text-align: center;\">El producto no existe!</h2>");
                                out.println("<h1 style=\"text-align: center;\"> :( </h1>");
                                out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaConsultaProducto.jsp'>");
                                out.println("<p style=\"text-align: center;\"> Espere por favor, será redireccionado en 5 segundos...</p>");
                                out.println("</div>");
                                out.println("</body>");
                                out.println("</html>");
                            }                            
                    }                    
                    //cerrar flujo del stament de busqueda del producto
                    stmnt.close();
        } catch (IOException | ClassNotFoundException | NumberFormatException | SQLException | ServletException error) {
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>ServletManejoCompraProducto</title>");
                            out.println("</head>");
                            out.println("<body style=\"background-image:url('imgs/fondo_error_1.png'); background-position: center;\">"); 
                            out.println("<div style=\"margin 25% auto; width: 500px; border-style: double; border-radius: 20px\" >");
                            out.println("<h1 style=\"text-align: center;\">Un error ha ocurrido en el proceso!</h1>");
                            out.println("<h2 style=\"text-align: center;\">" + error.toString() + "<h2>");
                            out.println("<h1 style=\"text-align: center;\"> :( </h1>");        
                            out.println("<meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaConsultaProducto.jsp'>");
                            out.println("<p style=\"text-align: center;\"> Espere por favor, será redireccionado en 5 segundos a busqueda nueva...</p>");
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
