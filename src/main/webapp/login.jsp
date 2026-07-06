<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Banking Transaction Management System</title>

    <!-- CSS -->
    <link rel="stylesheet" href="css/login.css">

    <!-- Font Awesome Icons -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
</head>

<body>

<div class="login-container">

    <div class="login-card">

        <div class="bank-logo">
            <i class="fa-solid fa-building-columns"></i>
        </div>

        <h1>Banking Transaction</h1>

        <p class="subtitle">Welcome Back</p>

        <% if(request.getParameter("error") != null){ %>
            <div class="error">
                <%= request.getParameter("error") %>
            </div>
        <% } %>

        <form action="login" method="post">

            <div class="input-box">

                <i class="fa-solid fa-user"></i>

                <input type="text"
                       name="accountNo"
                       placeholder="Account Number"
                       required>

            </div>

            <div class="input-box">

                <i class="fa-solid fa-lock"></i>

                <input type="password"
                       name="password"
                       placeholder="Password"
                       required>

            </div>

            <button type="submit" class="login-btn">

                <i class="fa-solid fa-right-to-bracket"></i>

                Login

            </button>

        </form>

    </div>

</div>

</body>
</html>