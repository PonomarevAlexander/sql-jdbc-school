package com.foxminded.school.domain.models;

public class Group {
    
    private int id;
    private String name;
    private int groupSize;
    
    public Group(String groupName) {
        this.name = groupName;
    }
    
    public Group() {}
    
    public int getGroupID() {
        return id;
    }
    
    public void setGroupID(int groupID) {
        this.id = groupID;
    }
    
    public String getGroupName() {
        return name;
    }
    
    public void setGroupName(String groupName) {
        this.name = groupName;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    } 
}
