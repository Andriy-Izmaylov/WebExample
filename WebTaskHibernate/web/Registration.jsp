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
    <title>Registration</title>
</head>
<body text=#7cfc00 bgcolor=black>
<div align="center">
    <h1>Registration</h1>
    <hr align="center" width="200" size="2" color="#7cfc00" />
    <p>
        <c:if test="${not empty errorMessage}">${errorMessage}</c:if>

    </p>
    <p></p>
    <form action="<c:url value='/Registration'/>" method="post">
        <table border="0" >
            <tr>
                <td><label> <strong>Login:</strong></label></td>
                <td><input name="login" type="text"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Password:</strong></label></td>
                <td><input name="Password" type="password"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Confirm Password:</strong></label></td>
                <td><input name="Confirm Password" type="password"/> </td>
            </tr>
            <tr>
                <td><label> <strong>First name:</strong></label></td>
                <td><input name="First name" type="text"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Last name:</strong></label></td>
                <td><input name="Last name" type="text"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Age:</strong></label></td>
                <td><input name="Age" type="text"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Country:</strong></label></td>
                <td><input name="Country" type="text"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Street:</strong></label></td>
                <td><input name="Street" type="text"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Zip Code:</strong></label></td>
                <td><input name="ZipCode" type="text"/> </td>
            </tr>
            <tr>
                <td><label> <strong>Music types</strong></label></td>
                <td><select name="musicTypes" size="5" multiple="multiple" style="width:173Px">
                    <c:forEach items="${musicTypes}" var="musicType">
                        <option value="${musicType.id}">${musicType.typeName}</option>
                    </c:forEach>
                </select> </td>
            </tr>
        </table>
        <p></p>
        <input type="submit" value="Register">
        <input type="reset" value="Clean">
    </form>
</div>
</body>
</html>