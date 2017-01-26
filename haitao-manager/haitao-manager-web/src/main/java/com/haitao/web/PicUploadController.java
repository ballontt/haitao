package com.haitao.web;

import com.haitao.pojo.PictureResult;
import com.haitao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ballontt on 2017/1/26.
 */
@Controller
public class PicUploadController {
    @Autowired
    private PictureService pictureService;

    //上传图片
    @RequestMapping(value="/picture/upload",method = RequestMethod.POST)
    @ResponseBody
    public PictureResult uploadPicture(MultipartFile picFile) {
        PictureResult pictureResult = pictureService.uploadPicture(picFile);
        return pictureResult;
    }
}
