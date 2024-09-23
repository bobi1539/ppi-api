package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.SearchDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.helper.ObjectDummy;

public abstract class ServiceTest {

    protected final Long id = 1L;
    protected SearchDto searchDto = ObjectDummy.getSearchDto();
    protected PageDto pageDto = ObjectDummy.getPageDto();
    protected HeaderRequest header = ObjectDummy.getHeaderRequest();
}
