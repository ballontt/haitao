package com.haitao.dto;

import com.haitao.entity.TbItemCat;

import java.util.List;

/**
 * Created by ballontt on 2017/2/13.
 */
public class ItemCatResult {
    private int label;
    private TbItemCat data;
    private List<ItemCatResult> children;

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public TbItemCat getData() {
        return data;
    }

    public void setData(TbItemCat data) {
        this.data = data;
    }

    public List<ItemCatResult> getChildren() {
        return children;
    }

    public void setChildren(List<ItemCatResult> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ItemCatResult{" +
                "label=" + label +
                ", data=" + data +
                ", children=" + children +
                '}';
    }
}
