package by.fyodorov.musicstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * request map for performing command. Saving servlet attributes of request and session
 */
public class RequestParameterMap {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;
    private HashMap<String, String> requestParameters;
    private HashMap<String, String[]> requestMultipleParameters;
    private String contextPath;

    /**
     * creating parameter map for current request. Saving all request attributes
     *
     * @param request - request from servlet
     */
    public RequestParameterMap(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        requestMultipleParameters = new HashMap<>();

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
                requestMultipleParameters.put(name, request.getParameterValues(name));
            } else {
                requestParameters.put(name, request.getParameter(name));
            }
        }
    }

    /**
     * getting parameter from map
     *
     * @param name - key of parameter
     * @return - value of parameter
     */
    public String getParameter(String name) {
        return requestParameters.get(name);
    }

    /**
     * getting attribute from map
     *
     * @param name - key of attribute
     * @return - value of attribute
     */
    public Object getRequestAttribute(String name) {
        return requestAttributes.get(name);
    }

    /**
     * getting session attribute from map
     *
     * @param name - key of session parameter
     * @return - value of session parameter
     */
    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    /**
     * getting context path of request
     *
     * @return - context path
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * getting multiple attribute from map. If map doesn't contain this key - return empty array.
     *
     * @param name - key of multiple attribute in map
     * @return - array of attributes. If map doesn't contain this key - return empty array
     * If map doesn't contain this key, but contain as parameter - return array with this parameter
     */
    public String[] getRequestMultipleAttribute(String name) {
        if (requestMultipleParameters.containsKey(name)) {
            return requestMultipleParameters.get(name);
        }
        if (requestParameters.containsKey(name)) {
            String[] res = new String[1];
            res[0] = requestParameters.get(name);
            return res;
        }
        return new String[0];
    }

    /**
     * setting parameter for refreshing servlet request
     *
     * @param name  - key of parameter
     * @param value - new value of parameter
     */
    public void setRequestParameter(String name, String value) {
        requestParameters.put(name, value);
    }

    /**
     * setting attribute for refreshing servlet request
     *
     * @param name  - key of attribute
     * @param value - new value of attribute
     */
    public void setRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }

    /**
     * setting session attribute for refreshing servlet session
     *
     * @param name  - key of attribute
     * @param value - new value of attribute
     */
    public void setSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }

    /**
     * refresh servlet request by this map
     *
     * @param request - request for refreshing. Upload in request new values of parameters
     */
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
        Set<String> multipleMapNames = requestMultipleParameters.keySet();

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
            request.setAttribute(name, requestMultipleParameters.get(name));
        }
    }
}
