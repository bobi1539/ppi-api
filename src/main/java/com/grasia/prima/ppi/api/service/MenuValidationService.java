package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.entity.MMenu;

public interface MenuValidationService {

    void validateCreateSequence(Integer sequence);

    void validateUpdateSequence(MMenu menuExisting, Integer sequence);
}
