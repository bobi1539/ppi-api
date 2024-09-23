package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.helper.ObjectDummy;

public abstract class ControllerTest {

    protected final Long id = 1L;
    protected final HeaderRequest header = ObjectDummy.getHeaderRequest();
}
