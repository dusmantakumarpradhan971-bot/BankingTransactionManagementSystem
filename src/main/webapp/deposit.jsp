<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.banking.model.Account"%>

<%
Account account = (Account) session.getAttribute("account");

if(account == null){
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Deposit Money</title>

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/dashboard.css">
<link rel="stylesheet" href="css/deposit.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

</head>

<body>

<%@ include file="includes/sidebar.jsp" %>

<div class="main">

    <div class="topbar">
        <%@ include file="includes/navbar.jsp" %>
    </div>

    <div class="content">

        <div class="page-title">

            <h1>
                <i class="fa-solid fa-money-bill-wave"></i>
                Deposit Money
            </h1>

            <p>Add money safely into your account.</p>

        </div>

        <% if(request.getParameter("error")!=null){ %>

        <div class="alert error">

            <i class="fa-solid fa-circle-xmark"></i>

            Invalid Amount!

        </div>

        <% } %>

        <div class="deposit-card">

            <div class="card-header">

                <i class="fa-solid fa-building-columns"></i>

                <h2>Deposit Form</h2>

            </div>

            <form action="deposit" method="post">

                <div class="input-box">

                    <label>Account Number</label>

                    <input type="text"
                           name="accountNo"
                           value="<%=account.getAccountNo()%>"
                           readonly>

                </div>

                <div class="input-box">

                    <label>Account Holder</label>

                    <input type="text"
                           value="<%=account.getAccountHolder()%>"
                           readonly>

                </div>

                <div class="input-box">

                    <label>Current Balance</label>

                    <input type="text"
                           value="₹ <%=String.format("%,.2f",account.getBalance())%>"
                           readonly>

                </div>

                <div class="input-box">

                    <label>Deposit Amount</label>

                    <input type="number"
                           name="amount"
                           min="1"
                           step="0.01"
                           placeholder="Enter Deposit Amount"
                           required>

                </div>

                <button type="submit">

                    <i class="fa-solid fa-circle-plus"></i>

                    Deposit Money

                </button>

            </form>

        </div>

    </div>

    <%@ include file="includes/footer.jsp" %>

</div>

</body>

</html>