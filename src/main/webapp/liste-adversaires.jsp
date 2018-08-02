<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<table class="table">
    <thead>
        <tr>
            <th scope="col" colspan="4">List Adversaires </th>

        </tr>
    </thead>
    <tbody>
        <tr class="carteDisplay">                              
            <c:forEach items="${joueurs}" var="jo">
                <td >
                    <div class="joueur" >
                        <img  src="<c:url value="${jo.avatar}"/>" alt="avatar" class="img-thumbnail "/>
                    </div>
                </td> 
            </c:forEach>                       
        </tr>
    </tbody>
</table>
