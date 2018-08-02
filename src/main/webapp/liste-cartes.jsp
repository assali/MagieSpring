<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table ">
    <thead>
        <tr>
            <th scope="col" colspan="4">List Cartes </th>

        </tr>
    </thead>
    <tbody>
        <tr class="carteDisplay">                              
            <c:forEach items="${cartes}" var="ca">

                <td ><div class="carte"><img  src="${ca.path}" alt="carte" class="img-thumbnail "/></div></td> 
                </c:forEach>                     
        </tr>
    </tbody>
</table>
