package com.grasia.prima.ppi.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        setHeaderResponse(response);
        writeJsonToWriter(response);
    }

    private void setHeaderResponse(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void writeJsonToWriter(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(getJsonUnauthorizedResponse());
        writer.flush();
        writer.close();
    }

    private String getJsonUnauthorizedResponse() throws JsonProcessingException {
        GlobalMessage error = GlobalMessage.UNAUTHORIZED;
        BaseResponse<Object> baseResponse = BaseResponse.builder()
                .code(error.status.value())
                .message(error.message)
                .build();
        return objectMapper.writeValueAsString(baseResponse);
    }
}
