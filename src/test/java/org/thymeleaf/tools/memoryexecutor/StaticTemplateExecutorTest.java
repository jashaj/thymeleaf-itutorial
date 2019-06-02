/*
 * Copyright 2013 The THYMELEAF team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thymeleaf.tools.memoryexecutor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.StandardMessageResolver;

public class StaticTemplateExecutorTest {

    @Test
    public void simpleTemplateWithOneVariable() {
        String simpleTemplate =
            "<!DOCTYPE html>"
            + "<html>"
            + "    <head>"
            + "        <title>Test</title>"
            + "    </head>"
            + "    <body>"
            + "        <h1 th:text=\"${greeting}\"></h1>"
            + "    </body>"
            + "</html>";
        String expected =
            "<!DOCTYPE html>"
            + "<html>"
            + "    <head>"
            + "        <title>Test</title>"
            + "    </head>"
            + "    <body>"
            + "        <h1>Hello world!</h1>"
            + "    </body>"
            + "</html>";
        Context context = new Context();
        context.setVariable("greeting", "Hello world!");
        StandardMessageResolver messageResolver = new StandardMessageResolver();
        StaticTemplateExecutor executor = new StaticTemplateExecutor(context, messageResolver);
        String result = executor.processTemplateCode(simpleTemplate);
        assertEquals(expected, result);
    }
}
