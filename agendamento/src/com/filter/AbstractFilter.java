package com.filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.transport.http.HTTPTransport;

import conexao.com.util.JSFUtil;

public class AbstractFilter {

	public AbstractFilter() {
		super();
	}

	protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		
		HttpServletResponse res = (HttpServletResponse) response;
		ServletContext ctx = (ServletContext) request.getServletContext();
		res.sendRedirect(ctx.getContextPath());
		
	}
	
	protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/faces/pages/public/accessDenied.xhtml");
		rd.forward(request, response);
	}
}