package com.banking.controller;

import java.io.IOException;
import java.util.List;

import com.banking.dao.TransactionDAO;
import com.banking.model.Account;
import com.banking.model.Transaction;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");

        TransactionDAO dao = new TransactionDAO();

        List<Transaction> recentTransactions =
                dao.getRecentTransactions(account.getAccountNo());

        request.setAttribute("recentTransactions",
                recentTransactions);

        request.getRequestDispatcher("dashboard.jsp")
                .forward(request, response);
    }

}