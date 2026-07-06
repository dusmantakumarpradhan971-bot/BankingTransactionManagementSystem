<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.banking.model.Account"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<%
Account account=(Account)session.getAttribute("account");

if(account==null){
response.sendRedirect("login.jsp");
return;
}

String date=
LocalDateTime.now().format(
DateTimeFormatter.ofPattern("dd MMM yyyy  hh:mm a"));
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Balance Inquiry</title>

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/dashboard.css">
<link rel="stylesheet" href="css/balance.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

</head>

<body>

<%@ include file="includes/sidebar.jsp"%>

<div class="main">

<div class="topbar">

<%@ include file="includes/navbar.jsp"%>

</div>

<div class="content">

<div class="page-title">

<h1>

<i class="fa-solid fa-piggy-bank"></i>

Balance Inquiry

</h1>

<p>Your latest account information.</p>

</div>

<div class="balance-card">

<div class="balance-icon">

<i class="fa-solid fa-wallet"></i>

</div>

<h4>Available Balance</h4>

<h1>

₹ <%=String.format("%,.2f",account.getBalance())%>

</h1>

</div>

<div class="info-grid">

<div class="info-box">

<i class="fa-solid fa-user"></i>

<h3>Account Holder</h3>

<p><%=account.getAccountHolder()%></p>

</div>

<div class="info-box">

<i class="fa-solid fa-credit-card"></i>

<h3>Account Number</h3>

<p><%=account.getAccountNo()%></p>

</div>

<div class="info-box">

<i class="fa-solid fa-circle-check"></i>

<h3>Status</h3>

<p><%=account.getStatus()%></p>

</div>

<div class="info-box">

<i class="fa-solid fa-calendar"></i>

<h3>Date & Time</h3>

<p><%=date%></p>

</div>

</div>

</div>

<%@ include file="includes/footer.jsp"%>

</div>

</body>

</html>