package com.scientiamobile.wurfl.core.updater;

import java.util.Map;

/**
 * A task that performs Update Pipeline.
 */

public interface UpdatePipelineTask {
    void execute(Map<String, Object> context);
}
