package by.fyodorov.musicstore.customtag;

import by.fyodorov.musicstore.model.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * tag for view information of current user
 */
public class UserInfoViewTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(UserInfoViewTag.class);

    @Override
    public int doStartTag() throws JspException {
        LOGGER.debug("DO START TAG");
        String userName = (String) pageContext.getSession().getAttribute("userName");
        String userRole = (String) pageContext.getSession().getAttribute("userRole");

        if (userName == null || userRole == null) {
            try {
                LOGGER.debug("include = \"" + pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/notLoginHeader.jsp" + "\"");
                pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/notLoginHeader.jsp");
            } catch (ServletException | IOException e) {
                throw new JspException(e);
            }
        } else {
            pageContext.getRequest().setAttribute("userName", userName);
            pageContext.getRequest().setAttribute("userRole", userRole);
            try {
                if (UserRole.ADMIN.toString().equals(userRole)) {
                    LOGGER.debug("include \"" + pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/adminLoginHeader.jsp" + "\"");
                    pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/adminLoginHeader.jsp");
                } else {
                    LOGGER.debug("include \"" + pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/loginHeader.jsp" + "\"");
                    pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/loginHeader.jsp");
                }
            } catch (ServletException | IOException e) {
                throw new JspException(e);
            }
        }
        return EVAL_PAGE;
    }
}
