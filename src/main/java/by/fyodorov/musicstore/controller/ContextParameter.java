package by.fyodorov.musicstore.controller;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.fyodorov.musicstore.application.InitParameter.FILE_PATH;

/**
 * singleton with servlet initialization arguments
 */
public class ContextParameter {
    private static ContextParameter instance;
    private static Lock instanceLock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private HashMap<String, String> paramMap;

    private ContextParameter() {
        paramMap = new HashMap<>();
    }

    /**
     * adding parameters from servlet context
     * @param context - servlet context
     */
    public void addContextParam(ServletContext context) {
        Enumeration<String> paramNames = context.getInitParameterNames();
        paramMap.put(FILE_PATH.toString(), context.getRealPath(FILE_PATH.toString()));
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            paramMap.put(name, context.getInitParameter(name));
        }
    }

    /**
     * getting servlet context parameter by key
     * @param key - key of parameter
     * @return - value of this parameter
     */
    public String getContextParam(String key) {
        return paramMap.get(key);
    }

    public static ContextParameter getInstance() {
        if (!isCreated.get()) {
            instanceLock.lock();
            if (instance == null) {
                instance = new ContextParameter();
                isCreated.set(true);
            }
            instanceLock.unlock();
        }
        return instance;
    }
}
