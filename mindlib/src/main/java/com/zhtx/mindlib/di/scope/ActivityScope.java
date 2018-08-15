package com.zhtx.mindlib.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者: ljz.
 * @date 2017/11/15
 * 描述：Activity生命周期
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}