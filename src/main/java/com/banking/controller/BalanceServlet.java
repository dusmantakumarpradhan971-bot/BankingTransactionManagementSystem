package com.banking.controller;

import java.io.IOException;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/balance")
public class BalanceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession session = request.getSession(false);

            if(session==null){

                response.sendRedirect("login.jsp");
                return;

            }

            Account account =
                    (Account)session.getAttribute("account");

            if(account==null){

                response.sendRedirect("login.jsp");
                return;

            }

            AccountDAO dao = new AccountDAO();

            Account latest =
                    dao.getAccount(account.getAccountNo());

            session.setAttribute("account", latest);

            request.getRequestDispatcher("balance.jsp")
                    .forward(request,response);

        } catch(Exception e){

            e.printStackTrace();

        }

    }

}