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
<%@page contentType= "text/html" pageEncoding="UTF-8" %>
<%@page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla Ventas Farmacia</title>
    </head> 
    <body style="background-image: url('imgs/fondo_generico.jpg');">  
        <div align="center" style="color: brown;">
            <%
                //Recupero el RS
                ResultSet rs = (ResultSet) request.getAttribute("rsResultadoTablaVentas");
            %>
            <h1>Tabla Ventas Farmacia</h1>        
            <table class="table table-striped" style="color: forestgreen; border-style: double; border-radius: 20px;">
                <tr>   
                    <th>CODIGO</th>
                    <th>USUARIO</th>
                    <th>PRODUCTO</th>
                    <th>MONTO</th>
                    <th>FECHA/HORA</th>                                                      
                </tr>
                <%  
                    //Recorrer todas la ventas
                    while (rs.next()) {
                %>     
                <tr>
                    <td><% int idVenta = rs.getInt(1); pageContext.setAttribute("idVenta", rs.getInt(1)); %> ${pageContext.getAttribute("idVenta")} </td> 
                    <td><% String nombreUsuario = rs.getString(2); pageContext.setAttribute("nombreUsuario",rs.getString(2)); %> ${pageContext.getAttribute("nombreUsuario")}</td> 
                    <td><% String nombreProducto = rs.getString(3); pageContext.setAttribute("nombreProducto", rs.getString(3)); %> ${pageContext.getAttribute("nombreProducto")}</td> 
                    <td><% float montoProducto = rs.getFloat(4); pageContext.setAttribute("montoProducto", rs.getFloat(4)); %> ${pageContext.getAttribute("montoProducto")}</td> 
                    <td><% String fechaHoraVenta = rs.getString(5); pageContext.setAttribute("fechaHoraVenta", rs.getString(5)); %> ${pageContext.getAttribute("fechaHoraVenta")}</td> 
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
