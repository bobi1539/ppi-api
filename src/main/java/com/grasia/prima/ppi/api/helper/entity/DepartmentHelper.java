package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.DepartmentResponse;
import com.grasia.prima.ppi.api.entity.MDepartment;
import com.grasia.prima.ppi.api.exception.BusinessException;

public class DepartmentHelper {

    private DepartmentHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static DepartmentResponse toDepartmentResponse(MDepartment department) {
        DepartmentResponse response = DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, department);
        return response;
    }
}
