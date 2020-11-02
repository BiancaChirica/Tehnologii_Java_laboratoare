package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;


@WebFilter(filterName = "BeforeFilter", urlPatterns = {"/*"})
public class BeforeFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        // Find the IP of the request
        String ipAddress = request.getRemoteAddr();
        // Write request data
        System.out.println("Before filter : IP: " + ipAddress + ", Time: " + new Date().toString());

        // set locale language, default is en
        if (request.getParameter("lang") != null) {
            request.getSession().setAttribute("localeLang", request.getParameter("lang"));
        } else {
            if (request.getSession().getAttribute("localeLang") == null)
                request.getSession().setAttribute("localeLang", "en");
        }

        System.out.println(request.getSession().getAttribute("localeLang") + " before ");
        chain.doFilter(req, res);
    }
}
