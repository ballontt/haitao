package com.haitao.entity;

import java.util.Date;

/**
 * Created by ballontt on 2017/2/27.
 */
public class TbItemParameter {
    private long id;
    private long itemCatId;
    private String paramData;
    private Date created;
    private Date updated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemCatId() {
        return itemCatId;
    }

    public void setItemCatId(long itemCatId) {
        this.itemCatId = itemCatId;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "TbItemParameter{" +
                "id=" + id +
                ", itemCatId=" + itemCatId +
                ", paramData='" + paramData + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
