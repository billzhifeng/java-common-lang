package com.github.java.common.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 通用的一些工具
 */
public final class CommonUtils {
    /**
     * 是否为字母
     * 
     * @param ch
     * @return
     */
    public static boolean isLetter(char ch) {
        return ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122;
    }

    /**
     * 打印异常的堆栈信息
     * 
     * @param exception 异常
     * @return 异常的堆栈信息
     */
    public static String printErrorTrace(Throwable exception) {
        StringBuffer errorString = new StringBuffer();
        errorString.append(exception.toString()).append("\n");
        StackTraceElement[] trace = exception.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
            errorString.append(trace[i]).append("\n");
        }
        return errorString.toString();
    }

    /**
     * @return
     */
    public static InetAddress getLocalHostAddress() {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements();) {
                NetworkInterface ni = nis.nextElement();
                if (ni.isLoopback() || ni.isVirtual() || !ni.isUp())
                    continue;
                for (Enumeration<InetAddress> ias = ni.getInetAddresses(); ias.hasMoreElements();) {
                    InetAddress ia = ias.nextElement();
                    if (ia instanceof Inet6Address)
                        continue;
                    return ia;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
