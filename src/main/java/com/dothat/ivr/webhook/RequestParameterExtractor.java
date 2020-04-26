package com.dothat.ivr.webhook;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Extracts the request parameters as a Map of List Values
 *
 * @author abhideep@ (Abhideep Singh)
 */
class RequestParameterExtractor {

  static Map<String, List<String>> extractFromGetRequest(String queryString)
      throws UnsupportedEncodingException {
    Map<String, List<String>> parameters = new HashMap<>();
    if (queryString != null) {
      String[] queryParameters = queryString.split("&");
      for (String queryParameter : queryParameters) {
        String nameValuePair = URLDecoder.decode(queryParameter, "UTF-8");
        String value = null;
        String key = nameValuePair;
        int separatorIndex = queryParameter.indexOf("=");
        if (separatorIndex > 0) {
          key = nameValuePair.substring(0, separatorIndex);
          if (nameValuePair.length() > separatorIndex + 1) {
            value = nameValuePair.substring(separatorIndex + 1);
          }
        }
        key = key.toUpperCase();
        if (!parameters.containsKey(key)) {
          parameters.put(key, new LinkedList<String>());
        }
        parameters.get(key).add(value);
      }
    }
    return parameters;
  }

  static Map<String, List<String>> extractFromPostRequest(HttpServletRequest req)
      throws UnsupportedEncodingException {
    Map<String, List<String>> parameters = new HashMap<>();
    Map parameterMap = req.getParameterMap();
    if (parameterMap != null) {
      for (Object mapKey : parameterMap.keySet()) {
        String key = (String) mapKey;
        key = key.toUpperCase();
        if (!parameters.containsKey(key)) {
          parameters.put(key, new LinkedList<String>());
        }
        String[] values = req.getParameterValues(key);
        if (values != null) {
          parameters.get(key).addAll(Arrays.asList(values));
        }
      }
    }
    return parameters;
  }
}
