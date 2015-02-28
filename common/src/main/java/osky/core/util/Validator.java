package osky.core.util;

import org.apache.commons.lang3.StringUtils;
import osky.Constants;
import osky.core.exception.ExceptionKit;

public class Validator {


    public static void isNotNull(Object object, String message) {
        if (object == null) {
            ExceptionKit.throwException(Constants.ERROR_CODE.VALIDATOR, message);
        }
    }


    public static void isNotBlank(String string, String message) {
        if (StringUtils.isBlank(string)) {
            ExceptionKit.throwException(Constants.ERROR_CODE.VALIDATOR, message);
        }
    }
}
