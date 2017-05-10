package testproject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kirill on 28.04.17.
 */

@WebFilter(filterName = "AuthFilter",urlPatterns = "*.xhtml")
public class AuthFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            //  allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder
            String reqURI = req.getRequestURI();
            if ( reqURI.indexOf("/authpage.xhtml") >= 0 || reqURI.indexOf("/createUser.xhtml") >= 0 || (ses != null && ses.getAttribute("login") != null) || reqURI.indexOf("/public/") >= 0 || reqURI.contains("javax.faces.resource") ) {

                chain.doFilter(request, response);

            }
            else {   // user didn't log in but asking for a page that is not allowed so take user to login page

                res.sendRedirect(req.getContextPath() + "/authpage.xhtml");  // Anonymous user. Redirect to login page

            }
        }
        catch(Throwable t) {
            System.out.println( t.getMessage());
        }
    }
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
