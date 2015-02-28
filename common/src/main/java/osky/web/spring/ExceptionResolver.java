package osky.web.spring;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import osky.core.exception.ExceptionKit;
import osky.core.exception.UncheckedException;
import osky.web.cache.SharedTokenHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ExceptionResolver extends SimpleMappingExceptionResolver {

    private String defaultViewSuffix = ".html";

    private String defaultErrorRoot = "/error/";

    private Set<Integer> defaultErrorSets = new TreeSet<Integer>();

    public void setDefaultErrorRoot(String defaultErrorRoot) {
        if (StringUtils.isNotBlank(defaultErrorRoot) &&
                defaultErrorRoot.charAt(defaultErrorRoot.length() - 1) != '/') {
            defaultErrorRoot = defaultErrorRoot + "/";
        }
        this.defaultErrorRoot = defaultErrorRoot;
    }

    public void setDefaultErrorSets(String defaultErrorSets) {
        String[] errMaps = defaultErrorSets.split(",");
        for (String errMap : errMaps) {
            Integer errCode = Integer.parseInt(errMap);
            this.defaultErrorSets.add(errCode);
        }
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        UncheckedException exception = ExceptionKit.wrapper(e);
        boolean ajax = request.getHeader("X-Requested-With") != null &&
                request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1;

        ModelAndView modelAndView = null;
        if (ajax) {
            if (logger.isDebugEnabled()) {
                logger.debug("Render a json view for the exception,{}.", e);
            }
            modelAndView = this.doResolveJsonException(exception);
        } else {
            modelAndView = super.doResolveException(request, response, handler, e);
            Integer code = exception.getCode();
            if (modelAndView != null) {
                modelAndView.addObject("code", code);
                modelAndView.addObject("message", exception.getMessage());
                if (code != null && defaultErrorSets.contains(code)) {
                    modelAndView.setViewName(defaultErrorRoot + String.valueOf(code) + defaultViewSuffix);
                } else {
                    String viewName = modelAndView.getViewName();
                    if (viewName.startsWith("/")) {
                        viewName = viewName.substring(1);
                    }
                    modelAndView.setViewName(defaultErrorRoot + viewName);
                }
            }
        }
        return modelAndView;
    }


    private ModelAndView doResolveJsonException(UncheckedException e) {
        ModelAndView modelAndView = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("code", e.getCode());
        attributes.put("message", e.getMessage());
        SharedTokenHolder.wrapper(attributes);
        view.setAttributesMap(attributes);
        modelAndView.setView(view);
        return modelAndView;
    }

}
