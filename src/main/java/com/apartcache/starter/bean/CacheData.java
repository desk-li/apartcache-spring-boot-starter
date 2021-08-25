package com.apartcache.starter.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by desk
 *
 * @date 2021/8/23
 */
public class CacheData implements Serializable {
    private static final long serialVersionUID = -4137023852429540956L;
    Long id;
    String cacheName;
    String clazz;
    String method;
    String params;
    LocalStatus status;
    String creator;
    Date createTime;
    String updater;
    Date updateTime;

    @Override
    public String toString() {
        return "CacheData{" +
                "id=" + id +
                ", cacheName='" + cacheName + '\'' +
                ", clazz='" + clazz + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", status=" + status +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", updater='" + updater + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public LocalStatus getStatus() {
        return status;
    }

    public void setStatus(LocalStatus status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public enum LocalStatus{
        UNACTIVE("未启用"),//未启用，启用后是RUNNING,然后再停止是STOPED
        EDITING("编辑中"),//编辑中，暂存
        RUNNING("运行中"),//运行中
        STOPED("已停止"),//已停止
        DELETED("已删除");//已删除

        String status;

        public String getStatus() {
            return status;
        }

        LocalStatus(String status) {
            this.status = status;
        }
    }
}
