<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="com.banking.model.Account"%>
<%@ page import="com.banking.model.Transaction"%>

<%
Account account = (Account) session.getAttribute("account");

if(account==null){
    response.sendRedirect("login.jsp");
    return;
}

List<Transaction> history =
(List<Transaction>)request.getAttribute("history");
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Transaction History</title>

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/dashboard.css">
<link rel="stylesheet" href="css/history.css">

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

        <div class="page-header">

            <div>

                <h1>
                    <i class="fa-solid fa-clock-rotate-left"></i>
                    Transaction History
                </h1>

                <p>View all your banking transactions.</p>

            </div>

        </div>

        <div class="history-card">

            <table class="history-table">

                <thead>

                    <tr>

                        <th>ID</th>

                        <th>Transaction</th>

                        <th>Amount</th>

                        <th>Balance</th>

                        <th>Date & Time</th>

                    </tr>

                </thead>

                <tbody>

                <%
                if(history!=null && !history.isEmpty()){

                    for(Transaction t : history){
                %>

                    <tr>

                        <td>#<%=t.getTransactionId()%></td>

                        <td>

                        <%
                        String type=t.getTransactionType();

                        if(type.contains("DEPOSIT")){
                        %>

                        <span class="badge deposit">
                            Deposit
                        </span>

                        <%

                        }else if(type.contains("WITHDRAW")){

                        %>

                        <span class="badge withdraw">
                            Withdraw
                        </span>

                        <%

                        }else if(type.contains("CREDIT")){

                        %>

                        <span class="badge credit">
                            Transfer Credit
                        </span>

                        <%

                        }else{

                        %>

                        <span class="badge debit">
                            Transfer Debit
                        </span>

                        <%

                        }
                        %>

                        </td>

                        <td>
                            ₹ <%=String.format("%,.2f",t.getAmount())%>
                        </td>

                        <td>
                            ₹ <%=String.format("%,.2f",t.getBalanceAfter())%>
                        </td>

                        <td>
                            <%=t.getTransactionDate()%>
                        </td>

                    </tr>

                <%
                    }
                }else{
                %>

                    <tr>

                        <td colspan="5" class="empty">

                            <i class="fa-solid fa-folder-open"></i>

                            <br><br>

                            No Transactions Found

                        </td>

                    </tr>

                <%
                }
                %>

                </tbody>

            </table>

        </div>

    </div>

    <%@ include file="includes/footer.jsp" %>

</div>

</body>

</html>