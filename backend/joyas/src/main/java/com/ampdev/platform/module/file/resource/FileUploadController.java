package com.ampdev.platform.module.file.resource;

import com.ampdev.platform.service.ServiceUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Avi on 5/27/16.
 */
@Controller
@RequestMapping(value = "/ws/file")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileUploadController {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<?> upload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        if (name.contains("/")) {
            return new ResponseEntity<>(String.format("Folder or relative paths not supported in name %s", name),
                    HttpStatus.BAD_REQUEST);
        }
        if (file.isEmpty()) {
            return new ResponseEntity<>(String.format("Failed to upload file %s as file is empty", name),
                    HttpStatus.BAD_REQUEST);
        }

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                new File(ServiceUtils.getFileRootDir() + "/" + name)))) {

            FileCopyUtils.copy(file.getInputStream(), stream);
            stream.close();

        } catch (Exception e) {
            new ResponseEntity<>("You failed to upload " + name + " => " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final String url = name;

        return new ResponseEntity<Object>(String.format("{\"url\": %s}", url), HttpStatus.OK);
    }
}




