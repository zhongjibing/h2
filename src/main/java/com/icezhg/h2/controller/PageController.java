package com.icezhg.h2.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/")
    public void uploadPage(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.println("<form enctype=\"multipart/form-data\" method=\"post\" action=\"http://localhost:8080/archive/upload\">\n");
        pw.println("upload: <input id=\"file\" name=\"file\" type=\"file\"/>\n");
        pw.println("<input type=\"submit\" value=\"submit\"/>\n");
        pw.println("</form>\n");
        pw.flush();
        pw.close();
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }
}
