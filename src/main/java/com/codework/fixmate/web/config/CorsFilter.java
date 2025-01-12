package com.codework.fixmate.web.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {



    public CorsFilter() {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

       try {
           HttpServletResponse response = (HttpServletResponse) res;
           HttpServletRequest request = (HttpServletRequest) req;
           Map<String, String> map = new HashMap<>();
           String originHeader=request.getHeader("origin");
           response.setHeader("Access-Control-Allow-Origin", originHeader != null ? originHeader : "*");
           response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
           response.setHeader("Access-Control-Max-Age", "3600");
           response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

           if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
               response.setStatus(HttpServletResponse.SC_OK);
           } else {
               chain.doFilter(req, res);
           }
       }catch (Error e){
           e.printStackTrace(); // Log the error
           throw new ServletException("Error processing CORS configuration", e);       }
    }
}
