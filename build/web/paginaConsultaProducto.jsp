<%-- 
    Document   : paginaPrincipalUsuarioConsultaProducto
    Created on : 12 feb. 2021, 19:27:49
    Author     : al
--%>

<%@page import= "java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscar Producto</title>
    </head>
    <body style= "background-image:url('imgs/fondo_index4.jpg'); background-size: 550px 700px;">   
        <div style= "background-color: silver; width: 600px; height: 150px; margin: 25% auto; border-radius: 20px; border-style: double;">
            <h1 style="color: blanchedalmond; text-align: center;">Productos Disponibles:</h1>
            <%
            /*Inicializar variables */
                String mensajeResultado = "Base de datos actualizada...";
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
                    PreparedStatement prprdStmnt = conexionDB.prepareStatement("SELECT id_producto, nombre FROM productos");
                    ResultSet rs = null;
                    rs = prprdStmnt.executeQuery();
                    //rs.beforeFirst();
                    %>
                        <form action="paginaMostrarProducto.jsp" method="POST" style="margin-left: 25%;">            
                            <select name="productoBuscado">
                                <% while(rs.next()){ %>
                                    <option value=<%= rs.getInt(1) %>><%= rs.getString(2) %></option>
                                <% } %>
                            </select>
                        <input type="submit" value="Ver Producto"/>
                    </form>
                    
                    <%


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
            <br>
            <a href="/AplicacionWebFarmaciaAyza/paginaPrincipalUsuario.jsp" style="margin-left: 40%;"><button>Volver</button></a>
            <a href="/AplicacionWebFarmaciaAyza/redirect.jsp" style="margin-left: 1%;"><button>Cerrar Sesión</button></a>            
        </div>
    </body>
</html>
