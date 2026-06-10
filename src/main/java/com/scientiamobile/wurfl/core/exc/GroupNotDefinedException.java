package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * Exception thrown when group not defined occurs.
 */

public class GroupNotDefinedException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String groupId;

    public GroupNotDefinedException(String groupId, String message) {
        super(message);
        this.groupId = groupId;
    }

    public GroupNotDefinedException(String groupId) {
        this(groupId, "Group: " + groupId + " is not defined in WURFL");
    }

    /**
     * Returns the grou pd.
     */

    public String getGroupId() {
        return this.groupId;
    }
}
