package com.tianheng.codetool.annotation;

import com.tianheng.codetool.config.CodetoolConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CodetoolConfig.class})
@Documented
@Inherited
public @interface EnableCodetool {
}
