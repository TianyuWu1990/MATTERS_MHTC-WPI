/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.admin;

public class AdminMetric {

    private int id;
    private String name;
    private String type;
    private boolean visible;
    private boolean calculated;

    public AdminMetric(int id, String name, String type, boolean visible, boolean calculated) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.visible = visible;
        this.calculated = calculated;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isCalculated() {
        return calculated;
    }

}
