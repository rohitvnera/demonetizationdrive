package com.ampdev.platform.module.common.constants;

/**
 * Created by Avi on 3/27/16.
 */
public enum BinaryEnum {

    YES("1"), NO("0");

    private String value;

    BinaryEnum(String value) {
        this.value = value;
    }

    public BinaryEnum get(String value) {
        BinaryEnum[] enums = values();
        for (BinaryEnum be : enums) {
            if (value.equals(be.value)) {
                return be;
            }
        }
        throw new IllegalArgumentException(String.format("Enum for value %s is not supported", value));
    }
}
