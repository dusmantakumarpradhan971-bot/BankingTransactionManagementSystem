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

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String fromAccount =
                    request.getParameter("fromAccount").trim();

            String toAccount =
                    request.getParameter("toAccount").trim();

            double amount =
                    Double.parseDouble(request.getParameter("amount"));

            // Amount Validation
            if (amount <= 0) {

                response.sendRedirect("transfer.jsp?error=invalidAmount");
                return;

            }

            // Same Account Validation
            if (fromAccount.equals(toAccount)) {

                response.sendRedirect("transfer.jsp?error=sameAccount");
                return;

            }

            AccountDAO dao = new AccountDAO();

            // Receiver Validation
            if (!dao.isValidAccount(toAccount)) {

                response.sendRedirect("transfer.jsp?error=invalidReceiver");
                return;

            }

            // Transfer
            boolean status =
                    dao.transferMoney(fromAccount,
                                      toAccount,
                                      amount);

            if (status) {

                // Refresh Session
                Account account =
                        dao.getAccount(fromAccount);

                HttpSession session =
                        request.getSession();

                session.setAttribute("account", account);

                response.sendRedirect("dashboard?success=transfer");

            } else {

                response.sendRedirect("transfer.jsp?error=insufficientBalance");

            }

        } catch (NumberFormatException e) {

            response.sendRedirect("transfer.jsp?error=invalidAmount");

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("transfer.jsp?error=exception");

        }

    }

}