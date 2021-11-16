<%-- 
    Document   : paginaConsultarTablaUsuarios
    Created on : 28 may. 2021, 21:47:51
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
        <title>Tabla Usuarios Farmacia</title>
    </head>
    <body style="background-image: url('imgs/fondo_generico.jpg');">  
        <div align="center" style="color: brown;">
            <%
                //Recupero el RS
                ResultSet rs = (ResultSet) request.getAttribute("rsResultadoTablaUsuarios");
            %>
            <h1>Tabla Usuarios Farmacia</h1>       
            <table class="table table-striped" style="color: forestgreen; border-style: double; border-radius: 20px;">
                <tr>   
                    <th>ID</th>
                    <th>USUARIO</th>
                    <!--<th>PASSWORD</th> -->
                    <th>TIPO</th>
                    <th>NOMBRE</th>   
                    <th>APELLIDO</th>
                </tr>
                <%  
                    while (rs.next()) {
                %>     
                <tr>
                    <td><% int idUsuario = rs.getInt(1); pageContext.setAttribute("idUsuario", rs.getInt(1)); %> ${pageContext.getAttribute("idUsuario")} </td> 
                    <td><% String nombreUsuario = rs.getString(2); pageContext.setAttribute("nombreUsuario",rs.getString(2)); %> ${pageContext.getAttribute("nombreUsuario")}</td> 
                    <!-- <td><% //String contrasenia = rs.getString(3); pageContext.setAttribute("contrasenia", rs.getString(3)); %> ${pageContext.getAttribute("contrasenia")}</td> --> 
                    <td><% String tipoUsuario = rs.getString(4); pageContext.setAttribute("tipoUsuario", rs.getString(4)); %> ${pageContext.getAttribute("tipoUsuario")}</td> 
                    <td><% String nombre = rs.getString(5); pageContext.setAttribute("nombre", rs.getString(5)); %> ${pageContext.getAttribute("nombre")}</td> 
                    <td><% String apellido = rs.getString(6); pageContext.setAttribute("apellido", rs.getString(6)); %> ${pageContext.getAttribute("apellido")}</td> 
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
