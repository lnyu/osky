package osky.web.validate;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {

    Class<? extends Validator>[] validators() default {};
}