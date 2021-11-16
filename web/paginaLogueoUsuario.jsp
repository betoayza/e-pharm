<%-- 
    Document   : logueoUsuario
    Created on : 30 ene. 2021, 20:49:00
    Author     : al
--%>

<!--Esta pagina dinamica llama al Servlet correspondiente para procesar el pedido-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio Sesion</title>
    </head>
    <body style= "background-image:url('imgs/fondo_index3.jpg');" > 
        <div align="center" style="border-style: double; border-radius: 20px; width: 500px; margin: 0 auto;">
            <h1>Inicio de Sesion</h1>
            <form action="ServletManejoLogueoUsuario2" method="POST">
                <table style="margin-left: auto; margin-right: auto;">
                    <tr>
                        <td>Usuario</td><td><input name="usuario"/></td>    
                    </tr>
                    <tr>
                        <td>Password</td><td><input name="password"/></td>
                    </tr>
                </table>
                <br>
                <input type="submit" value="Ingresar"/>                 
            </form>            
            <a href="/AplicacionWebFarmaciaAyza/redirect.jsp"><button>Volver</button></a>
        </div>
    </body>
</html>
