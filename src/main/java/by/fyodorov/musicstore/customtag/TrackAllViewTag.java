package by.fyodorov.musicstore.customtag;

import by.fyodorov.musicstore.model.TrackEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.LinkedList;

public class TrackAllViewTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(TrackAllViewTag.class);
    private static final String INFO_BUTTON = "Info";
    private static final String FORWARD_COMMAND = "/trackInfo?";
    private static final String TRACK_NAME_ARGUMENT = "trackInfoName=";

    @Override
    public int doStartTag() throws JspException {
        LOGGER.debug("DO VIEW TRACK TAG");
        LinkedList<TrackEntity> list = (LinkedList<TrackEntity>) pageContext.getRequest().getAttribute("trackList");
        if (list == null || list.isEmpty()) {
            pageContext.getRequest().setAttribute("trackAll", "nothing to show");
        } else {
            StringBuilder builder = new StringBuilder();

            for (TrackEntity track : list) {
                builder.append("<tr><td>");
                builder.append("<button type=\"button\" style = \"margin-left: 10px; margin-bottom: 4px; margin-right: 20px;\" class=\"btn btn-primary\" onClick='location.href=\"");
                builder.append(FORWARD_COMMAND).append(TRACK_NAME_ARGUMENT);
                builder.append(track.getName());
                builder.append("\"'>");
                builder.append(INFO_BUTTON);
                builder.append("</button></td><td><h5 style=\"margin-left: 10px; margin-bottom: 4px; margin-right: 20px;\">");
                builder.append(track.getName());
                builder.append("</h5></td><td><h5 style=\"margin-left: 10px; margin-bottom: 4px; margin-right: 20px;\">");
                builder.append(track.getGenre());
                builder.append("</h5></td><td><h5 style=\"margin-left: 10px; margin-bottom: 4px; margin-right: 20px;\">");
                builder.append(track.getPrice());
                builder.append("</h5></td><td><h5 style=\"margin-left: 10px; margin-bottom: 4px; margin-right: 20px;\">");
                builder.append(track.getDate());
                builder.append("</h5></td></tr>");
            }
            pageContext.getRequest().setAttribute("tracksInfo", builder.toString());
        }
        try {
            LOGGER.debug("include = \"" + pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/trackView.jsp" + "\"");
            pageContext.include(pageContext.getServletContext().getContextPath() + "/WEB-INF/jspf/trackView.jsp");
        } catch (ServletException | IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }
}
