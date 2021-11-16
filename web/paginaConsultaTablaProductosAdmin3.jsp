<%-- 
    Document   : paginaConsultaTablaProductosAdmin3
    Created on : 7 abr. 2021, 20:16:34
    Author     : al
--%>

<%@page import= "java.io.FileOutputStream" %>
<%@page import= "java.io.OutputStream" %>
<%@page import= "java.io.InputStream" %>
<%@page import= "java.io.FileInputStream"%>
<%@page import= "java.io.File" %>
<%@page contentType= "text/html" pageEncoding = "UTF-8" %>
<%@page import = "java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla Productos Farmacia</title>
    </head> 
    <body style="background-image: url('imgs/fondo_generico.jpg');">  
        <div align="center">
            <%
                //Recupero el RS
                ResultSet rs = (ResultSet) request.getAttribute("rsResultadoTablaProductos");
            %>
            <h1>Tabla Productos Farmacia</h1>        
            <table class="table table-striped" style="border-style: double; border-radius: 20px;">
                <tr>   
                    <th>ID</th>
                    <th>NOMBRE</th>
                    <th>CATEGORIA</th>
                    <th>STOCK</th>
                    <th>PRECIO</th>
                    <th>IMAGEN</th>                                    
                </tr>
                <%                      
                    while(rs.next()) {
                %>     
                <tr>
                    <td><%  pageContext.setAttribute("idProducto", rs.getInt(1)); %> <!-- setear el id producto -->
                            ${pageContext.getAttribute("idProducto")} <!-- mostrar id producto -->
                    </td> 
                    <td><%  pageContext.setAttribute("nombreProducto", rs.getString(2)); %> 
                            ${pageContext.getAttribute("nombreProducto")}
                    </td> 
                    <td><%  pageContext.setAttribute("categoriaProducto", rs.getString(3)); %>
                            ${pageContext.getAttribute("categoriaProducto")}
                    </td> 
                    <td><% pageContext.setAttribute("stockProducto", rs.getInt(4)); %> 
                           ${pageContext.getAttribute("stockProducto")}
                    </td> 
                    <td><% pageContext.setAttribute("precioProducto", rs.getInt(5)); %> 
                           ${pageContext.getAttribute("precioProducto")}
                    </td> 
                    <!-- mostrar imagen -->
                    <td><img src='imgs/${pageContext.getAttribute("nombreProducto")}.jpg' width="100" height="100" alt = "Imagen Producto" ></td> 
                </tr>
                <%
                        out.println("\n");
                    }
                %>            
            </table>
            <br>
            <a href="/AplicacionWebFarmaciaAyza/paginaAdmin.jsp"><button>Volver</button></a> 
            <a href="/AplicacionWebFarmaciaAyza/redirect.jsp"><button>Cerrar Sesión</button></a>
        </div>
    </body>
</html>
