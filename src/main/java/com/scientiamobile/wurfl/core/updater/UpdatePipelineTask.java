package com.scientiamobile.wurfl.core.updater;

import java.util.Map;

public interface UpdatePipelineTask {
    void execute(Map<String, Object> context);
}
