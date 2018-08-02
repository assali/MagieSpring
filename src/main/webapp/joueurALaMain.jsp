<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<table class="table">
    <thead>
        <tr>
            <th scope="col" colspan="4">Joueur Qui A La Main </th>

        </tr>
    </thead>
    <tbody>

           <c:choose>
                <c:when test="${vous != null}">
                    <tr>                              
                       <td> Vous avez la Main</td> 
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>                              
                          <td> ${main.pseudo}</td> 
                    </tr>
                </c:otherwise>
            </c:choose>
    </tbody>
</table>
