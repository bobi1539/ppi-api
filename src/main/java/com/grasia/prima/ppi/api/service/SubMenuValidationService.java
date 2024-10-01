package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.entity.MSubMenu;

public interface SubMenuValidationService {

    void validateCreateSequence(Integer sequence, MMenu menu);

    void validateUpdateSequence(MSubMenu subMenuExisting, Integer sequence, MMenu menu);
}
