<%-- 
    Document   : registroUsuario
    Created on : 30 ene. 2021, 21:41:50
    Author     : al
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro Usuario</title>
    </head>
    <body style= "background-image:url('imgs/fondo_index3.jpg');">
        <div align="center" style="border-style: double; border-radius: 20px; width: 500px; margin: 0 auto;">
            <h1>Datos registro</h1>
            <form action="/AplicacionWebFarmaciaAyza/ServletManejoRegistro" method="POST">
                <table>
                    <tr>
                        <td>Usuario:</td><td><input name="usuario"/></td>    
                    </tr>
                    <tr>
                        <td>Password:</td><td><input name="password"/></td>
                    </tr>
                    <tr>
                        <td>Nombre:</td><td><input name="nombre"/></td>    
                    </tr>
                    <tr>
                        <td>Apellido:</td><td><input name="apellido"/></td>
                    </tr>
                </table>
                <input type="submit" value="Enviar""/>
            </form>
            <a href="/AplicacionWebFarmaciaAyza/redirect.jsp" target="_parent"><button>Volver</button></a>
        </div>
    </body>
</html>
