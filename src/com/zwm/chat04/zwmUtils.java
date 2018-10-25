package com.zwm.chat04;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author zhangweiming
 * @version V1.0
 * @className zwmUtils
 * @description //TODO 工具类
 * @date 4:21 PM 2018/10/25
 */
public class zwmUtils {
    /**
     * @param [targets]
     * @return void
     * @methodName close
     * @description //TODO 释放资源
     * @author zhangweiming
     * @date 4:22 PM 2018/10/25
     */
    public static void close(Closeable... targets) {
        for (Closeable target : targets) {
            if (null != target) {
                try {
                    target.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
