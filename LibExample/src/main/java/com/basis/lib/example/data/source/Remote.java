package com.basis.lib.example.data.source;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by linpei on 2016/8/12.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Remote {
}
