package osky.core.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import osky.core.context.SpringContextHolder;

import java.util.Locale;

public class I18N {

    private static final Logger logger = LoggerFactory.getLogger(I18N.class);

    private static final Locale DEFAULT = Locale.SIMPLIFIED_CHINESE;

    private static ResourceBundleMessageSource message = null;

    public static String get(String code, Object... args) {
        String value = null;
        try {
            if (message == null) {
                message = SpringContextHolder.getBean(ResourceBundleMessageSource.class);
            }
            value = message.getMessage(code, args, DEFAULT);
        } catch (Throwable e) {
            logger.warn("", e);
        }
        return value == null || "".equals(value.trim()) ? code : value;
    }
}
