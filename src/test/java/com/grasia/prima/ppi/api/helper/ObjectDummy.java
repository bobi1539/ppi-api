package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.JwtComponentDto;
import com.grasia.prima.ppi.api.dto.request.*;
import com.grasia.prima.ppi.api.dto.response.*;
import com.grasia.prima.ppi.api.dto.search.*;
import com.grasia.prima.ppi.api.entity.*;
import com.grasia.prima.ppi.api.exception.BusinessException;
import org.springframework.http.MediaType;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public final class ObjectDummy {


    private ObjectDummy() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE" +
            "3MjYxMDUyMDgsImV4cCI6MTc1NzY0MTIwOH0.GCHe-PZy5ES38A8lDxuDkHbxWSM6ZAlkmzMpMrR8HFk";
    public static final String JWT_SECRET = "357643192F423F44284GXabT72B4B6250655368566D597133743677397A2543164629";
    public static final String JWT_EXPIRED_DURATION = "31536000000";

    public static Timestamp getTimestamp() {
        return Timestamp.valueOf(LocalDateTime.of(2024, 10, 10, 10, 10));
    }

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
                .name("admin")
                .email("admin@gmail.com")
                .emailVerifiedAt(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .isActive(true)
                .photo("file.png")
                .description("description")
                .userRole(getUserRole())
                .build();
    }

    public static UserCreateRequest getUserCreateRequest() {
        return UserCreateRequest.builder()
                .username("admin")
                .password("admin")
                .passwordConfirm("admin")
                .name("admin")
                .email("admin@gmail.com")
                .isActive(true)
                .photo(getFileUploadRequest())
                .description("description")
                .userRoleId(1L)
                .build();
    }

    public static UserUpdateRequest getUserUpdateRequest() {
        return UserUpdateRequest.builder()
                .username("admin")
                .name("admin")
                .email("admin@gmail.com")
                .isActive(true)
                .photo(getFileUploadRequest())
                .description("description")
                .userRoleId(1L)
                .build();
    }

    public static UserResponse getUserResponse() {
        return UserResponse.builder()
                .id(1L)
                .username("admin")
                .name("admin")
                .email("admin@gmail.com")
                .emailVerifiedAt(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .isActive(true)
                .photo("file.png")
                .description("description")
                .userRole(getUserRoleResponse())
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
        return MSystemParameter.builder().id(1L).name("GENDER").description("gender description").build();
    }

    public static SystemParameterRequest getSystemParameterRequest() {
        return SystemParameterRequest.builder().name("GENDER").description("gender description").build();
    }

    public static SystemParameterResponse getSystemParameterResponse() {
        return SystemParameterResponse.builder().id(1L).name("GENDER").description("gender description").build();
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

    public static MDivision getDivision() {
        return MDivision.builder()
                .id(1L)
                .name("division 1")
                .period(getPeriod())
                .build();
    }

    public static DivisionRequest getDivisionRequest() {
        return DivisionRequest.builder().name("division 1").periodId(1L).build();
    }

    public static DivisionResponse getDivisionResponse() {
        return DivisionResponse.builder().id(1L).name("division 1").build();
    }

    public static DivisionSearchDto getDivisionSearchDto() {
        return DivisionSearchDto.builder()
                .periodId(1L)
                .search("")
                .isDeleted(false)
                .page(1)
                .size(10)
                .build();
    }

    public static MPeriod getPeriod() {
        return MPeriod.builder()
                .id(1L)
                .name("period 1")
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .build();
    }

    public static PeriodRequest getPeriodRequest() {
        return PeriodRequest
                .builder()
                .name("period 1")
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .build();
    }

    public static PeriodResponse getPeriodResponse() {
        return PeriodResponse.builder()
                .id(1L)
                .name("period 1")
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .build();
    }

    public static MMenu getMenu() {
        return MMenu.builder()
                .id(1L)
                .name("dashboard")
                .route("dashboard")
                .icon("icon")
                .sequence(1)
                .build();
    }

    public static MenuRequest getMenuRequest() {
        return MenuRequest.builder()
                .name("dashboard")
                .route("dashboard")
                .icon("icon")
                .sequence(1)
                .build();
    }

    public static MenuResponse getMenuResponse() {
        return MenuResponse.builder()
                .id(1L)
                .name("dashboard")
                .route("dashboard")
                .icon("icon")
                .sequence(1)
                .build();
    }

    public static MSubMenu getSubMenu() {
        return MSubMenu.builder()
                .id(1L)
                .name("Sub Menu 1")
                .route("sub-menu-1")
                .sequence(1)
                .menu(getMenu())
                .build();
    }

    public static SubMenuRequest getSubMenuRequest() {
        return SubMenuRequest.builder()
                .name("Sub Menu 1")
                .route("sub-menu-1")
                .sequence(1)
                .menuId(1L)
                .build();
    }

    public static SubMenuResponse getSubMenuResponse() {
        return SubMenuResponse.builder()
                .id(1L)
                .name("Sub Menu 1")
                .route("sub-menu-1")
                .sequence(1)
                .menuId(1L)
                .build();
    }

    public static TUserRoleMenu getUserRoleMenu() {
        return TUserRoleMenu.builder()
                .id(1L)
                .userRole(getUserRole())
                .menu(getMenu())
                .createdAt(getTimestamp())
                .build();
    }

    public static TUserRoleSubMenu getUserRoleSubMenu() {
        MSubMenu subMenu = getSubMenu();
        subMenu.setMenu(getMenu());
        return TUserRoleSubMenu.builder()
                .id(1L)
                .userRole(getUserRole())
                .subMenu(subMenu)
                .createdAt(getTimestamp())
                .build();
    }

    public static UserRoleMenuRequest getUserRoleMenuRequest() {
        return UserRoleMenuRequest.builder()
                .userRoleId(1L)
                .menuIds(List.of(getUserRoleSubMenuRequest()))
                .build();
    }

    public static UserRoleSubMenuRequest getUserRoleSubMenuRequest() {
        return UserRoleSubMenuRequest.builder()
                .menuId(1L)
                .subMenuIds(List.of(1L, 2L))
                .build();
    }

    public static UserRoleMenuResponse getUserRoleMenuResponse() {
        return UserRoleMenuResponse.builder()
                .userRoleId(1L)
                .name("admin")
                .menus(List.of(getMenuResponse()))
                .build();
    }

    public static FileRequest getFileRequest() {
        return FileRequest.builder()
                .directoryName("test")
                .fileName("test.png")
                .fileBytes("string".getBytes())
                .build();
    }

    public static FileResponse getFileResponse() {
        return FileResponse.builder()
                .fileBytes("string".getBytes())
                .fileName("test.png")
                .mediaType(MediaType.IMAGE_PNG)
                .build();
    }

    public static StaffSearchDto getStaffSearchDto() {
        return StaffSearchDto.builder()
                .divisionId(1L)
                .isHead(null)
                .search("")
                .isDeleted(false)
                .page(1)
                .size(10)
                .build();
    }

    public static MStaff getStaff() {
        return MStaff.builder()
                .id(1L)
                .name("staff name")
                .position("president")
                .isHead(true)
                .photo("file.png")
                .quote("staff quote")
                .funFact("staff fun fact")
                .description("staff desc")
                .jobDescription("staff job desc")
                .division(getDivision())
                .build();
    }

    public static StaffRequest getStaffRequest() {
        return StaffRequest.builder()
                .name("staff name")
                .position("president")
                .isHead(true)
                .photo(getFileUploadRequest())
                .quote("staff quote")
                .funFact("staff fun fact")
                .description("staff desc")
                .jobDescription("staff job desc")
                .divisionId(1L)
                .build();
    }

    public static StaffResponse getStaffResponse() {
        return StaffResponse.builder()
                .id(1L)
                .name("staff name")
                .position("president")
                .isHead(true)
                .photo("file.png")
                .quote("staff quote")
                .funFact("staff fun fact")
                .description("staff desc")
                .jobDescription("staff job desc")
                .division(getDivisionResponse())
                .build();
    }

    public static MNewsletter getNewsletter() {
        return MNewsletter.builder()
                .id(1L)
                .slug("test")
                .title("test")
                .description("test desc")
                .cover("file.png")
                .content("file.png")
                .build();
    }

    public static NewsletterRequest getNewsletterRequest() {
        return NewsletterRequest.builder()
                .title("test")
                .description("test desc")
                .cover(getFileUploadRequest())
                .content(getFileUploadRequest())
                .build();
    }

    public static NewsletterResponse getNewsletterResponse() {
        return NewsletterResponse.builder()
                .id(1L)
                .slug("test")
                .title("test")
                .description("test desc")
                .cover("file.png")
                .content("file.png")
                .build();
    }

    public static MEvent getEvent() {
        return MEvent.builder()
                .id(1L)
                .slug("test")
                .title("test")
                .description("test desc")
                .cover("file.png")
                .startDate(LocalDate.of(2024, 10, 10))
                .endDate(LocalDate.of(2024, 11, 10))
                .startTime(LocalTime.of(14, 30, 0))
                .endTime(LocalTime.of(15, 30, 0))
                .build();
    }

    public static EventRequest getEventRequest() {
        return EventRequest.builder()
                .title("test")
                .description("test desc")
                .cover(getFileUploadRequest())
                .startDate(LocalDate.of(2024, 10, 10))
                .endDate(LocalDate.of(2024, 11, 10))
                .startTime(LocalTime.of(14, 30, 0))
                .endTime(LocalTime.of(15, 30, 0))
                .build();
    }

    public static EventResponse getEventResponse() {
        return EventResponse.builder()
                .id(1L)
                .slug("test")
                .title("test")
                .description("test desc")
                .cover("file.png")
                .startDate(LocalDate.of(2024, 10, 10))
                .endDate(LocalDate.of(2024, 11, 10))
                .startTime(LocalTime.of(14, 30, 0))
                .endTime(LocalTime.of(15, 30, 0))
                .duration("1 months ago")
                .build();
    }

    public static GallerySearchDto getGallerySearchDto() {
        return GallerySearchDto.builder()
                .eventId(1L)
                .search("")
                .isDeleted(false)
                .page(1)
                .size(10)
                .build();
    }

    public static MGallery getGallery() {
        return MGallery.builder()
                .id(1L)
                .fileName("file.png")
                .event(getEvent())
                .build();
    }

    public static GalleryRequest getGalleryRequest() {
        return GalleryRequest.builder()
                .fileUploads(List.of(getFileUploadRequest()))
                .eventId(1L)
                .build();
    }

    public static GalleryResponse getGalleryResponse() {
        return GalleryResponse.builder()
                .id(1L)
                .fileName("file.png")
                .event(getEventResponse())
                .build();
    }

    public static FileUploadRequest getFileUploadRequest() {
        return FileUploadRequest.builder()
                .fileBase64("aGVsbG8=")
                .fileName("file.png")
                .build();
    }

    public static StaffDivisionResponse getStaffDivisionResponse() {
        return StaffDivisionResponse.builder()
                .id(1L)
                .name("division-name")
                .build();
    }

    public static MSetting getSetting() {
        return MSetting.builder()
                .id(1L)
                .logo("file.png")
                .banner("file.png")
                .qrCode("file.png")
                .instagram("instagram")
                .tiktok("tiktok")
                .linkedin("linkedin")
                .youtube("youtube")
                .supportAccountName("support")
                .supportAccountNumber("number")
                .supportShortCode("short")
                .contactEmail("ppi@gmail.com")
                .contactPhoneNumber("phone number")
                .periodActive(getPeriod())
                .eventGallery(getEvent())
                .build();
    }

    public static SettingRequest getSettingRequest() {
        return SettingRequest.builder()
                .logo(getFileUploadRequest())
                .banner(getFileUploadRequest())
                .qrCode(getFileUploadRequest())
                .instagram("instagram")
                .tiktok("tiktok")
                .linkedin("linkedin")
                .youtube("youtube")
                .supportAccountName("support")
                .supportAccountNumber("number")
                .supportShortCode("short")
                .contactEmail("ppi@gmail.com")
                .contactPhoneNumber("phone number")
                .periodActiveId(1L)
                .eventGalleryId(1L)
                .build();
    }

    public static SettingResponse getSettingResponse() {
        return SettingResponse.builder()
                .id(1L)
                .logo("file.png")
                .banner("file.png")
                .qrCode("file.png")
                .instagram("instagram")
                .tiktok("tiktok")
                .linkedin("linkedin")
                .youtube("youtube")
                .supportAccountName("support")
                .supportAccountNumber("number")
                .supportShortCode("short")
                .contactEmail("ppi@gmail.com")
                .contactPhoneNumber("phone number")
                .periodActive(getPeriodResponse())
                .eventGallery(getEventResponse())
                .build();
    }

    public static MStudent getStudent() {
        return MStudent.builder()
                .id(1L)
                .name("student")
                .education("student@gmail.com")
                .photo("file.png")
                .major("major")
                .education("education")
                .graduation("graduation")
                .birthDate(LocalDate.of(1991, 10, 10))
                .gender(getSystemParameterList())
                .build();
    }

    public static StudentRequest getStudentRequest() {
        return StudentRequest.builder()
                .name("student")
                .education("student@gmail.com")
                .photo(getFileUploadRequest())
                .major("major")
                .education("education")
                .graduation("graduation")
                .birthDate(LocalDate.of(1991, 10, 10))
                .genderId(1L)
                .build();
    }

    public static StudentResponse getStudentResponse() {
        return StudentResponse.builder()
                .id(1L)
                .name("student")
                .education("student@gmail.com")
                .photo("file.png")
                .major("major")
                .education("education")
                .graduation("graduation")
                .birthDate(LocalDate.of(1991, 10, 10))
                .gender(getSystemParameterListResponse())
                .build();
    }

    public static DashboardResponse getDashboardResponse() {
        return DashboardResponse.builder()
                .totalStudent(10L)
                .totalEvent(10L)
                .totalNewsletter(10L)
                .totalGallery(10L)
                .build();
    }
}
