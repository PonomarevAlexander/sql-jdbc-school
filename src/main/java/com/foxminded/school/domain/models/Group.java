package com.foxminded.school.domain.models;

public class Group {
    
    private int groupID;
    private String groupName;
    private int groupSize;
    
    public Group(String groupName) {
        this.groupName = groupName;
    }
    
    public Group() {}
    
    public int getGroupID() {
        return groupID;
    }
    
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    } 
}
