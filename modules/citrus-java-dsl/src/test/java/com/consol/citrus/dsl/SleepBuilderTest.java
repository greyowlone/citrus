/*
 * Copyright 2006-2012 the original author or authors.
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

package com.consol.citrus.dsl;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.consol.citrus.actions.SleepAction;

/**
 * @author Christoph Deppisch
 */
public class SleepBuilderTest {
    
    @Test
    public void testNG() {
        CitrusTestBuilder builder = new CitrusTestBuilder() {
            @Override
            protected void configure() {
                sleep(0.5);
                sleep(500);
            }
        };
        
        builder.configure();
        
        Assert.assertEquals(builder.getTestCase().getActions().size(), 2);
        Assert.assertEquals(builder.getTestCase().getActions().get(0).getClass(), SleepAction.class);
        Assert.assertEquals(builder.getTestCase().getActions().get(1).getClass(), SleepAction.class);
        
        Assert.assertEquals(((SleepAction)builder.getTestCase().getActions().get(0)).getName(), SleepAction.class.getSimpleName());
        
        Assert.assertEquals(((SleepAction)builder.getTestCase().getActions().get(0)).getDelay(), "0.5");
        Assert.assertEquals(((SleepAction)builder.getTestCase().getActions().get(1)).getDelay(), "0.5");
    }
}