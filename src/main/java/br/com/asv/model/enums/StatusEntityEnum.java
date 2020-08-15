package br.com.asv.model.enums;

import lombok.Getter;

public enum StatusEntityEnum {
    DISABLED(0),
    ENABLED(1);

    @Getter
    private int value;

    StatusEntityEnum(int value) {
        this.value = value;
    }
}
