package me.sxl.trace.web;

import me.sxl.trace.core.ProductContext;

import javax.servlet.*;
import java.io.IOException;

public class WebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ProductContext productContext = ProductContext.getProductContext();
    }
}
