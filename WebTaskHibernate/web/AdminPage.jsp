<%--
  Created by IntelliJ IDEA.
  User: expertiza
  Date: 20.11.2015
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>AdminPage</title>
</head>
<body text=#7cfc00 bgcolor=black>
<form action="<c:url value='/logout'/>" method="get"
      style="text-align: right">
    <input type="submit" value="Logout" style="width: 120Px; height: 22Px">
</form>
<form action="<c:url value='/AddUserPage'/>" method="get">
    <input type="submit" value="Add user"
           style="width: 120Px; height: 22Px">
</form>

<div align="center">
        <table  BORDER=5  ALIGN=CENTER>
            <tr>
                <th>Login</th>
                <th>Password</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Age</th>
                <th>Country</th>
                <th>Street</th>
                <th>Zip Code</th>
                <th>Role</th>
                <th>Music Types</th>
                <th></th>
                <th></th>
            </tr>

            <c:forEach items="${setUsers}" var="user">
                <tr>
                    <td>
                        <c:out value="${user.login}" />
                    </td>
                    <td>
                        <c:out value="${user.password}" />
                    </td>
                    <td>
                        <c:out value="${user.firstName}" />
                    </td>
                    <td>
                        <c:out value="${user.lastName}" />
                    </td>
                    <td>
                        <c:out value="${user.age}" />
                    </td>

                    <td>
                        <c:out value="${user.address.country}" />
                    </td>
                    <td>
                        <c:out value="${user.address.street}" />
                    </td>
                    <td>
                        <c:out value="${user.address.zipCode}" />
                    </td>
                    <td>
                        <c:out value="${user.role.roleName}" />
                    </td>
                    <td>
                        <c:forEach items="${user.musicTypes}" var="type">
                            <c:out value="${type.typeName}" />
                        </c:forEach>
                    </td>
                    <td>
                        <form action="<c:url value='/Update'/>" method="get">
                            <input type="submit" value="Update"/>
                            <input type="hidden" name="id" value="${user.id}"/>
                        </form>
                    <td>
                    <form action="<c:url value='/AdminPage'/>" value=${user.id} method="post">
                                <input type="submit" value="Delete">
                                <input type="hidden" name="id" value="${user.id}" />
                        </form>
                    </td>
                </tr>
            </c:forEach>

        </table>
</div>
</body>
</html>