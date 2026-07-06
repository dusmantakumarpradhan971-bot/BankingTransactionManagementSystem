<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<%
String currentDate = LocalDate.now().format(
        DateTimeFormatter.ofPattern("dd MMM yyyy"));
%>

<div class="top-header">

    <div class="marquee">

        <marquee behavior="scroll" direction="left" scrollamount="6">

            DK Banking Transaction Management System |
            Developed by <b>Dusmanta Kumar Pradhan</b>

        </marquee>

    </div>

    <div class="datetime">

        <span>
            <i class="fa-solid fa-calendar-days"></i>
            <%= currentDate %>
        </span>

        <span id="clock">
            <i class="fa-solid fa-clock"></i>
        </span>

    </div>

</div>

<script>

function updateClock(){

    let now = new Date();

    let time = now.toLocaleTimeString('en-IN',{
        hour:'2-digit',
        minute:'2-digit',
        second:'2-digit'
    });

    document.getElementById("clock").innerHTML =
        '<i class="fa-solid fa-clock"></i> ' + time;
}

setInterval(updateClock,1000);

updateClock();

</script>