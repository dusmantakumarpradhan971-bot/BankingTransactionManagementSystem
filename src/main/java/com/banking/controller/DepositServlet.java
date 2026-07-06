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

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String accountNo = request.getParameter("accountNo");

            double amount = Double.parseDouble(
                    request.getParameter("amount"));

            // Validate Deposit Amount
            if (amount <= 0) {

                response.sendRedirect("deposit.jsp?error=invalidAmount");
                return;

            }

            AccountDAO dao = new AccountDAO();

            boolean status = dao.depositMoney(accountNo, amount);

            if (status) {

                // Fetch latest account details
                Account account = dao.getAccount(accountNo);

                // Update session
                HttpSession session = request.getSession();

                session.setAttribute("account", account);

                response.sendRedirect("dashboard?success=deposit");

            } else {

                response.sendRedirect("deposit.jsp?error=failed");

            }

        } catch (NumberFormatException e) {

            e.printStackTrace();

            response.sendRedirect("deposit.jsp?error=number");

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("deposit.jsp?error=exception");

        }

    }

}