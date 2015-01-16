/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.admin;

import java.util.LinkedList;
import java.util.List;

public class TreeViewNode {

    private String label;
    private int id;
    private List<TreeViewNode> children;

    public TreeViewNode(String label, int id) {
        this.label = label;
        this.id = id;
        this.children = new LinkedList<TreeViewNode>();
    }
    
    public TreeViewNode(String label, int id, List<TreeViewNode> children) {
        this.label = label;
        this.id = id;
        this.children = children;
    }
    
    public void addChild(TreeViewNode child) {
        this.children.add(child);
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public List<TreeViewNode> getChildren() {
        return children;
    }

}
