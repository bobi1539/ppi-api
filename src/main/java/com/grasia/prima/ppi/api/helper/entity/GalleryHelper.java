package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.GalleryResponse;
import com.grasia.prima.ppi.api.entity.MGallery;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.util.Objects;

public final class GalleryHelper {

    private GalleryHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static GalleryResponse toGalleryResponse(MGallery gallery) {
        if (Objects.isNull(gallery)) {
            return null;
        }
        GalleryResponse response = GalleryResponse.builder()
                .id(gallery.getId())
                .fileName(gallery.getFileName())
                .event(EventHelper.toEventResponse(gallery.getEvent()))
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, gallery);
        return response;
    }
}
