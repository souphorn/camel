/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.properties;

import org.apache.camel.CamelContext;
import org.apache.camel.ContextTestSupport;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

public class PropertiesComponentSimpleLanguageTest extends ContextTestSupport {

    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }

    @Test
    public void testPropertiesComponentSimpleLanguage() throws Exception {
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").transform().simple("Hi ${body} do you think ${properties:cheese.quote}?");
            }
        });
        context.start();

        String reply = template.requestBody("direct:start", "Claus", String.class);
        assertEquals("Hi Claus do you think Camel rocks?", reply);
    }

    @Test
    public void testPropertiesComponentDualSimpleLanguage() throws Exception {
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").transform().simple("Hi ${body} do you think ${properties:cheese.quote}? And do you like ${properties:cheese.type} cheese?");
            }
        });
        context.start();

        String reply = template.requestBody("direct:start", "Claus", String.class);
        assertEquals("Hi Claus do you think Camel rocks? And do you like Gouda cheese?", reply);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();

        PropertiesComponent pc = new PropertiesComponent();
        pc.setLocation("classpath:org/apache/camel/component/properties/cheese.properties");
        context.addComponent("properties", pc);

        return context;
    }

}
