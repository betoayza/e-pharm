<%-- 
    Document   : paginaConsultarMisComprasUsuario
    Created on : 23 jun. 2021, 19:56:41
    Author     : al
--%>
<%@page import = "java.sql.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consulta Mis Compras</title>
    </head>
    <body style="background-image: url('imgs/fondo_generico.jpg');">  
        <div align="center">
            <%
                //Recupero el RS
                ResultSet rs = (ResultSet) request.getAttribute("rsResultadoMisCompras");
            %>
            <h1>Mis Compras</h1>       
            <table class="table table-striped" style="border-style: double; border-radius: 20px ">
                <tr>  
                    <th>PRODUCTO</th>
                    <th>MONTO</th>
                    <th>FECHA/HORA</th>                                                      
                </tr>
                <%  
                    while (rs.next()) {
                %>     
                <tr>                    
                    <td><%  pageContext.setAttribute("nombreProducto", rs.getString(1)); %> ${pageContext.getAttribute("nombreProducto")}</td> 
                    <td><%  pageContext.setAttribute("montoProducto", rs.getFloat(2)); %> ${pageContext.getAttribute("montoProducto")}</td> 
                    <td><%  pageContext.setAttribute("fechaHoraVenta", rs.getString(3)); %> ${pageContext.getAttribute("fechaHoraVenta")}</td> 
                </tr>
                <%
                        out.println("\n");
                    }
                %>            
            </table>
            <br>
            <a href="/AplicacionWebFarmaciaAyza/paginaPrincipalUsuario.jsp"><button>Volver</button></a> 
            <a href="/AplicacionWebFarmaciaAyza/redirect.jsp"><button>Cerrar Sesión</button></a>
        </div>
    </body>
</html>
