<%-- 
    Document   : paginaUsuarioAdmin
    Created on : 30 ene. 2021, 21:03:42
    Author     : al
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
    </head> 
    <body style="background-image: url('imgs/fondo_generico.jpg');">
        <div align="center" style="background-image: url('imgs/fondo_div_rojo.jpg'); position: relative; color: aquamarine; margin: auto; width: 600px; height: 200px; border-radius: 20px; margin: 25% auto; border-style: double;"> 
            <form action="ServletManejarTablaAdmin" method="POST">             
                <h1>Pagina de Administrador</h1>        
                <h2>Elija la opcion que desea:</h2>                               
                  <select id="accion" name="accion" >                  
                        <option value="0">Seleccione:</option>
                        <option value="agregar">Alta Producto</option>
                        <option value="eliminar">Baja Producto</option>
                        <option value="modificar">Modificar Producto</option>                        
                        <option value="consultarTablaProductos">Consultar Productos</option>
                        <option value="consultarTablaVentas">Consultar Ventas</option>
                        <option value="consultarTablaUsuarios">Consultar Usuarios</option>
                  </select>
                  <input type="hidden" value="formularioNoEnviado" name="checador">
                  <input type="submit" value="Elegir">
                  <br>
                  <br>                                          
        </form>         
        <a href = "/AplicacionWebFarmaciaAyza/redirect.jsp"><button>Cerrar Sesion</button></a>
        </div>
    </body>
</html>
