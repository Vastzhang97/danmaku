package com.niit.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "danmaku", catalog = "")
public class UserEntity {
    private int uid;
    private String uname;
    private String password;
    private String headshot;
    private int level;
    private int privilege;
    private int status;
    @Transient
    private int hasUnRead;
    @Transient
    public int getHasUnRead() {
        return hasUnRead;
    }

    public void setHasUnRead(int hasUnRead) {
        this.hasUnRead = hasUnRead;
    }

    @Id
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "uname")
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "headshot")
    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    @Basic
    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Basic
    @Column(name = "privilege")
    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return uid == that.uid &&
                level == that.level &&
                privilege == that.privilege &&
                status == that.status &&
                Objects.equals(uname, that.uname) &&
                Objects.equals(password, that.password) &&
                Objects.equals(headshot, that.headshot);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, uname, password, headshot, level, privilege, status);
    }
}
