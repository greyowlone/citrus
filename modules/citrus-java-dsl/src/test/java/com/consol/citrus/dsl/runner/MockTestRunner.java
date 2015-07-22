/*
 * Copyright 2006-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.dsl.runner;

import com.consol.citrus.TestCase;
import org.springframework.context.ApplicationContext;

/**
 * @author Christoph Deppisch
 * @since 2.2.1
 */
public class MockTestRunner extends DefaultTestRunner {

    /**
     * Constructor using an application context.
     * @param applicationContext
     */
    public MockTestRunner(String name, ApplicationContext applicationContext) {
        super(applicationContext);

        name(name);

        start();
        execute();
        stop();
    }

    public void execute() {
    }

    @Override
    protected TestCase getTestCase() {
        return super.getTestCase();
    }
}