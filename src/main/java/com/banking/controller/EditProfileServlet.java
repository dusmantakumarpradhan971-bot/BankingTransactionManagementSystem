package com.banking.controller;

import java.io.IOException;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("account") == null) {

                response.sendRedirect("login.jsp");
                return;
            }

            Account account =
                    (Account) session.getAttribute("account");

            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            String address = request.getParameter("address");

            AccountDAO dao = new AccountDAO();

            boolean status = dao.updateProfile(
                    account.getAccountNo(),
                    email,
                    mobile,
                    address);

            if (status) {

                Account latest =
                        dao.getAccount(account.getAccountNo());

                session.setAttribute("account", latest);

                response.sendRedirect("profile?success=updated");

            } else {

                response.sendRedirect("profile?error=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("profile?error=exception");

        }

    }

}