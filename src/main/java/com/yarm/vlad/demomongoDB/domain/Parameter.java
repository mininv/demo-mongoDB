package com.yarm.vlad.demomongoDB.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Parameter {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return Objects.equals(value, parameter.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
