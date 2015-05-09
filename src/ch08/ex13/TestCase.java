/*
 * Copyright (C) 2015 Yoshiki Shibata. All rights reserved.
 */
package ch08.ex13;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TestCase annotation for a method.
 */
@Retention(RetentionPolicy.SOURCE)
@Repeatable(TestCases.class)
public @interface TestCase {
    int param();    // parameter
    int expected(); // expected return value
}
