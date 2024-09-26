package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.SystemParameterListRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.dto.search.SystemParameterListSearchDto;

public interface SystemParameterListService extends CrudService<SystemParameterListRequest, SystemParameterListResponse, SystemParameterListSearchDto> {
}
