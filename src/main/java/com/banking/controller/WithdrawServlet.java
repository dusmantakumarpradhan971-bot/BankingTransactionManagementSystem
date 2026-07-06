package com.banking.controller;

import java.io.IOException;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String accountNo = request.getParameter("accountNo");

            double amount = Double.parseDouble(request.getParameter("amount"));

            // Validation
            if (amount <= 0) {

                response.sendRedirect("withdraw.jsp?error=invalidAmount");
                return;

            }

            AccountDAO dao = new AccountDAO();

            boolean status = dao.withdrawMoney(accountNo, amount);

            if (status) {

                Account account = dao.getAccount(accountNo);

                HttpSession session = request.getSession();

                session.setAttribute("account", account);

                response.sendRedirect("dashboard?success=withdraw");

            } else {

                response.sendRedirect("withdraw.jsp?error=insufficient");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("withdraw.jsp?error=exception");

        }

    }

}