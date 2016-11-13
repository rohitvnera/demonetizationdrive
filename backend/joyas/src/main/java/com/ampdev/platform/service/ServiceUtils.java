package com.ampdev.platform.service;

import com.ampdev.platform.module.common.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Avi on 5/27/16.
 */
public final class ServiceUtils {

    private static final String CONFIG_PROPERTY_LOCATION = "config.properties";
    private static final String rootDir = "root.dir";
    private static final Properties _props = new Properties();

    static {

        try (InputStream is = ServiceUtils.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTY_LOCATION)) {
            _props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String getFileRootDir() {
        String root = _props.getProperty(rootDir);
        if (Util.isEmpty(root)) {
            throw new RuntimeException("Root directory is not set in config file: " + CONFIG_PROPERTY_LOCATION);
        }

        if (root.endsWith("/")) {
            root = root.substring(0, root.length() - 1);
            _props.setProperty(rootDir, root);
        }
        return root;
    }
}
