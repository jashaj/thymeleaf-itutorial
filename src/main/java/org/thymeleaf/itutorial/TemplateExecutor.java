/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package org.thymeleaf.itutorial;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.itutorial.beans.Gender;
import org.thymeleaf.itutorial.beans.PaymentMethod;
import org.thymeleaf.spring4.messageresolver.SpringMessageResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.tools.memoryexecutor.StaticTemplateExecutor;

public class TemplateExecutor {

    private final StaticTemplateExecutor executor;

    public TemplateExecutor(
        final HttpServletRequest request, final HttpServletResponse response,
        final ServletContext servletContext, final MessageSource messageSource,
        final Locale locale) {
        Map<String, Object > variables = new HashMap<>();
        variables.put("product", DAO.loadProduct());
        variables.put("productList", DAO.loadAllProducts());
        variables.put("html", "This is an <em>HTML</em> text. <b>Enjoy yourself!</b>");
        variables.put("customer", DAO.loadCustomer());
        variables.put("customerName", "Dr. Julius Erwing");
        variables.put("customerList", DAO.loadAllCustomers());
        variables.put("genders", Gender.values());
        variables.put("paymentMethods", PaymentMethod.values());
        variables.put("price", DAO.loadAmount());
        variables.put("releaseDate", DAO.loadReleaseDate());
        variables.put("productId", 273);
        variables.put("productName", "Red-carpet");
        WebContext webContext = new WebContext(request, response, servletContext, locale, variables);
        String templateMode = TemplateMode.HTML.name();
        SpringMessageResolver messageResolver = new SpringMessageResolver();
        messageResolver.setMessageSource(messageSource);
        executor = new StaticTemplateExecutor(webContext, messageResolver, templateMode);
    }

    public String generateCode(final String code) {
        return executor.processTemplateCode(code);
    }
}
