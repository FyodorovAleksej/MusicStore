package by.fyodorov.musicstore.customtag;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * tag of page navigation
 */
public class PageListTag extends TagSupport {
    private static final String HIDDEN = "hidden";
    private static final Logger LOGGER = LogManager.getLogger(PageListTag.class);

    @Override
    public int doStartTag() throws JspException {
        String pageString = pageContext.getRequest().getParameter(RequestArgument.SEARCH_PAGE.getName());
        String pageMaxString = ContextParameter.getInstance().getContextParam(InitParameter.PAGE_MAX.toString());
        Integer max = (Integer) pageContext.getSession().getAttribute(RequestArgument.ALL_COUNT.getName());
        max = max == null ? 0 : max;
        RequestParameterValidator validator = new RequestParameterValidator();
        if ((pageString == null || validator.validateInteger(pageString)) && (validator.validateInteger(pageMaxString))) {
            Integer pageMax = Integer.valueOf(pageMaxString);
            Integer page = pageString == null ? 0 : Integer.valueOf(pageString);
            max = (max + (pageMax - 1)) / pageMax;
            LOGGER.debug("page = " + pageString + "; max = " + max);
            if (page <= 0) {
                pageContext.getRequest().setAttribute(RequestArgument.PREVIOUS_PAGE.getName(), 0);
                pageContext.getRequest().setAttribute(RequestArgument.NEXT_PAGE.getName(), page + 1);
                pageContext.getRequest().setAttribute(RequestArgument.PREVIOUS_HIDE.getName(), HIDDEN);
            } else {
                if (page >= max - 1) {
                    pageContext.getRequest().setAttribute(RequestArgument.PREVIOUS_PAGE.getName(), page - 1);
                    pageContext.getRequest().setAttribute(RequestArgument.NEXT_PAGE.getName(), page);
                    pageContext.getRequest().setAttribute(RequestArgument.NEXT_HIDE.getName(), HIDDEN);
                } else {
                    pageContext.getRequest().setAttribute(RequestArgument.PREVIOUS_PAGE.getName(), page - 1);
                    pageContext.getRequest().setAttribute(RequestArgument.NEXT_PAGE.getName(), page + 1);
                }
            }
            pageContext.getRequest().setAttribute(RequestArgument.CURRENT_PAGE.getName(), page.toString());
        }
        try {
            pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/pageListNavigator.jsp");
        } catch (ServletException | IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }
}