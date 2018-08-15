package com.zhtx.mindlib.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者: ljz.
 * @date 2017/11/17.
 * 描述：api注解
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ApiUrl {

}
