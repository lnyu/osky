package osky.web.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import osky.Constants;
import osky.core.exception.ExceptionKit;
import osky.vo.ResponseVo;

import java.util.Map;
import java.util.Random;

public class SharedTokenHolder {

    public static final String TOKEN = "token";
    private static final String DEFAULT_CACHE = "shared_token";

    private static CacheManager cacheManager = null;
    private static Cache cache = null;

    private static Random random = new Random();

    private SharedTokenHolder() {

    }

    /**
     * Wrapper a json map response
     *
     * @param response
     */
    public static void wrapper(Map<String, Object> response) {
        String token = SharedTokenHolder.create();
        if (StringUtils.isNotBlank(token)) {
            response.put(SharedTokenHolder.TOKEN, token);
        }
    }

    /**
     * Wrapper a json map response
     *
     * @param response
     */
    public static void wrapper(ResponseVo response) {
        String token = SharedTokenHolder.create();
        if (StringUtils.isNotBlank(token)) {
            response.setToken(token);
        }
    }

    /**
     * Create Token.
     *
     * @return
     */
    public static String create() {
        if (cache == null) {
            if (cacheManager != null) {
                cache = cacheManager.getCache(DEFAULT_CACHE);
            }
        }

        String tokenId = null;
        if (cache != null) {
            int safeCounter = 8;
            do {
                if (safeCounter-- == 0) {
                    ExceptionKit.throwException(Constants.ERROR_CODE.WEB_TOKEN_GENERATOR);
                }
                tokenId = String.valueOf(random.nextLong());
            } while (tokenId == null || cache.get(tokenId) != null);
            cache.put(tokenId, true);
        }

        return tokenId;
    }


    /**
     * @param tokenId Check token to prevent resubmit.
     */
    public static synchronized boolean validate(String tokenId) {
        if (cache == null) {
            if (cacheManager != null) {
                cache = cacheManager.getCache(DEFAULT_CACHE);
            }
        }

        boolean result = true;
        if (cache != null) {
            result = cache.get(tokenId) != null;
            cache.evict(tokenId);
        }

        return result;
    }
}





