<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.banking.model.Account"%>
<%@ page import="java.util.List"%>
<%
Account account = (Account) session.getAttribute("account");

if (account == null) {
    response.sendRedirect("login.jsp");
    return;
}

response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>DK Bank Dashboard</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/dashboard.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

</head>

<body>

<div class="wrapper">

    <!-- Sidebar -->
    <%@ include file="includes/sidebar.jsp" %>

    <!-- Main Area -->
    
    <div class="main">
    
<%@ include file="includes/topbar.jsp" %>

        <!-- Top Navbar -->
        
        
            <%@ include file="includes/navbar.jsp" %>
        

        <!-- Page Content -->
        <div class="content">


<!-- Welcome Banner -->



<div class="welcome-banner">

    <div class="welcome-text">

        <h1>Welcome Back,</h1>

        <h2><%= account.getAccountHolder() %> 👋</h2>

        <p>Here's your account summary.</p>

    </div>

    <div class="welcome-image">

        <i class="fa-solid fa-building-columns"></i>

    </div>

</div>
            <!-- Account Cards -->

            <div class="cards">

                <div class="card">

                    <i class="fa-solid fa-wallet"></i>

                    <h3>Current Balance</h3>

                    <h2>₹ <%= account.getBalance() %></h2>

                </div>

                <div class="card">

                    <i class="fa-solid fa-user"></i>

                    <h3>Account Holder</h3>

                    <h2><%= account.getAccountHolder() %></h2>

                </div>

                <div class="card">

                    <i class="fa-solid fa-credit-card"></i>

                    <h3>Account Number</h3>

                    <h2><%= account.getAccountNo() %></h2>

                </div>

            </div>
<!-- Recent Transactions -->

<div class="recent-transactions">

    <h2>Recent Transactions</h2>

    <table>

        <thead>

            <tr>

                <th>Date</th>

                <th>Type</th>

                <th>Amount</th>

                <th>Status</th>

            </tr>

        </thead>

<tbody>

<%
List<com.banking.model.Transaction> recentTransactions =
(List<com.banking.model.Transaction>) request.getAttribute("recentTransactions");

if(recentTransactions != null && !recentTransactions.isEmpty()){

    for(com.banking.model.Transaction t : recentTransactions){
%>

<tr>

    <td><%= t.getTransactionDate() %></td>

    <td><%= t.getTransactionType() %></td>

    <td>₹ <%= t.getAmount() %></td>

    <td>
        <span class="success">Success</span>
    </td>

</tr>

<%
    }

}else{
%>

<tr>

    <td colspan="4" style="text-align:center;">
        No Transactions Found
    </td>

</tr>

<%
}
%>

</tbody>

    </table>

</div>
            <!-- Quick Actions -->

            <h2 class="section-title">
                Quick Actions
            </h2>

            <div class="action-grid">

                <a href="deposit.jsp" class="action-card">
                    <i class="fa-solid fa-money-bill-wave"></i>
                    <span>Deposit</span>
                </a>

                <a href="withdraw.jsp" class="action-card">
                    <i class="fa-solid fa-wallet"></i>
                    <span>Withdraw</span>
                </a>

                <a href="transfer.jsp" class="action-card">
                    <i class="fa-solid fa-money-bill-transfer"></i>
                    <span>Transfer</span>
                </a>

                <a href="balance" class="action-card">
                    <i class="fa-solid fa-piggy-bank"></i>
                    <span>Balance</span>
                </a>

                <a href="history" class="action-card">
                    <i class="fa-solid fa-clock-rotate-left"></i>
                    <span>History</span>
                </a>

                <a href="logout" class="action-card logout-card">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    <span>Logout</span>
                </a>

            </div>

        

        <%@ include file="includes/footer.jsp" %>

    </div>

</div>


<script src="js/dashboard.js"></script>

</body>

</html>