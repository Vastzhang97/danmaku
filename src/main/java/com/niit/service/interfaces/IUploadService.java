package com.niit.service.interfaces;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface IUploadService {

    String uploadHeadshot(MultipartFile file, HttpServletRequest request) throws IOException;

    int uploadVideo(HttpServletRequest request) throws Exception;
}
