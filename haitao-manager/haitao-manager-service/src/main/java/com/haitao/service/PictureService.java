package com.haitao.service;

import com.haitao.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ballontt on 2017/1/26.
 */
public interface PictureService {
    PictureResult uploadPicture(MultipartFile picFile);
}
