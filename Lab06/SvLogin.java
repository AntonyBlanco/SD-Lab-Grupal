package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class SvLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isValidUser(username, password)) {
            response.sendRedirect("welcome.jsp?username=" + username);

        } else {
            //request.setAttribute("errorMessage", "Invalid username or password");
            //request.getRequestDispatcher("login.jsp").forward(request, response);
            response.sendRedirect("error.jsp");

        }
    }

    private boolean isValidUser(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
