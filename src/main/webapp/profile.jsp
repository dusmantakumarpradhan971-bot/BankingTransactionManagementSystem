<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.banking.model.Account"%>

<%
Account profile = (Account) request.getAttribute("profile");

if(profile == null){
    response.sendRedirect("dashboard");
    return;
}

String success = request.getParameter("success");
String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>My Profile</title>

<link rel="stylesheet" href="css/style.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>

.profile-container{
    max-width:900px;
    margin:30px auto;
}

.profile-card{
    background:#fff;
    border-radius:15px;
    padding:35px;
    box-shadow:0 8px 25px rgba(0,0,0,.12);
}

.profile-header{
    text-align:center;
    margin-bottom:30px;
}

.profile-avatar{

    width:140px;
    height:140px;
    margin:auto;
    border-radius:50%;
    overflow:hidden;
    border:5px solid #1565c0;
    box-shadow:0 5px 20px rgba(0,0,0,.2);

}

.profile-avatar img{

    width:100%;
    height:100%;
    object-fit:cover;

}

.profile-header h2{
    margin-top:15px;
    color:#1565c0;
}

.profile-header p{
    color:#777;
    margin-top:8px;
}

.profile-grid{
    display:grid;
    grid-template-columns:1fr 1fr;
    gap:20px;
}

.info-box{
    background:#f5f8fc;
    padding:18px;
    border-radius:12px;
}

.info-box h4{
    color:#666;
    margin-bottom:8px;
}

.info-box input,
.info-box textarea{

    width:100%;
    padding:12px;
    border:1px solid #ccc;
    border-radius:8px;
    font-size:15px;
    box-sizing:border-box;
}

.info-box input:focus,
.info-box textarea:focus{

    border-color:#1565c0;
    outline:none;
    box-shadow:0 0 8px rgba(21,101,192,.25);
}

textarea{
    resize:none;
}

.success-message{

    background:#d4edda;
    color:#155724;
    padding:15px;
    border-radius:8px;
    margin-bottom:20px;
    text-align:center;
    font-weight:bold;
}

.error-message{

    background:#f8d7da;
    color:#721c24;
    padding:15px;
    border-radius:8px;
    margin-bottom:20px;
    text-align:center;
    font-weight:bold;
}

.action-buttons{

    text-align:center;
    margin-top:30px;
}

.btn{

    display:inline-block;
    padding:12px 25px;
    margin:8px;
    border:none;
    border-radius:8px;
    color:white;
    text-decoration:none;
    font-size:16px;
    cursor:pointer;
    transition:.3s;
}

.edit{
    background:#1565c0;
}

.edit:hover{
    background:#0d47a1;
}

.back{
    background:#616161;
}

.back:hover{
    background:#424242;
}

.password{

    background:#43a047;

}

.password:hover{

    background:#2e7d32;

}
.status-badge{

    display:inline-block;
    margin-top:12px;
    background:#d4edda;
    color:#155724;
    padding:8px 18px;
    border-radius:30px;
    font-weight:bold;
    font-size:15px;

}

</style>

</head>

<body>

<%@ include file="includes/sidebar.jsp"%>

<div class="main">

<%@ include file="includes/topbar.jsp"%>

<%@ include file="includes/navbar.jsp"%>

<div class="content">

<% if(success!=null){ %>

<div class="success-message">

✔ Profile Updated Successfully

</div>

<% } %>

<% if(error!=null){ %>

<div class="error-message">

✖ Unable to Update Profile

</div>

<% } %>

<div class="profile-container">

<div class="profile-card">

<div class="profile-header">

<div class="profile-avatar">

    <img src="image/myProfile.jpeg"
         alt="Profile">

</div>

<h2><%= profile.getAccountHolder() %></h2>

<div class="status-badge">

    <i class="fa-solid fa-circle-check"></i>

    ACTIVE ACCOUNT

</div>

<p>Customer ID :
<b><%= profile.getAccountNo() %></b></p>

</div>

<form action="editProfile" method="post">

<div class="profile-grid">

<div class="info-box">

<h4>Account Number</h4>

<input type="text"
value="<%= profile.getAccountNo() %>"
readonly>

</div>

<div class="info-box">

<h4>Account Holder</h4>

<input type="text"
value="<%= profile.getAccountHolder() %>"
readonly>

</div>

<div class="info-box">

<h4>Email Address</h4>

<input type="email"
       id="email"
       name="email"
       value="<%= profile.getEmail() %>"
       readonly>

</div>

<div class="info-box">

<h4>Mobile Number</h4>

<input type="text"
       id="mobile"
       name="mobile"
       value="<%= profile.getMobile() %>"
       readonly>

</div>

<div class="info-box">

<h4>Address</h4>

<textarea id="address"
          name="address"
          rows="4"
          readonly><%= profile.getAddress() %></textarea>

</div>
<div class="info-box">

    <h4>Current Balance</h4>

    <input type="text"
           value="₹ <%= profile.getBalance() %>"
           readonly>

</div>

<div class="info-box">

    <h4>Account Status</h4>

    <input type="text"
           value="<%= profile.getStatus() %>"
           readonly>

</div>

</div>

<div class="action-buttons">

<button type="button"
        id="editBtn"
        class="btn edit"
        onclick="enableEdit()">

    <i class="fa-solid fa-user-pen"></i>

    Edit Profile Information

</button>

<button type="submit"
        id="updateBtn"
        class="btn edit"
        style="display:none;">

    <i class="fa-solid fa-floppy-disk"></i>

    Update Profile

</button>

<a href="changePassword.jsp" class="btn password">

    <i class="fa-solid fa-key"></i>

    Change Password

</a>

    <a href="dashboard" class="btn back">

        <i class="fa-solid fa-arrow-left"></i>

        Back to Dashboard

    </a>

</div>

</form>

</div>

</div>

</div>

<%@ include file="includes/footer.jsp"%>

</div>
<script>

function enableEdit(){

    document.getElementById("email").readOnly=false;

    document.getElementById("mobile").readOnly=false;

    document.getElementById("address").readOnly=false;

    document.getElementById("editBtn").style.display="none";

    document.getElementById("updateBtn").style.display="inline-block";

}

</script>
</body>

</html>