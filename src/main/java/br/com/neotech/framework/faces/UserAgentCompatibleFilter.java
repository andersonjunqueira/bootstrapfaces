package br.com.neotech.framework.faces;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

@WebFilter(urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "compatibilityMode", value = "IE=edge,chrome=1") })
@Log4j
public class UserAgentCompatibleFilter implements javax.servlet.Filter {

    private String compatibilityMode;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws ServletException, IOException {
        if (compatibilityMode != null) {
            HttpServletResponse res = (HttpServletResponse) resp;
            res.addHeader("X-UA-Compatible", compatibilityMode);
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        compatibilityMode = config.getInitParameter("compatibilityMode");
        if (compatibilityMode == null) {
            log.warn("No CompatibilityMode set for UserAgentCompatibleFilter, thus disabling it");
        }
    }
}