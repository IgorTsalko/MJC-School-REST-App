package com.epam.esm.server.entity;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class TagResponse extends RepresentationModel<TagResponse> {

    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public TagResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TagResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagResponse that = (TagResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
