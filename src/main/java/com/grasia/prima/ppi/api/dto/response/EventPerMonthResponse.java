package com.grasia.prima.ppi.api.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventPerMonthResponse {
    private int monthSequence;
    private String month;
    private long totalEvent;
}
