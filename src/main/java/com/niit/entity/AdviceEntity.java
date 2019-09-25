package com.niit.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "advice", schema = "danmaku", catalog = "")
public class AdviceEntity {
    private int id;
    private String name;
    private String contact;
    private String content;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdviceEntity that = (AdviceEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, contact, content);
    }

    @Override
    public String toString() {
        return "AdviceEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
