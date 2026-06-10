package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * 覆盖原始 WURFL 文件并执行一致性检查的管线任务。
 * <p>将下载好的临时 WURFL 文件复制到原始路径，然后使用新的 WURFL 文件重新初始化引擎
 * 并加载数据进行一致性验证。如果加载失败（例如文件损坏），任务会标记为失败，
 * 触发后续的回滚操作。</p>
 */

public class OverwriteAndCheckConsistencyTask implements UpdatePipelineTask {
    private static final Logger log = LoggerFactory.getLogger(OverwriteAndCheckConsistencyTask.class);
    /**
     * 可选的补丁文件路径列表，用于在加载新 WURFL 文件时应用补丁
     */
    private String[] patchPaths;

    public OverwriteAndCheckConsistencyTask() {
    }

    public OverwriteAndCheckConsistencyTask(String[] patchPaths) {
        this.patchPaths = patchPaths;
    }

    /**
     * 执行文件覆盖和一致性检查。
     * <p>从上下文中获取临时文件路径和原始文件路径，先删除原始文件再复制临时文件覆盖。
     * 然后使用新文件创建引擎并调用 {@code load()} 方法验证文件有效性。</p>
     *
     * @param context 管线执行上下文 Map
     */
    public void execute(Map<String, Object> context) {
        String newWurflTempPath;
        newWurflTempPath = (String) context.get("new_wurfl_temp_path");
        Validate.notEmpty(newWurflTempPath);
        String originalWurflPath = (String) context.get("original_wurfl_path");

        try {
            if (!(new File(originalWurflPath).getCanonicalFile()).delete()) {
                log.warn("Failed to delete original WURFL file before overwriting");
            }
            FileUtils.copyFile(new File(newWurflTempPath).getCanonicalFile(), new File(originalWurflPath).getCanonicalFile(), true);
            context.put("original_wurfl_overwritten", "true");
            GeneralWURFLEngine newEngine;
            if (ArrayUtils.isEmpty(this.patchPaths)) {
                newEngine = new GeneralWURFLEngine(originalWurflPath);
            } else {
                newEngine = new GeneralWURFLEngine(originalWurflPath, this.patchPaths);
            }

            newEngine.load();
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
        } catch (Throwable e) {
            log.error("WURFL consistency check failed", e);
            context.put("task_error_message", "Error trying to overwrite WURFL file : " + ExceptionUtils.getFirstAvailableMessage(e));
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
        }
    }
}
