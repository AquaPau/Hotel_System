<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <title>Hello World!</title>
  </head>
  <body>
  <c:forEach items="${userList}" var="user">
    <p>ID: ${user.id}</p>
    <p>LOGIN: ${user.login}</p>
    <p>PERMISSION: ${user.permission}</p>
    <p>FIRST NAME: ${user.firstName}</p>
    <p>LAS NAME: ${user.lastName}</p>
    <br>
  </c:forEach>
  </body>
</html>
