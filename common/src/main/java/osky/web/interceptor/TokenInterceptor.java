package osky.web.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import osky.Constants;
import osky.core.exception.ExceptionKit;
import osky.web.cache.SharedTokenHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenId = request.getParameter(SharedTokenHolder.TOKEN);
        if (StringUtils.isNotBlank(tokenId) && !SharedTokenHolder.validate(tokenId)) {
            ExceptionKit.throwException(Constants.ERROR_CODE.WEB_TOKEN);
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getHeader("X-Requested-With") != null &&
                request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) {
            request.setAttribute(SharedTokenHolder.TOKEN, SharedTokenHolder.create());
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}