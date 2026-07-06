package com.banking.controller;

import java.io.IOException;

import com.banking.dao.AccountDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String accountNo = request.getParameter("accountNo");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        AccountDAO dao = new AccountDAO();

        try {

            // Check Current Password
            if (!dao.checkCurrentPassword(accountNo, currentPassword)) {

                response.sendRedirect("changePassword.jsp?error=current");
                return;
            }
         // New password should not be same as current password
            if (currentPassword.equals(newPassword)) {

                response.sendRedirect(request.getContextPath() + "/changePassword.jsp?error=same");
                return;
            }
            // Check New Password Match
            if (!newPassword.equals(confirmPassword)) {

                response.sendRedirect("changePassword.jsp?error=match");
                return;
            }

            // Update Password
            boolean status = dao.changePassword(accountNo, newPassword);

            if (status) {

                response.sendRedirect("changePassword.jsp?success=true");

            } else {

                response.sendRedirect("changePassword.jsp?error=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("changePassword.jsp?error=exception");
        }
    }
}