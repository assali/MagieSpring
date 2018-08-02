
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
                <table class="table ">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Nom</th> 
                            <th scope="col">Rejoindre</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listParties}" var="partie" >
                            <tr>
                                <th scope="row">${partie.id}</th>
                                <td>${partie.nom}</td>
                                <td><a class="linkStyle" href="<c:url value="/rejoindre-partie" />?id=${partie.id}">click pour rejoindre</a> </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
            <c:import url="_PIED.jsp"/>
            <c:import url="_JAVASCRIPTS.jsp"/>
        </div>
    </body>
</html>
