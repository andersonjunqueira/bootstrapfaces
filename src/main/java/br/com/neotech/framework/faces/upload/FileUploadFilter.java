package br.com.neotech.framework.faces.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebFilter(
    filterName = "FileUploadFilter",
    urlPatterns = { "/*" },
    initParams = {
        @WebInitParam(name="thresholdSize", value="10485760")
    }
)
public class FileUploadFilter implements Filter {

    private final static String THRESHOLD_SIZE_PARAM = "thresholdSize";
    private final static String UPLOAD_DIRECTORY_PARAM = "uploadDirectory";

    private String thresholdSize;
    private String uploadDir;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        thresholdSize = filterConfig.getInitParameter(THRESHOLD_SIZE_PARAM);
        uploadDir = filterConfig.getInitParameter(UPLOAD_DIRECTORY_PARAM);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);

        if(isMultipart) {

            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            if(thresholdSize != null) {
                diskFileItemFactory.setSizeThreshold(Integer.valueOf(thresholdSize));
            }

            if(uploadDir != null) {
                diskFileItemFactory.setRepository(new File(uploadDir));
            }

            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            MultipartRequest multipartRequest = new MultipartRequest(httpServletRequest, servletFileUpload);

            filterChain.doFilter(multipartRequest, response);

        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}