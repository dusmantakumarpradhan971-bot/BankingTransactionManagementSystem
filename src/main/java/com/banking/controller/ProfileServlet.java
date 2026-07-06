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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        try {

            Account account =
                    (Account) session.getAttribute("account");

            AccountDAO dao = new AccountDAO();

            Account latest =
                    dao.getAccount(account.getAccountNo());

            request.setAttribute("profile", latest);

            request.getRequestDispatcher("profile.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("dashboard");

        }

    }

}