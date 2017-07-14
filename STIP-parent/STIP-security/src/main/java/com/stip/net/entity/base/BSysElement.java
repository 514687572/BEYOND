package com.stip.net.entity.base;

import com.stip.mybatis.generator.plugin.BaseModel;
import java.io.Serializable;

public class BSysElement extends BaseModel<Long> implements Serializable {
    private Long eleId;

    private String eleName;

    private Long parentId;

    private String eleCode;

    private Long eleIndex;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getEleId() {
        return eleId;
    }

    public void setEleId(Long eleId) {
        this.eleId = eleId;
    }

    public String getEleName() {
        return eleName;
    }

    public void setEleName(String eleName) {
        this.eleName = eleName == null ? null : eleName.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getEleCode() {
        return eleCode;
    }

    public void setEleCode(String eleCode) {
        this.eleCode = eleCode == null ? null : eleCode.trim();
    }

    public Long getEleIndex() {
        return eleIndex;
    }

    public void setEleIndex(Long eleIndex) {
        this.eleIndex = eleIndex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eleId=").append(eleId);
        sb.append(", eleName=").append(eleName);
        sb.append(", parentId=").append(parentId);
        sb.append(", eleCode=").append(eleCode);
        sb.append(", eleIndex=").append(eleIndex);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BSysElement other = (BSysElement) that;
        return (this.getEleId() == null ? other.getEleId() == null : this.getEleId().equals(other.getEleId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEleId() == null) ? 0 : getEleId().hashCode());
        return result;
    }
}