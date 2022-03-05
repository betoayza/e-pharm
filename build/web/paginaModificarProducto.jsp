<%-- 
    Document   : ModificarProducto
    Created on : 4 feb. 2021, 20:17:59
    Author     : al
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Producto</title>
    </head> 
    <body style="background-image: url('imgs/fondo_generico.jpg');">
        <div align="center" style="color: darkgoldenrod; border-style: dotted; width: 600px; margin: 25% auto; height: 320px; border-radius: 20px;">
            <h1 style="color: cadetblue;">Modifique un producto</h1>
            <form action="ServletManejarTablaAdmin" method="POST">            
                <table>    
                    <tr>
                        <td>ID (producto a modificar):</td><td><input name="id_producto"/></td>    
                    </tr>
                    <tr>
                        <td>Nuevo nombre:</td><td><input name="nombreNuevo"/></td>    
                    </tr>
                    <tr>
                        <td>Nueva categoria:</td><td><input name="categoriaNueva"/></td>
                    </tr>
                    <tr>
                        <td>Nuevo stock:</td><td><input name="stockNuevo"/></td>    
                    </tr>
                    <tr>
                        <td>Nuevo precio:</td><td><input name="precioNuevo"/></td>
                    </tr>
                    <tr>
                        <td>Nueva Imagen:</td><td><input name="archivoImagenNueva" id="archivo" type="file"/></td>    
                    </tr>
                    <tr>
                        <td><input type="hidden" name="checador2" value="formularioEnviado"/></td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="accion" value="modificar"/></td>
                    </tr>
                </table>                
                <input type="submit" value="Enviar">
            </form>
            <br>
            <a href = "/AplicacionWebFarmaciaAyza/paginaAdmin.jsp"><button>Volver</button></a>
            <a href = "/AplicacionWebFarmaciaAyza/redirect.jsp"><button>Cerrar Sesion</button></a>
        </div>            
    </body>
</html>
