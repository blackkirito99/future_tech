package presentation;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {

    private String requestUri;
    private Map < String, String[] > requestMap;
    private Map < String, Object > responseMap;

    public RequestContext() {
        requestUri = null;
        requestMap = new HashMap < String, String[] > ();
        responseMap = new HashMap < String, Object > ();
    }

    public RequestContext(String requestUri, Map < String, String[] > requestMap) {
        this.requestUri = requestUri;
        this.requestMap = requestMap;
        this.responseMap = new HashMap < String, Object > ();
    }

    public String getParameter(String param) {
        String[] val = null;
        if (param != null) {
            val = requestMap.get(param);
        }
        return val[0];
    }

    public void setAttribute(String param, Object value) {
        responseMap.put(param, value);
    }

    public String getRequestUri() {
        return requestUri;
    }

    public Map < String, String[] > getRequestMap() {
        return requestMap;
    }

    public Map < String, Object > getResponseMap() {
        return responseMap;
    }

}