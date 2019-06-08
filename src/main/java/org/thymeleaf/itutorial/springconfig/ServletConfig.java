package org.thymeleaf.itutorial.springconfig;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.itutorial.beans.AmountFormatter;
import org.thymeleaf.itutorial.beans.TimestampFormatter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.tools.templateresolvers.DynamicTemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@EnableWebMvc
@Configuration
public class ServletConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LocaleChangeInterceptor());
  }

  @Override
  public void addFormatters(final FormatterRegistry registry) {
    registry.addFormatter(new AmountFormatter());
    registry.addFormatter(new TimestampFormatter());
  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    return messageSource;
  }

  @Bean
  public LocaleResolver localeResolver() {
    return new SessionLocaleResolver();
  }

  @Bean
  public TemplateEngine templateEngine() {
    SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
    springTemplateEngine.addTemplateResolver(templateResolver());
    springTemplateEngine.addTemplateResolver(dynamicTemplateResolver());
    return springTemplateEngine;
  }

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setOrder(0);
    templateResolver.setCacheable(false);
    templateResolver.setApplicationContext(applicationContext);
    templateResolver.setPrefix("/WEB-INF/templates/");
    templateResolver.setResolvablePatterns(Collections.singleton("*.html"));
    templateResolver.setTemplateMode(TemplateMode.HTML);
    return templateResolver;
  }

  @Bean
  public DynamicTemplateResolver dynamicTemplateResolver() {
    final DynamicTemplateResolver resolver = new DynamicTemplateResolver();
    resolver.setOrder(1);
    return resolver;
  }

  @Bean
  public ViewResolver viewResolver(TemplateEngine templateEngine) {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine);
    resolver.setCache(false);
    resolver.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
    return resolver;
  }

}
