<%-- 
    Document   : listarEmployee
    Created on : 30/10/2018, 14:26:03
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee List</title>
    </head>
    <body>
        <h1>Employee List</h1>
        <br/><br/>
        <table border="1">
            <tr>
                <th>Nombre</th>
                <th>Apellidos</th>
                
            </tr>
            <c:forEach var="employee" items="${employees}">
                <tr>
                    <td>${employee.firstName}</td>
                    <td>${employee.lastName}</td>
                    
                </tr>
            </c:forEach>
        </table>
        <br/>
    </body>
</html>
