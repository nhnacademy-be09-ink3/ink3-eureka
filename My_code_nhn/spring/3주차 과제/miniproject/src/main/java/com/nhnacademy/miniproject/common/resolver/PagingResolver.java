package com.nhnacademy.miniproject.common.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class PagingResolver implements HandlerMethodArgumentResolver {

    private int DEFAULT_PAGE = 1;
    private int DEFAULT_SIZE = 5;
    private int MAX_PAGE = 10;
    private int MAX_SIZE = 20;


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
        int page = httpServletRequest.getParameter("page") == null ? DEFAULT_PAGE : Integer.parseInt(httpServletRequest.getParameter("page"));
        int size = httpServletRequest.getParameter("size") == null ? DEFAULT_SIZE : Integer.parseInt(httpServletRequest.getParameter("size"));

        page = page > MAX_PAGE ? MAX_PAGE : page;
        size = size > MAX_SIZE ? MAX_SIZE : size;

        return PageRequest.of(page, size);
    }
}
