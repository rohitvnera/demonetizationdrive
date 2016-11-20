package com.ampdev.platform.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Avi on 5/27/16.
 */
public class MultipartFileUploader {

    public static void main(String[] args) {
        String charset = "UTF-8";
        File uploadFile1 = new File("/Users/Avi/my/photos/wedding/fwd/DSC_8541.JPG");
        File uploadFile2 = new File("/Users/Avi/my/photos/wedding/fwd/DSC_8542.JPG");
        String requestURL = "http://localhost:8080/joyas/ws/file/upload/";

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");

            multipart.addFormField("name", "test");
            multipart.addFilePart("file", uploadFile1);
       //     multipart.addFilePart("fileUpload", uploadFile2);

            List<String> response = multipart.finish();


            for (String line : response) {
            }
        } catch (IOException ex) {
        }
    }
}