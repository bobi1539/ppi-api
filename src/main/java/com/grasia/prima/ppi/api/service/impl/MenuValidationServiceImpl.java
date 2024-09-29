package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.repository.MenuRepository;
import com.grasia.prima.ppi.api.service.MenuValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MenuValidationServiceImpl implements MenuValidationService {

    private final MenuRepository menuRepository;

    @Override
    public void validateCreateSequence(Integer sequence) {
        validateSequence(sequence);
        Optional<MMenu> menu = menuRepository.findBySequence(sequence);
        if (menu.isPresent()) {
            throw new BusinessException(GlobalMessage.MENU_SEQUENCE_HAS_BEEN_REGISTERED);
        }
    }

    @Override
    public void validateUpdateSequence(MMenu menuExisting, Integer sequence) {
        validateSequence(sequence);
        Optional<MMenu> menu = menuRepository.findBySequence(sequence);
        if (menu.isPresent() && !menu.get().getId().equals(menuExisting.getId())) {
            throw new BusinessException(GlobalMessage.MENU_SEQUENCE_HAS_BEEN_REGISTERED);
        }
    }

    private void validateSequence(Integer sequence) {
        if (sequence < 1) {
            throw new BusinessException(GlobalMessage.SEQUENCE_MUST_GREATER_THAN_ZERO);
        }
    }
}
