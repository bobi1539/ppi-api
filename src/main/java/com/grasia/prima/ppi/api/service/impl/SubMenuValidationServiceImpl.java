package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.entity.MSubMenu;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.repository.SubMenuRepository;
import com.grasia.prima.ppi.api.service.SubMenuValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SubMenuValidationServiceImpl implements SubMenuValidationService {

    private final SubMenuRepository subMenuRepository;

    @Override
    public void validateCreateSequence(Integer sequence, MMenu menu) {
        validateSequence(sequence);
        Optional<MSubMenu> subMenu = subMenuRepository.findBySequenceAndMenu(sequence, menu);
        if (subMenu.isPresent()) {
            throw new BusinessException(GlobalMessage.SUB_MENU_SEQUENCE_HAS_BEEN_REGISTERED);
        }
    }

    @Override
    public void validateUpdateSequence(MSubMenu subMenuExisting, Integer sequence, MMenu menu) {
        validateSequence(sequence);
        Optional<MSubMenu> subMenu = subMenuRepository.findBySequenceAndMenu(sequence, menu);
        if (subMenu.isPresent() && !subMenu.get().getId().equals(subMenuExisting.getId())) {
            throw new BusinessException(GlobalMessage.SUB_MENU_SEQUENCE_HAS_BEEN_REGISTERED);
        }
    }

    private void validateSequence(Integer sequence) {
        if (sequence < 1) {
            throw new BusinessException(GlobalMessage.SEQUENCE_MUST_GREATER_THAN_ZERO);
        }
    }
}
