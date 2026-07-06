<%
com.banking.model.Account user =
        (com.banking.model.Account) session.getAttribute("account");
%>

<div class="topbar">

    <div class="left">
        <h2>Dashboard</h2>
    </div>

    <div class="right">

        <div class="notification">
            <i class="fa-solid fa-bell"></i>
        </div>

<div class="theme-toggle" onclick="toggleTheme()">
    <i class="fa-solid fa-moon" id="themeIcon"></i>
</div>
        <div class="profile">

            <div class="profile-icon">
                <i class="fa-solid fa-user"></i>
            </div>

            <div class="profile-info">

                <span class="welcome">Welcome</span>

                <h4><%= user.getAccountHolder() %></h4>

            </div>

        </div>

    </div>

</div>
<script src="<%=request.getContextPath()%>/js/theme.js"></script>