<%-- 
    Document   : paginaMostrarCompraProducto
    Created on : 6 mar. 2021, 19:54:38
    Author     : al
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compra Exitosa</title>
    </head>
    <body style= "background-image: url('imgs/fondo_generico.jpg');">
        <h1 align= "center" style="color: darkred;">Compra exitosa!</h1>
        <div align="center" style="color: darkgoldenrod; border-style: double; margin: auto; height: 275px; width: 500px; border-radius: 20px;">            
            <h1>Detalles:</h1>
            <%
                //obtiene los atributos pasados de la solicitud
                String nombreProductoComprado  = request.getAttribute("nombreProductoComprado").toString(); 
                String categoriaProductoComprado = request.getAttribute("categoriaProductoComprado").toString(); 
                float precioProductoComprado = (float) request.getAttribute("precioProductoComprado");  
                //setea los atributos del Contexto de la pagina
                pageContext.setAttribute("categoriaProductoComprado",categoriaProductoComprado);
                pageContext.setAttribute("precioProductoComprado",precioProductoComprado);
                pageContext.setAttribute("nombreProductoComprado", nombreProductoComprado);
            %>
            <table class="table table-striped">                
                <tr>                                
                    <th>NOMBRE</th>
                    <th>CATEGORIA</th>
                    <th>PRECIO</th>
                    <th>PRODUCTO</th>
                </tr>

                <tr>
                    <td>${pageContext.getAttribute("nombreProductoComprado")}</td>
                    <td>${pageContext.getAttribute("categoriaProductoComprado")}</td>
                    <td>${pageContext.getAttribute("precioProductoComprado")}</td>                    
                    <td><img src='imgs/${pageContext.getAttribute("nombreProductoComprado")}.jpg' width="100" height="100" alt = "Imagen Producto" ></td> 
                </tr>
            </table> 
        <br>
        <a href="/AplicacionWebFarmaciaAyza/paginaConsultaProducto.jsp" ><button>Volver</button></a> 
        <a href="/AplicacionWebFarmaciaAyza/redirect.jsp"><button>Cerrar Sesión</button></a>
        </div>
        
        
    </body>
</html>
