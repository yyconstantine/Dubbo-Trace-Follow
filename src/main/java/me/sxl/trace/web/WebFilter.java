package me.sxl.trace.web;

import lombok.extern.slf4j.Slf4j;
import me.sxl.trace.core.ProductContext;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class WebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    /**
     * 由于一般的dubbo-rpc设计都是由http请求作为入口,所以选择在web层进行全局上下文的初始化
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("Record Request-URI: {}", request.getRequestURI());
        // 获取全局上下文信息
        ProductContext productContext = ProductContext.getProductContext();
        try {
            String traceId = request.getHeader("x_traceid");
            if (!StringUtils.isEmpty(traceId)) {
                productContext.setTraceId(traceId);
            }
            String spanId = request.getHeader("x_spanid");
            if (!StringUtils.isEmpty(spanId)) {
                productContext.setParentId(spanId);
            }
            productContext.setSpanId(ProductContext.generateSpanId());
        } catch (Exception e) {
            log.debug("处理http入口请求失败", e);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
