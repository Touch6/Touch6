package com.touch6.core.exception;

/**
 * 异常码操作工具类
 *
 */
public class ECodeUtil {
    /**
     * 代码异常
     */
    private static ECode eCode_comm;
    /**
     * 接口异常
     */
    private static ECode eCode_interface;

    public static ECode geteCode_comm() {
        return eCode_comm;
    }

    public static void seteCode_comm(ECode eCode_comm) {
        ECodeUtil.eCode_comm = eCode_comm;
    }

    public static ECode geteCode_interface() {
        return eCode_interface;
    }

    public static void seteCode_interface(ECode eCode_interface) {
        ECodeUtil.eCode_interface = eCode_interface;
    }

    /**
     * 获取代码异常
     *
     * @param key
     * @return
     */
    public static Error getCommError(String key) {
        return eCode_comm.getError(key);
    }

    /**
     * 获取接口异常
     *
     * @param key
     * @return
     */
    public static Error getInterfaceError(String key) {
        return eCode_interface.getError(key);
    }

}
