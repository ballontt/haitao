package com.haitao.service.imp;

import com.haitao.pojo.PictureResult;
import com.haitao.service.PictureService;
import com.haitao.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ballontt on 2017/1/26.
 */
@Service
public class PictureServiceImp implements PictureService {
    @Value("${IMAGE_SERVER_IP}")
    private String image_server_ip;

    @Override
    public PictureResult uploadPicture(MultipartFile picFile) {
        PictureResult pictureResult = new PictureResult();
        if(picFile.isEmpty()) {
            pictureResult.setError(1);
            pictureResult.setMsg("图片为空");
            return pictureResult;
        }
        else{
            //获取图片的名称
            String picName = picFile.getOriginalFilename();
            //获取图片的类型后缀
            String extName = picName.substring(picName.lastIndexOf(".")+1);
            try {
                //读取配置文件
                FastDFSClient fastDFSClient = new FastDFSClient("properties/fastdfs_client.conf");
                //上传图片并获得图片在图片服务器上URL
                String picUrl = fastDFSClient.uploadFile(picFile.getBytes(),extName);
                picUrl = image_server_ip + picUrl;
                //把Url响应给客户端
                pictureResult.setError(0);
                pictureResult.setUrl(picUrl);

            } catch (Exception e) {
                e.printStackTrace();
                pictureResult.setError(1);
                pictureResult.setMsg("图片上传失败");
            }
            return pictureResult;
        }
    }
}
