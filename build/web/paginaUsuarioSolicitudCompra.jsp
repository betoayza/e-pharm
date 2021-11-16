<%-- 
    Document   : paginaUsuarioComun
    Created on : 30 ene. 2021, 21:03:19
    Author     : al
--%>

<%@ page import = "java.sql.*" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comprar Producto</title>
    </head>
    <body>    

        <!-- Envio datos del formulario a una JSP que me va a mostrar la lista de resultados en base a nombreArticulo y tipoArticulo -->
        <form action="/AplicacionWebFarmaciaAyza/ServletManejoCompra.jsp" method="POST">
            <div align="center"> 
                <p>Detalles del producto:</p>                              
                <table>
                    <tr>
                        <th>Nombre</th>
                        <th>Categoria</th>
                        <th>Precio</th>
                        <th>Imagen</th>
                    <tr>
                        <%
                            /*Inicializar variables */
                            String mensajeResultado = "Base de datos actualizada...";
                            String urlDB = "jdbc:mysql://localhost:3306/farmaciaOnline";
                            String usuarioDB = "root";
                            String psswdDB = "";
                            String driverDB = "com.mysql.jc.jdbc.Driver";

                            /* Paso4) Conexión a la base de datos */
                            Connection conexionDB = null;

                            //Intentar conectar a BD y ejecutar las operaciones
                            try {
                                Class.forName(driverDB);
                                conexionDB = DriverManager.getConnection(urlDB, usuarioDB, psswdDB);

                                //Hacer la consulta
                                String nombreProductoBuscado = request.getParameter("productoBuscado");
                                String consulta = "SELECT nombre, categoria, precio, imagen WHERE nombre = '" + request.getParameter("productoBuscado");
                                Statement stmnt = conexionDB.createStatement();
                                ResultSet rs = stmnt.executeQuery(consulta);
                                while (rs.next()) {
                        %> 
                    <tr>
                        <td name="nombre"><%  rs.getString(1); %></td>
                        <td name="categ"><%  rs.getString(2); %></td>
                        <td name="precio"><%  rs.getInt(3); %></td>
                        <td name="imagen"><%  rs.getBlob(4);%></td> 
                    </tr>                            
                    <%
                        }
                    %>
                </table> 
                <input type="submit" value="Comprar"> 
            </div>
        </form>
        <%
                //cerrar flujo Statement
                stmnt.close();
                //Mostrar resultado busqueda producto
            } catch (ClassNotFoundException e) {
                mensajeResultado = " Error creando el driver!";
                mensajeResultado += " <br/>" + e.toString();
            } catch (SQLException e) {
                mensajeResultado = " Error procesando el SQL!";
                mensajeResultado += " <br/>" + e.toString();
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
    </body>
</html>
