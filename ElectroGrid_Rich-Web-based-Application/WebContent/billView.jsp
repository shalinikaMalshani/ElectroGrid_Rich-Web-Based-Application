<%@page import="com.model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">

<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/bill.js"></script>
</head>
<body>
<div class="container">
<div class="row"><div class="col-6">
<h1>View Bill</h1>
<form id="formItem" name="formItem">
Bill code:
<input id="billCode" name="billCode" type="text"
class="form-control form-control-sm">
<br>
<input id="btnView" name="btnView" type="button" value="View"
class="btn btn-primary">
</form>
<br>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divBillGrid">
<%
Bill billObj=new Bill();
String billCode = request.getParameter("billCode");
out.print(billObj.getBill(billCode));
%>
</div>
</div> </div> </div>
</body>
</html>