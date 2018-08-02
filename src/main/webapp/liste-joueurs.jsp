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
                <form method="post">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Pseudo</th>
                                <th scope="col"> Avatar</th>
                                <th scope="col">Nb Parties Jouées</th>
                                <th scope="col">Nb Parties Gagnées</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listJoueurs}" var="joueur" >
                                <tr>                     
                                    <th scope="row">${joueur.id}</th>
                                    <td>${joueur.pseudo}</td>     
                                    <td >
                                        <div class="joueur">
                                            <img src="<c:url value="${joueur.avatar}"/>" alt="avatar" class="img-thumbnail "/>
                                        </div>
        
                                    </td> 
                                    <td>${joueur.nbPartiejouees}</td>
                                    <td>${joueur.nbPartiesGagnees}</td>
                                </tr>
                            </c:forEach>

                            <c:if test="${nbjoueurs!= null}">

                                <tr>
                                    <td>
                                    <td><a id="ecran" class="linkStyle" href="<c:url value="/demarrer-partie" />?id=${partieId}">click pour demarrer</a> </td>
                                    </td>
                                </tr>
                            </c:if>

                        </tbody>
                    </table>
                </form>
            </div>
            <c:import url="_PIED.jsp"/>
            <c:import url="_JAVASCRIPTS.jsp"/>
        </div>
    </body>
</html>
