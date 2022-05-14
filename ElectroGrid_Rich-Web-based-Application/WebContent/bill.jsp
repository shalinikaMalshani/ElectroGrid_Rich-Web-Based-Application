<%@page import="com.model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<link rel="stylesheet" href="Views/bill.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/bill.js"></script>
</head>
<body>
<div class="container">
<div class="row"><div class="col-6">
<h1>Bill Management</h1>
<form id="formItem" name="formItem">
Bill code:
<input id="billCode" name="billCode" type="text"
class="form-control form-control-sm">
<br>Customer ID:
<input id="customerID" name="customerID" type="text"
class="form-control form-control-sm">
<br>Month:
<input id="month" name="month" type="text"
class="form-control form-control-sm">

<br>Units:
<div id="calc">
<input id="units" name="units" type="text"
class="form-control form-control-sm">&nbsp;<input id="CalcKwh" name="CalcKwh" type="button" value="Calculate KWHCharge"
class="btn btn-danger"></div>
<br>KWH Charge:
<input id="KWHCharge" name="KWHCharge" type="text"
class="form-control form-control-sm">
<br>Fixed Charge:
<input id="fixedCharge" name="fixedCharge" type="text"
class="form-control form-control-sm">
<br>Rebate:
<div id="calc">
<input id="rebate" name="rebate" type="text"
class="form-control form-control-sm">&nbsp;
<input id="CalcTot" name="CalcTot" type="button" value="Calculate Total"
class="btn btn-danger"></div>
<br>Total:
<input id="total" name="total" type="text"
class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save"
class="btn btn-primary">
<input type="hidden" id="hidItemIDSave"
name="hidItemIDSave" value="">
</form>
<br>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divItemsGrid">
<%
Bill billObj=new Bill();
out.print(billObj.readBill());
%>
</div>
</div> </div> </div>
</body>
</html>