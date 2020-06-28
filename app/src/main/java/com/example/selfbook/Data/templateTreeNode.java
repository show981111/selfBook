package com.example.selfbook.Data;

import java.util.ArrayList;
import java.util.List;

public class templateTreeNode {
    private Content data = null;
    private List<templateTreeNode> children = new ArrayList<>();


    public templateTreeNode(Content data) {
        this.data = data;

    }

    public void addChild(templateTreeNode child) {
        this.children.add(child);
    }

    public void addChild(Content data) {
        templateTreeNode newChild = new templateTreeNode(data);
        children.add(newChild);
    }

    public void addChildren(List<templateTreeNode> children) {
        this.children.addAll(children);
    }

    public List<templateTreeNode> getChildren() {
        return children;
    }

    public Content getData() {
        return this.data;
    }

    public void setData(Content data) {
        this.data = data;
    }


}
