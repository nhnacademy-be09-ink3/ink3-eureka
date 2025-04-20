package com.nhnacademy.miniproject.config;

import com.nhnacademy.miniproject.common.converter.CsvCreateMemberRequestHttpMessageConverter;
import com.nhnacademy.miniproject.common.converter.CsvMemberHttpMessageConverter;
import com.nhnacademy.miniproject.common.resolver.PagingResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new CsvCreateMemberRequestHttpMessageConverter());
        converters.add(new CsvMemberHttpMessageConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PagingResolver());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login"); //추가
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/member").setViewName("member");
        registry.addViewController("/google").setViewName("google");
        registry.addViewController("/403").setViewName("403"); //추가
        registry.addViewController("/block").setViewName("block"); //추가
    }

}
