<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.banking.model.Account"%>

<%
Account account=(Account)session.getAttribute("account");

if(account==null){
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Change Password</title>

<link rel="stylesheet" href="css/style.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>

.password-container{

    max-width:600px;
    margin:40px auto;

}

.password-card{

    background:#fff;
    border-radius:15px;
    padding:35px;
    box-shadow:0 5px 20px rgba(0,0,0,.15);

}

.password-card h2{

    text-align:center;
    color:#1565c0;
    margin-bottom:30px;

}

.form-group{

    margin-bottom:20px;

}

.form-group label{

    display:block;
    font-weight:bold;
    margin-bottom:8px;

}

.form-group input{

    width:100%;
    padding:12px;
    border:1px solid #ccc;
    border-radius:8px;
    font-size:16px;
    box-sizing:border-box;

}

.form-group input:focus{

    border-color:#1565c0;
    outline:none;

}

.btn-group{

    text-align:center;
    margin-top:25px;

}

.btn{

    padding:12px 25px;
    border:none;
    border-radius:8px;
    color:#fff;
    text-decoration:none;
    cursor:pointer;
    margin:5px;
    font-size:16px;

}

.change{

    background:#1565c0;

}

.change:hover{

    background:#0d47a1;

}

.back{

    background:#666;

}

.back:hover{

    background:#444;

}

.error{

    color:red;
    text-align:center;
    margin-bottom:15px;

}

.success{

    color:green;
    text-align:center;
    margin-bottom:15px;

}

</style>

</head>

<body>

<%@ include file="includes/sidebar.jsp"%>

<div class="main">

<%@ include file="includes/topbar.jsp"%>

<%@ include file="includes/navbar.jsp"%>

<div class="content">

<div class="password-container">

<div class="password-card">

<h2>
<i class="fa-solid fa-lock"></i>
Change Password
</h2>

<%
String success = request.getParameter("success");
String error = request.getParameter("error");

if(success != null){
%>

<p class="success">
✔ Password changed successfully.
</p>

<%
}

if("current".equals(error)){
%>

<p class="error">
❌ Current password is incorrect.
</p>

<%
}

if("match".equals(error)){
%>

<p class="error">
❌ New password and Confirm password do not match.
</p>

<%
}

if("failed".equals(error)){
%>

<p class="error">
❌ Unable to change password.
</p>

<%
}


if("same".equals(error)){
%>

<p class="error">
❌ New password cannot be the same as the current password.
</p>

<%
}
%>

<form action="changePassword" method="post">

<input type="hidden"
name="accountNo"
value="<%=account.getAccountNo()%>">

<div class="form-group">

<label>Current Password</label>

<input type="password"
name="currentPassword"
required>

</div>

<div class="form-group">

<label>New Password</label>

<input type="password"
name="newPassword"
required>

</div>

<div class="form-group">

<label>Confirm Password</label>

<input type="password"
name="confirmPassword"
required>

</div>

<div class="btn-group">

<button class="btn change">

<i class="fa-solid fa-key"></i>

Change Password

</button>

<a href="profile" class="btn back">

Back

</a>

</div>

</form>

</div>

</div>

</div>

<%@ include file="includes/footer.jsp"%>

</div>

</body>

</html>