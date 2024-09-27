package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.JwtComponentDto;
import com.grasia.prima.ppi.api.dto.request.*;
import com.grasia.prima.ppi.api.dto.response.*;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.dto.search.SystemParameterListSearchDto;
import com.grasia.prima.ppi.api.entity.*;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.time.LocalDate;

public final class ObjectDummy {


    private ObjectDummy() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE" +
            "3MjYxMDUyMDgsImV4cCI6MTc1NzY0MTIwOH0.GCHe-PZy5ES38A8lDxuDkHbxWSM6ZAlkmzMpMrR8HFk";
    public static final String JWT_SECRET = "357643192F423F44284GXabT72B4B6250655368566D597133743677397A2543164629";
    public static final String JWT_EXPIRED_DURATION = "31536000000";

    public static SearchDto getSearchDto() {
        return SearchDto.builder().search("").page(1).size(10).build();
    }

    public static SystemParameterListSearchDto getSystemParameterListSearchDto() {
        return SystemParameterListSearchDto.builder().systemParameterId(1L).search("").page(1).size(10).build();
    }

    public static HeaderRequest getHeaderRequest() {
        return HeaderRequest.builder().userId(1L).userFullName("Ucup").build();
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
                .fullName("admin")
                .email("admin@gmail.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .education("education")
                .graduation("graduation")
                .userRole(getUserRole())
                .gender(getSystemParameterList())
                .build();
    }

    public static UserCreateRequest getUserCreateRequest() {
        return UserCreateRequest.builder()
                .username("admin")
                .password("admin")
                .passwordConfirm("admin")
                .fullName("admin")
                .email("admin@gmail.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .education("education")
                .graduation("graduation")
                .userRoleId(1L)
                .genderId(1L)
                .build();
    }

    public static UserUpdateRequest getUserUpdateRequest() {
        return UserUpdateRequest.builder()
                .username("admin")
                .fullName("admin")
                .email("admin@gmail.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .education("education")
                .graduation("graduation")
                .userRoleId(1L)
                .genderId(1L)
                .build();
    }

    public static UserResponse getUserResponse() {
        return UserResponse.builder()
                .id(1L)
                .username("admin")
                .fullName("admin")
                .email("admin@gmail.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .education("education")
                .graduation("graduation")
                .userRole(getUserRoleResponse())
                .gender(getSystemParameterListResponse())
                .build();
    }

    public static JwtComponentDto getJwtComponentDto() {
        return JwtComponentDto.builder()
                .userId("1")
                .username("admin")
                .userFullName("admin")
                .build();
    }

    public static MUserRole getUserRole() {
        return MUserRole.builder()
                .id(1L)
                .name("admin")
                .build();
    }

    public static UserRoleRequest getUserRoleRequest() {
        return UserRoleRequest.builder().name("admin").build();
    }

    public static UserRoleResponse getUserRoleResponse() {
        return UserRoleResponse.builder().id(1L).name("admin").userCount(1).build();
    }

    public static LogAuth getLogAuth() {
        return LogAuth.builder()
                .id(1L)
                .refreshToken("refresh-token")
                .refreshTokenExpiry(LocalDate.now().plusMonths(1))
                .user(getUser())
                .build();
    }

    public static RefreshTokenRequest getRefreshTokenRequest() {
        return RefreshTokenRequest.builder().refreshToken("refresh-token").build();
    }

    public static MSystemParameter getSystemParameter() {
        return MSystemParameter.builder().id(1L).name("GENDER").build();
    }

    public static SystemParameterRequest getSystemParameterRequest() {
        return SystemParameterRequest.builder().name("GENDER").build();
    }

    public static SystemParameterResponse getSystemParameterResponse() {
        return SystemParameterResponse.builder().id(1L).name("GENDER").build();
    }

    public static MSystemParameterList getSystemParameterList() {
        return MSystemParameterList.builder().id(1L).name("MAN").systemParameter(getSystemParameter()).build();
    }

    public static SystemParameterListRequest getSystemParameterListRequest() {
        return SystemParameterListRequest.builder().name("MAN").systemParameterId(1L).build();
    }

    public static SystemParameterListResponse getSystemParameterListResponse() {
        return SystemParameterListResponse.builder()
                .id(1L)
                .name("MAN")
                .systemParameter(getSystemParameterResponse())
                .build();
    }

    public static MDepartment getDepartment() {
        return MDepartment.builder().id(1L).name("department 1").build();
    }

    public static DepartmentRequest getDepartmentRequest() {
        return DepartmentRequest.builder().name("department 1").build();
    }

    public static DepartmentResponse getDepartmentResponse() {
        return DepartmentResponse.builder().id(1L).name("department 1").build();
    }

    public static MCommittee getCommittee() {
        return MCommittee.builder()
                .id(1L)
                .name("committee 1")
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .build();
    }

    public static CommitteeRequest getCommitteeRequest() {
        return CommitteeRequest
                .builder()
                .name("committee 1")
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .build();
    }

    public static CommitteeResponse getCommitteeResponse() {
        return CommitteeResponse.builder()
                .id(1L)
                .name("department 1")
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .build();
    }
}
