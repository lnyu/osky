package osky.web.validate;

import org.aspectj.lang.JoinPoint;
import osky.Constants;
import osky.core.exception.ExceptionKit;
import osky.core.exception.UncheckedException;

public class Inject {

    public void before(JoinPoint jp) {
        try {

            Class[] argTypes = new Class[jp.getArgs().length];
            for (int i = 0; i < argTypes.length; i++) {
                argTypes[i] = jp.getArgs()[i].getClass();
            }

            String method = jp.getSignature().getName();
            Before before = jp.getTarget().getClass().getMethod(method, argTypes).getAnnotation(Before.class);
            if (before != null) {
                Class<? extends Validator>[] validators = before.validators();
                if (validators != null && validators.length > 0) {
                    for (Class<? extends Validator> clazz : validators) {
                        Validator validator = clazz.newInstance();
                        validator.intercept();
                    }
                }
            }
        } catch (UncheckedException exception) {
            throw exception;
        } catch (Exception e) {
            ExceptionKit.throwException(
                    Constants.ERROR_CODE.WEB_VALIDATOR_GENERATOR,
                    "error to invoke the validator.");
        }
    }
}
