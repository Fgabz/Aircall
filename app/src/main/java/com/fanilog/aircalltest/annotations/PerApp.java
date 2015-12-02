package com.fanilog.aircalltest.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by fanilo on 11/30/15.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}
