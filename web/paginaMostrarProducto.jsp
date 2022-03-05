<%-- 
    Document   : ConsultaProductoUsuario
    Created on : 9 feb. 2021, 19:04:26
    Author     : al
--%>
    


<%@page import= "javax.imageio.stream.ImageOutputStream" %>
<%@page import= "org.springframework.util.Base64Utils" %>
<%@page import= "java.io.FileOutputStream" %>
<%@page import= "java.io.File" %>
<%@page import= "java.io.BufferedOutputStream" %>
<%@page import= "java.io.BufferedInputStream" %>
<%@page import= "java.io.OutputStream" %>
<%@page import= "javax.swing.ImageIcon" %>
<%@page import= "java.io.ByteArrayInputStream" %>
<%@page import= "javax.imageio.ImageIO" %>
<%@page import= "java.awt.image.BufferedImage" %>
<%@page import= "java.awt.Image" %>
<%@page import= "java.io.InputStream" %>
<%@page import= "java.io.FileInputStream" %>
<%@page import= "java.util.ArrayList" %>
<%@page import= "javax.swing.JOptionPane" %>
<%@page import= "javax.servlet.http.HttpServlet" %>
<%@page contentType = "text/html" pageEncoding= "UTF-8" %>
<%@page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina Articulo</title>
    </head> 
    <body style= "background-image: url('imgs/fondo_generico.jpg');">
        <div align="center" style="width:600px; height: 340px; margin: 25% auto; color: brown; border-radius: 20px; border-style: double;">
            <%
                //Obtener el atributo del Servlet Cliente
                //String nombreProductoComprado = request.getAttribute("nombreProductoComprado").toString();

                /*Inicializar variables */
                String mensajeResultado = "Base de datos actualizada...";
                String urlDB = "jdbc:mysql://localhost:3306/farmaciaOnline";
                String usuarioDB = "root";
                String psswdDB = "";
                String driverDB = "com.mysql.cj.jdbc.Driver";

                /* Paso4) Conexión a la base de datos */
                Connection conexionDB = null;

                //Intentar conectar a BD y ejecutar las operaciones
                try {
                    Class.forName(driverDB);
                    conexionDB = DriverManager.getConnection(urlDB, usuarioDB, psswdDB);
                    //paso el ID
                    String idProductoBuscado = request.getParameter("productoBuscado");
                    //Hacer la consulta por ID
                    PreparedStatement stmnt = conexionDB.prepareStatement("SELECT nombre, categoria, precio, imagen FROM productos WHERE id_producto = ?");
                    stmnt.setInt(1, Integer.parseInt(idProductoBuscado));
                    ResultSet rs = stmnt.executeQuery();                    
                    //Si hay coincidencia
                    if (rs.next()) {
                        pageContext.setAttribute("nombreProducto",rs.getString(1));
                        pageContext.setAttribute("categoriaProducto",rs.getString(2));
                        pageContext.setAttribute("precioProducto",rs.getInt(3));                        
                        pageContext.setAttribute("idProducto",idProductoBuscado);                    
            %>
                        <h1 style="text-align: center;">Articulo a comprar:</h1>                       
                        <form method="POST" action="ServletManejoCompra2">                            
                            <table class="table table-striped" style="border-style: double;  border-radius: 20px; color: forestgreen;">                
                                <tr>                                
                                    <th>ARTICULO</th>
                                    <th>CATEGORIA</th>
                                    <th>PRECIO</th>
                                    <th>PRODUCTO</th>
                                </tr>

                                <tr>
                                    <td>${pageContext.getAttribute("nombreProducto")}</td>
                                    <td>${pageContext.getAttribute("categoriaProducto")}</td>
                                    <td>${pageContext.getAttribute("precioProducto")}</td> 
                                    <td>
                                        <img src='imgs/${pageContext.getAttribute("nombreProducto")}.jpg' width="100" height="100" alt = "Imagen Producto" >        
                                    </td>                        
                                    <!--Hiddens para enviar a la jsp que muestra la compra -->
                                    <td><input type="hidden" value="${pageContext.getAttribute("nombreProducto")}" name="nombreProductoComprado"></td>
                                    <td><input type="hidden" value="${pageContext.getAttribute("categoriaProducto")}" name="categoriaProductoComprado"></td>
                                    <td><input type="hidden" value="${pageContext.getAttribute("precioProducto")}" name="precioProductoComprado"></td>
                                    <td><input type="hidden" value="${pageContext.getAttribute("idProducto")}" name="idProductoComprado"></td>
                                </tr>
                            </table>                            
                            <br>
                            <input type="submit" value="Comprar!"/>
                            <br>
                            <br>                            
                        </form>
                            <a href="/AplicacionWebFarmaciaAyza/paginaConsultaProducto.jsp"><button>Volver</button></a> 
                            <a href="/AplicacionWebFarmaciaAyza/redirect.jsp"><button style="margin-left: 1%;">Cerrar Sesión</button></a>
            <%                
                } else {
            %>
                    <h2>Por el momento no hay productos disponibles :( ... disculpe las molestias </h2>
                    <meta http-equiv='Refresh' content='5; url=/AplicacionWebFarmaciaAyza/paginaConsultaProducto.jsp'>
                    <p> Espere por favor, será redireccionado en 5 segundos...</p>
            <%
                 }
                    //cerrar flujos 
                    stmnt.close();
                } catch (ClassNotFoundException e) {
                    mensajeResultado = " Error creando el driver!";
                    mensajeResultado += "\n" + e.toString();
                } catch (SQLException e) {
                    mensajeResultado = " Error procesando el SQL!";
                    mensajeResultado += "\n" + e.toString();
                } finally {
                    /* Cerramos */
                    try {
                        if (conexionDB != null) {
                            conexionDB.close();
                        }
                    } catch (SQLException e) {
                        mensajeResultado = "Error al cerrar la conexión.";
                        mensajeResultado += " <br/>" + e.toString();
                    }
                }
            %>
        </div>
    </body>
</html>
