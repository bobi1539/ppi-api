package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class CustomResponseAdviceTest {

    @InjectMocks
    private CustomResponseAdvice customResponseAdvice;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testSupports_ReturnsTrue() {
        Class<? extends HttpMessageConverter<?>> converterType = (Class<? extends HttpMessageConverter<?>>) mock(HttpMessageConverter.class).getClass();
        boolean result = customResponseAdvice.supports(methodParameter, converterType);
        assertTrue(result);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testBeforeBodyWrite_WithRestControllerAnnotation() {
        Object body = new Object();
        MediaType mediaType = MediaType.APPLICATION_JSON;
        Class<? extends HttpMessageConverter<?>> converterType = (Class<? extends HttpMessageConverter<?>>) mock(HttpMessageConverter.class).getClass();

        doReturn(RestControllerClass.class).when(methodParameter).getContainingClass();

        Object result = customResponseAdvice.beforeBodyWrite(
                body, methodParameter, mediaType, converterType, serverHttpRequest, serverHttpResponse
        );

        assertNotNull(result);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testBeforeBodyWrite_WithRestControllerAnnotationAndBodyIsInputStreamResource() {
        Object body = new InputStreamResource(new ByteArrayInputStream("hello".getBytes()));
        MediaType mediaType = MediaType.APPLICATION_JSON;
        Class<? extends HttpMessageConverter<?>> converterType = (Class<? extends HttpMessageConverter<?>>) mock(HttpMessageConverter.class).getClass();

        doReturn(RestControllerClass.class).when(methodParameter).getContainingClass();

        Object result = customResponseAdvice.beforeBodyWrite(
                body, methodParameter, mediaType, converterType, serverHttpRequest, serverHttpResponse
        );

        assertTrue(result instanceof InputStreamResource);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testBeforeBodyWrite_WithoutRestControllerAnnotation() {
        Object body = new Object();
        MediaType mediaType = MediaType.APPLICATION_JSON;
        Class<? extends HttpMessageConverter<?>> converterType = (Class<? extends HttpMessageConverter<?>>) mock(HttpMessageConverter.class).getClass();

        doReturn(NonRestControllerClass.class).when(methodParameter).getContainingClass();

        Object result = customResponseAdvice.beforeBodyWrite(
                body, methodParameter, mediaType, converterType, serverHttpRequest, serverHttpResponse
        );

        assertEquals(body, result);
    }

    @RestController
    private static class RestControllerClass {
    }

    private static class NonRestControllerClass {
    }

    @Test
    void testBuildSuccessResponse() {
        BaseResponse<Object> response = customResponseAdvice.buildSuccessResponse(new Object());
        assertNotNull(response);
        assertEquals(GlobalMessage.SUCCESS.status.value(), response.getCode());
        assertEquals(GlobalMessage.SUCCESS.message, response.getMessage());
    }
}