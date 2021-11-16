<%-- 
    Document   : AltaProducto
    Created on : 2 feb. 2021, 20:16:44
    Author     : al
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alta Producto</title>
    </head> 
    <body style="background-image: url('imgs/fondo_generico.jpg');">
        <div align="center" style="color: darkred; border-style: double; margin: 25% auto; height: 300px; width: 500px; border-radius: 20px;">
            <h1 style="text-align: center;">Alta Producto</h1>
            <form name="altaProducto" action="ServletManejarTablaAdmin" method="POST"> <!-- enctype = "multipart/form-data"-->
                <table>
                    <tr>
                        <td>Nombre</td><td><input name="nombre"/></td>    
                    </tr>
                    <tr>
                        <td>Categoria</td><td><input name="categoria"/></td>
                    </tr>
                    <tr>
                        <td>Stock</td><td><input name="stock"/></td>    
                    </tr>
                    <tr>
                        <td>Precio</td><td><input name="precio"/></td>
                    </tr>
                    <tr>
                        <td>Añadir imagen</td><td><input name="archivoImagen" id="archivo" type="file"/></td>
                    </tr>
                    <tr>
                    <input type="hidden" value="agregar" name="accion">                    
                    </tr>
                </table>
                <input type="submit" value="Dar alta"/>
            </form>  
            <br>
            <a href="/AplicacionWebFarmaciaAyza/paginaAdmin.jsp" ><button>Volver</button></a> 
            <a href="/AplicacionWebFarmaciaAyza/redirect.jsp" ><button>Cerrar Sesión</button></a>
        </div>
    </body>
</html>
