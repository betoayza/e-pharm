<%-- 
    Document   : paginaPrincipalUsuario
    Created on : 21 jun. 2021, 20:48:24
    Author     : al
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina Principal Usuario</title>
    </head>
    <body style="background-image:url('imgs/fondo_pildoras.jpg');">
        <div style= "background-image:url('imgs/fondo_div_beige.png'); width: 450px; height: 230px; border-radius: 20px; margin: 25% auto; border-style: double;"> 
            <form action="ServletManejarOpcionesUsuario" method="POST">             
                <h1 style="color: brown; text-align: center">Pagina de Usuario</h1>        
                <h2 style="color: gray; text-align: center">Elija la opcion que desea:</h2>                               
                  <select id="accion" name="accion" style="margin-left: 20%;;">                  
                        <option value="0">Seleccione:</option>
                        <option value="verCompras">Ver Mis Compras</option>
                        <option value="buscarProducto">Buscar Producto</option>                        
                  </select>
                  <input type="hidden" value="formularioNoEnviado" name="checador">
                  <input type="submit" value="Elegir" style="margin-left: 5%;">
                  <br>
                  <br>                                          
            </form>         
            <a href = "/AplicacionWebFarmaciaAyza/redirect.jsp" style="margin-left: 40%;"><button>Cerrar Sesion</button></a>
        </div>
    </body>
</html>
