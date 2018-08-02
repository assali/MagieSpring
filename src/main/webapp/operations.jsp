<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form method="post">
    <table class="table">
        <thead>
            <tr>
                <th scope="col" colspan="4">Passer Tour Ou Lancer Sort</th>

            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${valid != null}">
                    <tr>                              
                        <td> <button  id="btnPasseTour" value="passe" name="action">Passer Tour</button></td> 
                        <td><select name="sorts">
                                <c:forEach items="${sorts}" var="s">
                                    <option value="${s}">${s}</option>

                                </c:forEach>

                            </select> 
                            <button  id="btnLanceSort" value="sort" name="action" >Lancer Sort</button></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>                              
                        <td> <button  id="btnPasseTour" disabled>Passer Tour</button></td> 
                        <td> <button id="btnLanceSort" disabled>Lancer Sort</button></td> 

                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</form>
