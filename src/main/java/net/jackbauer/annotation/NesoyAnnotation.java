package net.jackbauer.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD,
})
public @interface NesoyAnnotation {
    String value() default "NesoyAnnotation : Default String Value";
}
