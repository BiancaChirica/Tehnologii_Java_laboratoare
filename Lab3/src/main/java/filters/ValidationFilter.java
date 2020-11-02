package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ValidationFilter", urlPatterns = {"/FlowController"})
public class ValidationFilter implements Filter {

    private String defaultLanguage;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.defaultLanguage = filterConfig.getInitParameter("defaultLanguage");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // log to test the application's flow
        System.out.println("Validation filter");

        String word = request.getParameter("word");
        String definition = request.getParameter("definition");
        String captchaInput = request.getParameter("captchaInput");

        // validate params
        if (word == null || word.trim().length() == 0 || definition == null || definition.trim().length() == 0
                || captchaInput == null || captchaInput.trim().length() == 0) {
            response.sendRedirect("input.jsp");
            return;
        }

        // wrap request for language attribute
        ServletRequestWrapper wrapper = new HttpServletRequestWrapper(request) {
            public String getParameter(String paramName) {
                if (paramName.equals("language") &&
                        (request.getParameter("language") == null || request.getParameter("language").equals("null")))
                    return  defaultLanguage;

                return request.getParameter(paramName);
            }

        };

        chain.doFilter(wrapper, res);
    }
}
