<%--
  Created by IntelliJ IDEA.
  User: expertiza
  Date: 15.11.2015
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Welcome</title>
</head>
<body text=#7cfc00 bgcolor=black>
<form action="<c:url value='/logout'/>" method="get"
      style="text-align: right">
    <input type="submit" value="Logout" style="width: 120Px; height: 22Px">
</form>
<div align="center">
    <table >
        <tr>
            <td style="height:400px; vertical-align:middle; width:300px; text-align:center;">
                <h1>Welcome <%= session.getAttribute( "name" ) %> <%= session.getAttribute( "lastName" ) %> !</h1>
                <hr align="center" width="200" size="2" color="#7cfc00" />
            </td>
        </tr>
    </table>

</div>
</body>
</html>