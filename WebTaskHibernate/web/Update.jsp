<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
  <title>UpdatePage</title>
</head>
<body text=#7cfc00 bgcolor=black>
<div align="center">
  <h1>update user</h1>
  <hr align="center" width="200" size="2" color="#7cfc00" />
  <p>
    <c:if test="${not empty errorMessage}">${errorMessage}</c:if>
  </p>
  <p></p>
  <form action="<c:url value='/Update'/>" method="post">
    <input type="hidden" name="id" value="${user.id}"/>
    <table border="0" >
      <tr>
        <td><label> <strong>Login:</strong></label></td>
        <td><input name="login" type="text" value="${user.login}"/> </td>
      </tr>
      <tr>
        <td><label> <strong>Password:</strong></label></td>
        <td><input name="Password" type="text" value="${user.password}"/> </td>
      </tr>
      <tr>
        <td><label> <strong>First name:</strong></label></td>
        <td><input name="First name" type="text" value="${user.firstName}"/> </td>
      </tr>
      <tr>
        <td><label> <strong>Last name:</strong></label></td>
        <td><input name="Last name" type="text"value="${user.lastName}"/> </td>
      </tr>
      <tr>
        <td><label> <strong>Age:</strong></label></td>
        <td><input name="Age" type="text"value="${user.age}"/> </td>
      </tr>
      <tr>
        <td><label> <strong>Country:</strong></label></td>
        <td><input name="Country" type="text"value="${user.address.country}"/> </td>
      </tr>
      <tr>
        <td><label> <strong>Street:</strong></label></td>
        <td><input name="Street" type="text"value="${user.address.street}"/> </td>
      </tr>
      <tr>
        <td><label> <strong>Zip code:</strong></label></td>
        <td><input name="ZipCode" type="text"value="${user.address.zipCode}"/> </td>
      </tr>
      <tr>
        <td><label> <strong>Role</strong></label></td>
        <td><select name="role" size="1" style="width:173Px">
          <c:forEach items="${roles}" var="role">
            <option <c:if test="${role.roleName eq user.role.roleName}">selected="selected"</c:if> >${role.roleName}</option>
          </c:forEach>
        </select> </td>
      </tr>
      <tr>
        <td><label> <strong>Music types</strong></label></td>
        <td><select name="musicTypes" size="4" multiple="multiple" style="width:173Px">
          <c:forEach items="${musicTypes}" var="musicType">
            <option value="${musicType.id}">${musicType.typeName}</option>
          </c:forEach>
        </select> </td>
      </tr>
    </table>
    <p></p>
    <input type="submit" value="Update">
    <input type="reset" value="Clean">
  </form>
</div>
</body>
</html>