package com.platform.mesh.utils.spring;

import com.platform.mesh.core.constants.SystemConst;

public class SysOSUtil {


    /**
     * 功能描述:
     * 〈获取操作系统名称〉
     * @return 正常返回:{@link String} os.name 属性值
     * @author 蝉鸣
     */
    public static String getOsName() {
        return System.getProperty(SystemConst.SYSTEM_OS);
    }

    /**
     * 功能描述:
     * 〈判断操作系统是否是 Windows〉
     * @return 正常返回:{@link boolean} true：操作系统是 Windows false：其它操作系统
     * @author 蝉鸣
     */
    public static boolean isWindows() {
        //获取当前系统名称
        String osName = getOsName();
        //判断系统类型
        return osName != null && osName.startsWith(SystemConst.WINDOWS);
    }

    /**
     * 功能描述:
     * 〈判断操作系统是否是 MacOS〉
     * @return 正常返回:{@link boolean} true：操作系统是 MacOS false：其它操作系统
     * @author 蝉鸣
     */
    public static boolean isMacOs() {
        //获取当前系统名称
        String osName = getOsName();
        //判断系统类型
        return osName != null && osName.startsWith(SystemConst.MAC);
    }

    /**
     * 功能描述:
     * 〈判断操作系统是否是 Linux〉
     * @return 正常返回:{@link boolean} true：操作系统是 Linux false：其它操作系统
     * @author 蝉鸣
     */
    public static boolean isLinux() {
        //获取当前系统名称
        String osName = getOsName();
        //判断系统类型
        return (osName != null && osName.startsWith(SystemConst.LINUX)) || (!isWindows() && !isMacOs());
    }

}
