package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MGallery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GalleryResponse extends BaseEntityResponse {
    private Long id;
    private String fileName;
    private EventResponse event;

    public static GalleryResponse toResponse(MGallery gallery) {
        if (Objects.isNull(gallery)) {
            return null;
        }
        GalleryResponse response = builder()
                .id(gallery.getId())
                .fileName(gallery.getFileName())
                .event(EventResponse.toResponse(gallery.getEvent()))
                .build();
        BaseEntityResponse.setBaseEntity(response, gallery);
        return response;
    }
}
