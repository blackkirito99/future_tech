package presentation;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestMapper {

    public Map < String, String[] > extract(HttpServletRequest request) {
        Map < String, String[] > requestMap = null;
        requestMap = request.getParameterMap();

        return requestMap;
    }

    public void bind(HttpServletRequest request, Map < String, Object > responseMap) {

        Set < String > keys = null;
        keys = responseMap.keySet();

        for (String param: keys) {
            request.setAttribute(param, responseMap.get(param));
        }

    }

}