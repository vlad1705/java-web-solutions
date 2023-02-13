package com.bobocode.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var session = req.getSession();
        var sessionName = Optional.ofNullable((String)session.getAttribute("name"));
        var requestName = Optional.ofNullable(req.getParameter("name"));
        requestName.filter(a->sessionName.isEmpty()).
                ifPresent(name-> session.setAttribute("name", name));

        String name = requestName.or(()->sessionName).orElse("friend:)");

        printTimeAndName(resp,name);

        resp.addCookie(new Cookie("VladCookie", UUID.randomUUID().toString()));
    }

    private void printTimeAndName(HttpServletResponse resp, String name) throws IOException {
        PrintWriter out = resp.getWriter();
        DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
        out.println("Hallo " + name + ", it's :" + LocalDateTime.now().format(TIME_FORMATTER) + " o'clock :)");
    }
}
