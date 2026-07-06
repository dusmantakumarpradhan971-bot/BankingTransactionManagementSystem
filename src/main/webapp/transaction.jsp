<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
List<Map<String, Object>> list =
    (List<Map<String, Object>>) request.getAttribute("transactions");
%>

<html>
<head>
    <title>Transaction History</title>

    <style>
        body { font-family: Arial; background:#f2f4f8; }

        .container {
            width: 800px;
            margin: 40px auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
        }

        h2 { text-align:center; }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        th {
            background: #0d6efd;
            color: white;
        }

        .back {
            display:block;
            margin-top:20px;
            text-align:center;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Transaction History</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Account</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Balance</th>
            <th>Date</th>
        </tr>

        <%
        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> row : list) {
        %>

        <tr>
            <td><%= row.get("id") %></td>
            <td><%= row.get("account") %></td>
            <td><%= row.get("type") %></td>
            <td><%= row.get("amount") %></td>
            <td><%= row.get("balance") %></td>
            <td><%= row.get("date") %></td>
        </tr>

        <%
            }
        } else {
        %>

        <tr>
            <td colspan="6">No Transactions Found</td>
        </tr>

        <% } %>

    </table>

    <a class="back" href="dashboard.jsp">⬅ Back to Dashboard</a>

</div>

</body>
</html>