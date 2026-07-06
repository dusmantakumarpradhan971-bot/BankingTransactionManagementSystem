<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.banking.model.Account"%>

<%
Account account = (Account)session.getAttribute("account");

if(account==null){
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Transfer Money</title>

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/dashboard.css">
<link rel="stylesheet" href="css/transfer.css">

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
<i class="fa-solid fa-money-bill-transfer"></i>
Transfer Money
</h1>

<p>Transfer money safely between DK Bank accounts.</p>

</div>

<%
String error=request.getParameter("error");
String success=request.getParameter("success");
%>

<% if(success!=null){ %>

<div class="alert success">

<i class="fa-solid fa-circle-check"></i>

Money Transferred Successfully

</div>

<% } %>

<% if(error!=null){ %>

<div class="alert error">

<i class="fa-solid fa-circle-xmark"></i>

Transfer Failed!

</div>

<% } %>

<form action="transfer" method="post">

<div class="transfer-container">

<div class="account-card">

<i class="fa-solid fa-building-columns big-icon"></i>

<h2>From Account</h2>

<label>Account Number</label>

<input
type="text"
name="fromAccount"
value="<%=account.getAccountNo()%>"
readonly>

<label>Account Holder</label>

<input
type="text"
value="<%=account.getAccountHolder()%>"
readonly>

<label>Available Balance</label>

<input
type="text"
value="₹ <%=String.format("%,.2f",account.getBalance())%>"
readonly>

</div>

<div class="arrow">

<i class="fa-solid fa-arrow-right"></i>

</div>

<div class="account-card">

<i class="fa-solid fa-user-plus big-icon"></i>

<h2>To Account</h2>

<label>Receiver Account Number</label>

<input
type="text"
name="toAccount"
placeholder="Enter Receiver Account"
required>

<label>Amount</label>

<input
type="number"
name="amount"
placeholder="Enter Amount"
min="1"
step="0.01"
required>

<button>

<i class="fa-solid fa-paper-plane"></i>

Transfer Now

</button>

</div>

</div>

</form>

</div>

<%@ include file="includes/footer.jsp"%>

</div>

</body>

</html>