package com.ssafy.square4us.api.mvc.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmptyDto {
    private boolean isEmpty;

    public EmptyDto(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
