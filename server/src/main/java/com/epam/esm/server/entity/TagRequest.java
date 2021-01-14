package com.epam.esm.server.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TagRequest {

    @Positive(message = "{notPositive}")
    private Integer id;
    @NotEmpty(message = "{empty}")
    @Size(min = 2, max = 50, message = "{invalidLength}")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagRequest that = (TagRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
