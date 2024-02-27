package models;

import lombok.Data;

@Data
public class ApiVariant {

    private int variantNumber;

    public ApiVariant(int variantNumber) {
        this.variantNumber = variantNumber;
    }
}
