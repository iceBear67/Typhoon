package io.github.icebear67.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.TYPE})
@Documented
public @interface InternalUseOnly {
}
