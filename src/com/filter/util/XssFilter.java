package com.filter.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
 
/**
 * Xss����������
 *
 * @author storezhang
 */
public class XssFilter implements Filter {
 
    
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Xss filter inited!");
    }
 
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       System.out.println("����������");
    	XssHttpWrapper xssRequest = new XssHttpWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }
 
    
    public void destroy() {
        System.out.println("Xss filter destroyed!");
    }
}