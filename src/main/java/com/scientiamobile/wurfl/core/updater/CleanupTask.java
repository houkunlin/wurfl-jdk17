package com.scientiamobile.wurfl.core.updater;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * A task that performs Cleanup.
 */

public class CleanupTask implements UpdatePipelineTask {
    private static final Logger log = LoggerFactory.getLogger(CleanupTask.class);

    public void execute(Map<String, Object> context) {
        String[] pathsToDelete;
        pathsToDelete = new String[]{(String) context.get("backup_wurfl_path"), (String) context.get("new_wurfl_temp_path")};
        if (ArrayUtils.isNotEmpty(pathsToDelete)) {
            for (int i = 0; i < 2; ++i) {
                String path;
                path = pathsToDelete[i];
                if (StringUtils.isNotEmpty(path)) {
                    try {
                        File file = new File(path).getCanonicalFile();
                        if (file.exists() && !file.delete()) {
                            log.warn("Failed to delete file: {}", file.getAbsolutePath());
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }

        context.clear();
    }
}
