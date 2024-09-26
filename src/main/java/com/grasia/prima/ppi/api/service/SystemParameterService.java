package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SystemParameterRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SystemParameterService extends CrudService<SystemParameterRequest, SystemParameterResponse, SearchDto> {
}
