package com.banking.controller;

import java.io.IOException;
import java.util.List;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;
import com.banking.model.Transaction;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("========== HISTORY SERVLET ==========");

        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("Session is NULL");
            response.sendRedirect("login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            System.out.println("Account is NULL");
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("Logged Account = " + account.getAccountNo());

        try {

            AccountDAO dao = new AccountDAO();

            List<Transaction> history =
                    dao.getTransactionHistory(account.getAccountNo());

            System.out.println("History Size = " + history.size());

            request.setAttribute("history", history);

            request.getRequestDispatcher("history.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}