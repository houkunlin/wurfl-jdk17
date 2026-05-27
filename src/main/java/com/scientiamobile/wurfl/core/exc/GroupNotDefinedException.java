package com.scientiamobile.wurfl.core.exc;

public class GroupNotDefinedException extends WURFLRuntimeException {
    private static final long serialVersionUID = 1L;
    private final String groupId;

    public GroupNotDefinedException(String groupId, String message) {
        super(message);
        this.groupId = groupId;
    }

    public GroupNotDefinedException(String groupId) {
        this(groupId, "Group: " + groupId + " is not defined in WURFL");
    }

    public String getGroupId() {
        return this.groupId;
    }
}
