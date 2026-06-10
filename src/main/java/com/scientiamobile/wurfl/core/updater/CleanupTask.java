package com.scientiamobile.wurfl.core.updater;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 更新管线结束后的清理任务。
 * <p>无论更新成功还是失败，此任务都会在管线执行完毕后被调用，
 * 负责删除临时下载文件和备份文件，并清空上下文数据以释放内存。</p>
 */

public class CleanupTask implements UpdatePipelineTask {
    private static final Logger log = LoggerFactory.getLogger(CleanupTask.class);

    /**
     * 执行清理操作。
     * <p>从上下文中读取备份文件路径和临时文件路径，逐个删除这些临时文件，
     * 最后清空整个上下文 Map。</p>
     *
     * @param context 管线执行上下文 Map
     */
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
