package com.haitao.utils;

import java.util.Random;

/**
 * Created by ballontt on 2017/2/23.
 */
public class IDUtils {

    public static long getItemId() {
        long mills = System.currentTimeMillis();
        Random random = new Random();
        int end2 = random.nextInt(99);
        String str = mills + String.format("%02d",end2);
        long id = new Long(str);
        return id;
    }
}
