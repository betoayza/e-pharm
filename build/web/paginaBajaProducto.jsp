<%-- 
    Document   : BajaProducto
    Created on : 4 feb. 2021, 19:52:07
    Author     : al
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina Bajas</title>
    </head>
    <body style="background-image: url('imgs/fondo_generico.jpg');"> 
        <div align="center" style="margin: 25% auto; color: blueviolet; border-style: double; height: 250px; width: 600px; border-radius: 20px;">
            <h1>Elija ID Producto a eliminar</h1>
            <form action="ServletManejarTablaAdmin" method="POST">
                <table>
                    <tr>
                        <td>ID a eliminar</td><td><input name="id_productoBaja"/></td>    
                    </tr>
                    <tr>
                        <td><input name="accion" value="eliminar" type="hidden"/></td>                    
                    </tr>
                    <tr>
                        <td><input name="checador2" value="formularioEnviado" type="hidden"/></td>
                    </tr>                                        
                </table> 
                <input type="submit" value="Baja!"/>
            </form>
            <br>
            <a href="/AplicacionWebFarmaciaAyza/paginaAdmin.jsp"><button>Volver</button></a> 
            <br>
            <a href="/AplicacionWebFarmaciaAyza/redirect.jsp" ><button>Cerrar Sesión</button></a>
        </div>
    </body>
</html>
