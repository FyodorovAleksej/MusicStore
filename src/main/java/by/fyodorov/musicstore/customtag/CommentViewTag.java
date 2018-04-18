package by.fyodorov.musicstore.customtag;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.view.CommentView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.LinkedList;

public class CommentViewTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(CommentViewTag.class);

    @Override
    public int doStartTag() throws JspException {
        LOGGER.debug("DO VIEW TRACK TAG");
        LinkedList<CommentView> list = (LinkedList<CommentView>) pageContext.getRequest().getAttribute(RequestArgument.TRACK_INFO_COMMENT_LIST.getName());
        if (list == null || list.isEmpty()) {
            pageContext.getRequest().setAttribute("commentAll", "");
        } else {
            StringBuilder builder = new StringBuilder();
            for (CommentView comment : list) {
                builder.append("<tr><td>");
                builder.append("<h5 style=\"margin-left: 10px; margin-bottom: 4px; margin-right: 20px;\">");
                builder.append(comment.getUserName());
                builder.append("</h5></td><td><h5 style=\"margin-left: 10px; margin-bottom: 4px; margin-right: 20px;\">");
                builder.append(comment.getText());
                builder.append("</h5></td><td><h5 style=\"margin-left: 10px; margin-bottom: 4px; margin-right: 20px;\">");
                builder.append(comment.getDate());
                builder.append("</h5></td></tr>");
            }
            pageContext.getRequest().setAttribute("comments", builder.toString());
        }
        try {
            LOGGER.debug("include = \"" + pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/trackComment.jsp" + "\"");
            pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/trackComment.jsp");
        } catch (ServletException | IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }
    /*
    <form>
    <input type="text" class="text text-warning" name="newComment">
    <button type="submit">${commentButton}</button>
    </form>
     */
}
