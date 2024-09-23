package com.grasia.prima.ppi.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomAuthenticationEntryPointTest {

    @InjectMocks
    private CustomAuthenticationEntryPoint entryPoint;

    @Mock
    private AuthenticationException authException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCommence() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        entryPoint.commence(request, response, authException);

        // Verify the response status and content type
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("UTF-8", response.getCharacterEncoding());

        // Verify the response body
        ObjectMapper objectMapper = new ObjectMapper();
        GlobalMessage error = GlobalMessage.UNAUTHORIZED;
        BaseResponse<Object> expectedResponse = BaseResponse.builder()
                .code(error.status.value())
                .message(error.message)
                .build();
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        assertEquals(expectedJson, response.getContentAsString());
    }
}