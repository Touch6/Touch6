package com.touch6.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 自定义异常
 *
 */
public class CoreException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CoreException.class);
    /**
     * 异常编码
     */
    int code;
    /**
     * 异常信息
     */
    Error error;
    /**
     * 异常信息
     */
    String exceptionInfo;

    /**
     * 获取错误码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置错误码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取异常信息
     */
    public String getExceptionInfo() {
        return exceptionInfo;
    }

    /**
     * 设置异常信息
     */
    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    /**
     * 记录异常位置信息
     */
    private void logExcption(Throwable cause) {
        StackTraceElement element = getStackTrace()[0];
        if (element == null)
            return;
        exceptionInfo = "code=" + code + ",class=" + element.getClassName()
                + ",method=" + element.getMethodName() + ",file="
                + element.getFileName() + ",line=" + element.getLineNumber();
        logger.error(exceptionInfo);
        // TODO 打印堆栈信息
        if (cause != null) {
            printStackTrace();
        }
    }

    /**
     * 记录异常位置信息
     */
    private void logError(Throwable cause) {
        StackTraceElement element = getStackTrace()[0];
        if (element == null)
            return;
        exceptionInfo = "error=" + error + ",class=" + element.getClassName()
                + ",method=" + element.getMethodName() + ",file="
                + element.getFileName() + ",line=" + element.getLineNumber();
        logger.error(exceptionInfo);
        // TODO 打印堆栈信息
        if (cause != null) {
            printStackTrace();
        }
    }

    /**
     * 推荐使用构造方法
     */
    public CoreException(Error error) {
        super((error == null) ? null : error.getInfo());
        if (error != null) {
            code = error.getCode();
        }
        this.error = error;
        logError(null);
    }

    /**
     * 推荐使用构造方法
     */
    public CoreException(Error error, Throwable cause) {
        super(((error == null) ? (cause == null ? "" : cause.getMessage())
                : error.getInfo()), cause);
        if (error != null) {
            code = error.getCode();
        }
        this.error = error;
        logError(cause);
    }

    public CoreException(int code) {
        super();
        this.code = code;
        logExcption(null);
    }

    public CoreException(int code, String message) {
        super(message);
        this.code = code;
        logExcption(null);
    }

    public CoreException(Error code, String message) {
        super(message);
        this.code = code.getCode();
        this.error = code;
        logExcption(null);
    }

    public CoreException(int code, Throwable cause) {
        super(cause);
        this.code = code;
        logExcption(cause);
    }

    public CoreException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        logExcption(cause);
    }

    @Override
    public String toString() {
        return "CoreException [code=" + code + ", error=" + error
                + ", exceptionInfo=" + exceptionInfo + ", getMessage()="
                + getMessage() + "]";
    }

}
