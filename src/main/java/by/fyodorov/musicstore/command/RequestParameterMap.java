package by.fyodorov.musicstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class RequestParameterMap {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;
    private HashMap<String, String> requestParameters;
    private HashMap<String, String[]> requestMultipleParametres;
    private String contextPath;

    public RequestParameterMap(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        requestMultipleParametres = new HashMap<>();

        contextPath = request.getContextPath();

        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }

        Enumeration<String> sessionNames = request.getSession().getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            String name = sessionNames.nextElement();
            sessionAttributes.put(name, request.getSession().getAttribute(name));
        }

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String[] params = request.getParameterValues(name);
            if (params.length > 1) {
                requestMultipleParametres.put(name, request.getParameterValues(name));
            } else {
                requestParameters.put(name, request.getParameter(name));
            }
        }
    }

    public String getParameter(String name) {
        return requestParameters.get(name);
    }

    public Object getRequestAttribute(String name) {
        return requestAttributes.get(name);
    }

    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    public String getContextPath() {
        return contextPath;
    }

    public String[] getRequestMultipleAttribute(String name) {
        if (requestMultipleParametres.containsKey(name)) {
            return requestMultipleParametres.get(name);
        }
        if (requestParameters.containsKey(name)) {
            String[] res = new String[1];
            res[0] = requestParameters.get(name);
            return res;
        }
        return new String[0];
    }


    public void setRequestParameter(String name, String value) {
        requestParameters.put(name, value);
    }

    public void setRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }

    public void setSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }

    public void setRequestMultipleParameter(String name, String[] values) {
        requestMultipleParametres.put(name, values);
    }

    public void refresh(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Enumeration<String> requestNames = session.getAttributeNames();
        Set<String> mapNames = sessionAttributes.keySet();

        List<String> requestList = Collections.list(requestNames);
        requestList.removeAll(mapNames);

        for (String name : requestList) {
            session.setAttribute(name, null);
        }

        for (String name : mapNames) {
            session.setAttribute(name, sessionAttributes.get(name));
        }

        requestNames = request.getAttributeNames();
        mapNames = requestAttributes.keySet();
        Set<String> multipleMapNames = requestMultipleParametres.keySet();

        requestList = Collections.list(requestNames);
        requestList.removeAll(mapNames);
        requestList.removeAll(multipleMapNames);

        for (String name : requestList) {
            request.setAttribute(name, null);
        }

        for (String name : mapNames) {
            request.setAttribute(name, requestAttributes.get(name));
        }

        for (String name : multipleMapNames) {
            request.setAttribute(name, requestMultipleParametres.get(name));
        }
    }
}
