package main.java.org.polytech.achraf.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.org.polytech.achraf.entities.User;

@WebFilter("/login.jsf")
public class LoginFilter implements Filter{
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
	    FilterChain chain) throws ServletException, IOException {

	  HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    HttpSession session = request.getSession();

	    if (session == null || session.getAttribute("user") == null) {
	    	chain.doFilter(req, res); // Logged-in user found, so just continue request.
	    } else {
	    	User user = (User)session.getAttribute("user");
	    	if(user.getRole().getLabel().toLowerCase().equals("administrateur"))
	    		response.sendRedirect(request.getContextPath() + "/admin/admin_sortie.jsf"); // No logged-in user found, so redirect to login page.
	    	else
	    		response.sendRedirect(request.getContextPath() + "/user/user_sortie.jsf"); // No logged-in user found, so redirect to login page.
	    }

	}
}
