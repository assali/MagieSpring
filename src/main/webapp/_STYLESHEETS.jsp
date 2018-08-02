<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="css/styleTest.css" rel="stylesheet" type="text/css"/>

<c:if test="${cookie.style != null}">
    <link href="css/${cookie.style.value}.css?date=${dateAct}" rel="stylesheet" type="text/css"/>
</c:if>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
      crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css?family=Chela+One|Gaegu" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Knewave" rel="stylesheet">


