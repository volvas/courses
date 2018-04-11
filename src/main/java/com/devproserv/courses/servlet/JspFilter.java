package com.devproserv.courses.servlet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.devproserv.courses.config.MainConfig.HOME_PAGE;
import static com.devproserv.courses.config.MainConfig.LECTURER_PAGE;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;

/**
 * Filter forbids direct access to internal JSP. In case of direct typing
 * address in the browser the filter redirects to home page.
 * 
 * @author vovas11
 *
 */
@WebFilter(urlPatterns = {STUDENT_PAGE, LECTURER_PAGE},
           dispatcherTypes = {DispatcherType.REQUEST})
public class JspFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // redirect to home page
        httpResponse.sendRedirect(httpRequest.getContextPath() + HOME_PAGE);
    }

    @Override
    public void destroy() {}

}
