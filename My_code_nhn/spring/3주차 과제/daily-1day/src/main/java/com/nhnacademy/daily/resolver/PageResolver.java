package com.nhnacademy.daily.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.springframework.data.domain.Pageable;
import java.util.Objects;

public class PageResolver implements HandlerMethodArgumentResolver {
    private static final int MAX_PAGE = 10;
    private static final int MAX_SIZE = 10;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 5;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {


        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        int page = Objects.isNull(httpServletRequest.getParameter("page"))? DEFAULT_PAGE : Integer.parseInt(httpServletRequest.getParameter("page"));
        int size = Objects.isNull(httpServletRequest.getParameter("size"))? DEFAULT_SIZE : Integer.parseInt(httpServletRequest.getParameter("size"));

        page = page > MAX_PAGE ? MAX_PAGE : page;
        size = size > MAX_SIZE ? MAX_SIZE : size;

        return PageRequest.of(page, size);
    }
}
