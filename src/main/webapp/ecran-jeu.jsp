


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

                <div id="adversaires"></div>
                <div id="cartes"></div>
                <div id="aLaMAin"></div>
                <div id="op"></div>
            </div>
            <c:import url="_PIED.jsp"/>
            <c:import url="_JAVASCRIPTS.jsp"/>
            <script>
                $(document).ready(function (e) {
                    // $('#adversaires').load("liste-adversaires");
                    // $('#cartes').load("liste-cartes");
                    //$('#aLaMAin').load("joueurALaMain");
                    // $('#op').load("operations");

                    $.ajax({
                        url: 'liste-adversaires',
                        success: function (responseText) {
                            $('#adversaires').html(responseText);
                            $('.joueur').click(function (e) {
                                $(this).css("border-color", "red");

                            });
                            $('.joueur').dblclick(function (e) {
                                $(this).css("border-color", "black");

                            });
                        }
                    });
                    $.ajax({
                        url: 'liste-cartes',
                        success: function (responseText) {
                            $('#cartes').html(responseText);
                            $('.carte').click(function (e) {
                                $(this).css("border-color", "red");

                            });
                            $('.carte').dblclick(function (e) {
                                $(this).css("border-color", "black");

                            });
                        }
                    });
                    $.ajax({
                        url: 'joueurALaMain',
                        success: function (responseText) {
                            $('#aLaMAin').html(responseText);
                        }
                    });
                    $.ajax({
                        url: "operations",
                        success: function (responseText) {
                            $('#op').html(responseText);
                        }

                    });

                    $("#btnPasseTour").click(function (responseText) {
                        alert("you will receive one extra carte.");
                       // $('#cartes').html(responseText);
                       // $('#aLaMAin').html(responseText);

                    });
                });
            </script>
        </div>
    </body>
</html>

