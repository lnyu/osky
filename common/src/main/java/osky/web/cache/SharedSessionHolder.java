package osky.web.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SharedSessionHolder {

    public static final String SESSION = "SESSION";
    private static final String DEFAULT_CACHE = "shared_session";
    private static CacheManager cacheManager = null;
    private static Cache cache = null;

    /**
     * @param request
     * @return
     */
    private static String getSessionId(HttpServletRequest request) {
        String cacheKey = request.getParameter("jsessionid");
        if (cacheKey == null || "".equals(cacheKey.trim())) {
            HttpSession session = request.getSession(true);
            if (session != null) {
                cacheKey = request.getSession(true).getId();
            }
        }
        if (cacheKey != null) {
            cacheKey = cacheKey.trim();
        }
        return "".equals(cacheKey) ? null : cacheKey;
    }

    /**
     * @param create
     * @param request
     * @return
     */
    private static Map<String, Object> getCacheMap(boolean create, HttpServletRequest request) {
        if (cache == null) {
            if (cacheManager != null) {
                cache = cacheManager.getCache(DEFAULT_CACHE);
            }
        }

        Map<String, Object> shared = null;
        if (cache != null) {
            String cacheKey = getSessionId(request);
            Cache.ValueWrapper cacheValue = cache.get(cacheKey);
            if (cacheValue == null) {
                if (create) {
                    shared = new HashMap<String, Object>();
                    cache.put(cacheKey, shared);
                }
            } else {
                shared = (Map<String, Object>) cacheValue.get();
            }
        }

        return shared;
    }


    /**
     * @param key
     * @param value
     * @param args
     */
    public static void put(String key, Object value, HttpServletRequest... args) {
        HttpServletRequest request = args == null || args.length < 1 ?
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : args[0];

        if (cacheManager == null) {
            request.getSession(true).setAttribute(key, value);
        } else {
            Map<String, Object> cache = getCacheMap(true, request);
            cache.put(key, value);
        }
    }

    /**
     * @param key
     * @param args
     * @return
     */
    public static Object get(String key, HttpServletRequest... args) {
        HttpServletRequest request = args == null || args.length < 1 ?
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : args[0];
        if (cacheManager == null) {
            return request.getSession(true).getAttribute(key);
        } else {
            Map<String, Object> cache = getCacheMap(false, request);
            return cache == null ? null : cache.get(key);
        }
    }

    /**
     * @param key
     * @param args
     * @return
     */
    public static void remove(String key, HttpServletRequest... args) {
        HttpServletRequest request = args == null || args.length < 1 ?
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : args[0];
        if (cacheManager == null) {
            request.getSession(true).removeAttribute(key);
        } else {
            Map<String, Object> cache = getCacheMap(false, request);
            if (cache != null) {
                cache.remove(key);
            }
        }
    }
}
