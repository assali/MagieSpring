<%-- 
    Document   : _TEMPLATE
    Created on : 10 juil. 2018, 16:42:35
    Author     : Administrateur
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Cache-Control" content="no-cache" />
        <meta http-equiv="Content-Type" content="te xt/html; charset=UTF-8">
        <title>Home Page</title>
        <c:import url="_STYLESHEETS.jsp"/>
    </head>
    <body>
        <div id="container">
            <c:import url="_TITRE.jsp"/>
            <c:import url="_MENU.jsp"/>
            <div id="contenu">
                <form method="POST" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="pseudo">pseudo</label>
                        <input type="text" class="form-control" name="pseudo" placeholder="pseudo">
                    </div>
                    <div class="form-group" >
                        <label for="avatar">Avatar</label>
                        <input type="file" name="avatar" value="Upload">
                    </div>
                    <button type="submit" class="btn btn-primary">Rejoindre</button>
                </form>
            </div>
            <c:import url="_PIED.jsp"/>
            <c:import url="_JAVASCRIPTS.jsp"/>
        </div>
    </body>
</html>
