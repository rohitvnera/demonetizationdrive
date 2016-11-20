package com.ampdev.platform.service;

import com.google.api.client.util.Lists;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

/**
 * Created by Avi on 7/2/16.
 */
public class MainClass {

    public static void main(String[] args) {

        // 30 bits
        get(39, -41, 30);

        // 30 bits
        get(10, -17, 30);

    }

    static int getMax(int bits) {
        BigInteger value = BigInteger.ZERO
                .setBit(bits)
                .subtract(BigInteger.ONE);
        return value.intValue();
    }

    private static void get(int a, int b, int bits) {
        List<Integer> sols = Lists.newArrayList();
        int l = getMax(bits);
        for (int i = 1; i < l; i++) {
            int j = (i % l);
            if ((a * j == b * j)) {
                sols.add(i);
            }
        }
        Collections.sort(sols);
    }

    private static void test() throws FileNotFoundException {
        String path = "/Users/Avi/my/test/";
        String file = "avi.txt";
        final PrintWriter writer = new PrintWriter(new File(path, file));
        writer.println("avinash");
        writer.close();
    }

    private static void testSubstring() {
        final String path = "/Users/Avi/my/repo/joyas/backend/joyas/joyas/src/main/java/com/ampdev/platform/service/.MainClass.java.jln_hide_image";
        String name = FilenameUtils.getBaseName(path);
        final String onlyPath = FilenameUtils.getPath(path);
    }

    public static String getHidePath(String pathString) {
        if (pathString != null) {
            int lastIndex = pathString.lastIndexOf('/');

            return pathString.substring(0, lastIndex) + "/." + pathString.substring(lastIndex + 1);
        }
        return "";
    }

}
