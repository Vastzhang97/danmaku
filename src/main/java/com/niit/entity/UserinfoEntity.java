package com.niit.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userinfo", schema = "danmaku", catalog = "")
public class UserinfoEntity {
    private int uiid;
    private Integer uid;
    private Integer fid;

    @Id
    @Column(name = "uiid")
    public int getUiid() {
        return uiid;
    }

    public void setUiid(int uiid) {
        this.uiid = uiid;
    }

    @Basic
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "fid")
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserinfoEntity that = (UserinfoEntity) o;
        return uiid == that.uiid &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(fid, that.fid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uiid, uid, fid);
    }
}
