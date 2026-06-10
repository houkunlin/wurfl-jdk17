package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

import java.io.Serial;

/**
 * WURFL 数据一致性校验异常的抽象基类。
 * <p>在加载和合并 WURFL 数据后，对设备、功能点（Capability）、分组（Group）
 * 以及设备继承关系进行一致性校验时，如发现任何不符合规则的情况，则抛出此类异常。</p>
 */

public abstract class WURFLConsistencyException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    protected WURFLConsistencyException() {
        super("WURFL consistency exception");
    }

    protected WURFLConsistencyException(Throwable cause) {
        super("WURFL consistency exception", cause);
    }

    protected WURFLConsistencyException(String message) {
        super(message);
    }

    protected WURFLConsistencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
