package org.thymeleaf.tools.templateresolvers;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Map;

/**
 * Resolves a string template that starts with 'dynamic:' and contains newlines. Meant for the templates that are sent
 * from the textarea in the exercises.
 */
public class DynamicTemplateResolver extends StringTemplateResolver {

    public static final String PREFIX = "dynamic:";

    @Override
    protected ITemplateResource computeTemplateResource(final IEngineConfiguration configuration, final String ownerTemplate, final String template,
                                                        final Map<String, Object> templateResolutionAttributes) {
        if (template == null || !template.startsWith(PREFIX)) {
            // Cannot resolve this template, let another TemplateResolver try it
            return null;
        }

        String actualTemplate = template.substring(PREFIX.length());
        return super.computeTemplateResource(configuration, ownerTemplate, actualTemplate, templateResolutionAttributes);
    }

}
