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
    /**
     * handle start tag
     * @return - EVAL_PAGE - end of tag
     * @throws JspException - when can't forward or redirect
     */
    @Override
    public int doStartTag() throws JspException {
        String userName = (String) pageContext.getSession().getAttribute("userName");
        String userRole = (String) pageContext.getSession().getAttribute("userRole");

        if (userName == null || userRole == null) {
            try {
                pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/notLoginHeader.jsp");
            } catch (ServletException | IOException e) {
                throw new JspException(e);
            }
        } else {
            pageContext.getRequest().setAttribute("userName", userName);
            pageContext.getRequest().setAttribute("userRole", userRole);
            try {
                if (UserRole.ADMIN.toString().equals(userRole)) {
                    pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/adminLoginHeader.jsp");
                } else {
                    pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/loginHeader.jsp");
                }
            } catch (ServletException | IOException e) {
                throw new JspException(e);
            }
        }
        return EVAL_PAGE;
    }
}
