package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.JwtComponentDto;
import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.SearchDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.LoginRequest;
import com.grasia.prima.ppi.api.dto.response.LoginResponse;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ObjectDummy {


    private ObjectDummy() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE" +
            "3MjYxMDUyMDgsImV4cCI6MTc1NzY0MTIwOH0.GCHe-PZy5ES38A8lDxuDkHbxWSM6ZAlkmzMpMrR8HFk";
    public static final String JWT_SECRET = "357643192F423F44284GXabT72B4B6250655368566D597133743677397A2543164629";
    public static final String JWT_EXPIRED_DURATION = "31536000000";

    public static Date getDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    public static SearchDto getSearchDto() {
        return SearchDto.builder().search("").build();
    }

    public static PageDto getPageDto() {
        return PageDto.builder().search("").page(1).size(10).build();
    }

    public static HeaderRequest getHeaderRequest() {
        return HeaderRequest.builder().userId(1L).userName("Ucup").build();
    }

    public static LoginRequest getLoginRequest() {
        return LoginRequest.builder()
                .username("admin")
                .password("admin147")
                .build();
    }

    public static LoginResponse getLoginResponse() {
        return LoginResponse.builder()
                .jwt(JWT)
                .build();
    }

    public static MUser getUser() {
        return MUser.builder()
                .id(1L)
                .username("admin")
                .password("$2a$12$aXJHIHcSPjINQaVjgxmKgOtsN9Ifb7D3TatHZYBjIk4ZEVu6E7lb2")
                .build();
    }

    public static JwtComponentDto getJwtComponentDto() {
        return JwtComponentDto.builder().username("admin").build();
    }
}
