package osky.web.beetl.func;


import org.beetl.core.Context;
import org.beetl.core.Function;

public class TimestampFunc implements Function {

    public String call(Object[] paras, Context ctx) {
        return String.valueOf(System.currentTimeMillis());

    }
}