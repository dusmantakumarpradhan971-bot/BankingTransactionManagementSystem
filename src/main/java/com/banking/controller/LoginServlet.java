package com.banking.controller;

import java.io.IOException;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String accountNo = request.getParameter("accountNo");
            String password = request.getParameter("password");

            AccountDAO dao = new AccountDAO();
            Account account = dao.login(accountNo, password);

            if (account != null) {

                HttpSession session = request.getSession(true);
                session.setAttribute("account", account);   // ✅ ONLY THIS
                session.setMaxInactiveInterval(30 * 60);

                response.sendRedirect("dashboard");

            } else {
                response.sendRedirect("login.jsp?error=Invalid Credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Server Error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("login.jsp");
    }
}