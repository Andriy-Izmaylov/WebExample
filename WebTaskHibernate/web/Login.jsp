<%--
  Created by IntelliJ IDEA.
  User: expertiza
  Date: 15.11.2015
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<html>
<head>
    <title>Login</title>
</head>
<body text=#7cfc00 bgcolor=black>
<div align="center">
    <h1>Welcome</h1>
    <hr align="center" width="200" size="2" color="#7cfc00" />
    <p>
        <c:if test="${not empty errorMessage}">${errorMessage}</c:if>
    </p>
    <p></p>
    <form action="<c:url value='/Login'/>" method="post">
        <table border="0" >
            <tr>
                <td><label> <strong>Login:</strong></label></td>
                <td><input name="login" type="text"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Password:</strong></label></td>
                <td><input name="password" type="password"/> </td>
            </tr>
        </table>
        <p></p>
        <input type="submit" value="Login"style="width:120Px;height:22Px">
    </form>
    <form action="<c:url value='/Registration'/>" method="get">
        <input type="submit" value="Register" style="width:120Px;height:22Px">
    </form>
</div>
</body>
</html>