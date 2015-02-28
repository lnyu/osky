package osky.web.beetl.func;

import org.beetl.core.Context;
import org.beetl.core.Function;

public class I18N implements Function {
    public Object call(Object[] obj, Context context) {
        String key = "", value = "";
        try {
            if (obj != null && obj.length > 0) {
                key = String.valueOf(obj[0]);
                if (obj.length > 1) {
                    Object[] args = new Object[obj.length - 1];
                    System.arraycopy(obj, 1, args, 0, args.length);
                    return osky.core.i18n.I18N.get(key, args);
                } else {
                    return osky.core.i18n.I18N.get(key);
                }
            }
        } catch (Exception e) {
        }
        return value == null || "".equals(value) ? key : value;
    }
}
