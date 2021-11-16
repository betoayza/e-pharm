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
                        <td>Nombre:</td><td><input name="nombreNuevo"/></td>    
                    </tr>
                    <tr>
                        <td>Categoria:</td><td><input name="categoriaNueva"/></td>
                    </tr>
                    <tr>
                        <td>Stock:</td><td><input name="stockNuevo"/></td>    
                    </tr>
                    <tr>
                        <td>Precio:</td><td><input name="precioNuevo"/></td>
                    </tr>
                    <tr>
                        <td>Imagen:</td><td><input name="archivoImagenNueva" id="archivo" type="file"/></td>    
                    </tr>
                    <tr>
                        <td><input type="hidden" name="checador2" value="formularioEnviado"/></td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="accion" value="modificar"/></td>
                    </tr>
                </table>
                <div id="contenedor"></div>
                <input type="submit" value="Enviar">
            </form>
            <br>
            <a href = "/AplicacionWebFarmaciaAyza/paginaAdmin.jsp"><button>Volver</button></a>
            <a href = "/AplicacionWebFarmaciaAyza/redirect.jsp"><button>Cerrar Sesion</button></a>
        </div>      
        
        <script type="text/javascript">
            //En base a la columna seleccionada
            //1) Abrir un cuadro de texto
            //ó
            //2) Abrir una ventana para seleccionar la imagen
            
            function realizarAccion(opcion){
                console.log("Realizando accion",opcion.value);
                valueOpcion = document.getElementById("columna").value=opcion.value;
                //Si se eligio "imagen", abrir ventana para subir la imagen
                if(valueOpcion===4){               
                    var container = document.getElementById('container');
                    container.innerHTML = '<b><input type="file" id="filechooser" name="nueva_imagen"> </b>';
                }                  
                //Caso contrario, abrir una caja de texto html para cambiar columna
                else{                      
                    var container = document.getElementById('container');
                    container.innerHTML = '<b><input type="text" name="nueva_info"></b>';
                }
            }
</script>
    </body>
</html>
