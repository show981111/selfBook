package com.example.selfbook.Data;

import java.util.ArrayList;
import java.util.List;

public class templateTreeNode {
    private userAnswer data = null;
    private List<templateTreeNode> children = new ArrayList<>();


    public templateTreeNode(userAnswer data) {
        this.data = data;

    }

    public void addChild(templateTreeNode child) {
        this.children.add(child);
    }

    public void addChild(userAnswer data) {
        templateTreeNode newChild = new templateTreeNode(data);
        children.add(newChild);
    }

    public void addChildren(List<templateTreeNode> children) {
        this.children.addAll(children);
    }

    public List<templateTreeNode> getChildren() {
        return children;
    }

    public userAnswer getData() {
        return this.data;
    }

    public void setData(userAnswer data) {
        this.data = data;
    }


}
