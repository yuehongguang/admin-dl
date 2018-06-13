package com.igrowth.app.biz;

import com.github.wxiaoqi.security.api.entity.Media;
import com.github.wxiaoqi.security.api.entity.MediaType;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.igrowth.app.mapper.MediaMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by Jason on 16/6/30.
 */
@Service
public class MediaAdminBiz extends BaseBiz<MediaMapper, Media> {
    @Value("${fanta.oss.image.path:media/image/}")
    private String ossImagePath;

    @Value("${fanta.oss.audio.path:media/audio/}")
    private String ossAudioPath;

    @Value("${fanta.oss.video.path:media/video/}")
    private String ossVideoPath;

    @Value("${fanta.oss.thumbnail.path:media/thumbnail/}")
    private String ossThumbnailPath;

    @Value("${fanta.oss.file.path:media/file/}")
    private String ossFilePath;

    @Value("${fanta.oss.url:http://static.chinacnid.com/}")
    private String ossUrl;

    @Value("${fanta.oss.bucket:feimatch-static}")
    private String ossBucket;


    @Autowired
    private BravoBiz bravoBiz;

    /**
     * 上传文件到oss
     * @param file 文件
     * @param type 文件类型

     * @return 访问url

     */
    public String uploadToOss(MultipartFile file, String type) throws IOException {
        Integer mti = MediaType.getIntMediaType(MediaType.get(type));
        MediaType mt = MediaType.get(type);
        Media me = new Media();
        String originFileName = file.getOriginalFilename();
        String suffix = originFileName.substring(originFileName.lastIndexOf(".")+1);
        String fileName = UUID.randomUUID().toString()+"."+suffix;

        switch (mt){
            case IMAGE:
                me.setMediaPath(this.ossImagePath);
                me.setMediaFileName(this.ossImagePath+fileName);
                break;
            case VIDEO:
                me.setMediaPath(this.ossVideoPath);
                me.setMediaFileName(this.ossVideoPath+fileName);
                break;
            case AUDIO:
                me.setMediaPath(this.ossAudioPath);
                me.setMediaFileName(this.ossAudioPath+fileName);
                break;
            case THUMBNAIL:
                me.setMediaPath(this.ossAudioPath);
                me.setMediaFileName(this.ossAudioPath+fileName);
                break;
            default:
                me.setMediaPath(this.ossFilePath);
                me.setMediaFileName(this.ossFilePath+fileName);
                break;
        }
        me.setMediaName(originFileName);
        me.setMediaType(mti);
        me.setMediaUrl(this.ossUrl+me.getMediaFileName());
        bravoBiz.upload(this.ossBucket,me.getMediaFileName(), file.getBytes());
        me = insertMedia(me);
        return me.getMediaUrl();
    }

    private Media insertMedia(Media media){
        mapper.insertMediaAndGetId(media);
        Media media1 = super.selectById(media.getId());
        return media1;
    }

    public String uploadToOss(File file, String type) throws IOException {
        Integer mti = MediaType.getIntMediaType(MediaType.get(type));
        MediaType mt = MediaType.get(type);
        Media me = new Media();
        String originFileName = file.getName();
        String suffix = originFileName.substring(originFileName.lastIndexOf(".")+1);
        String fileName = UUID.randomUUID().toString()+"."+suffix;

        switch (mt){
            case IMAGE:
                me.setMediaPath(this.ossImagePath);
                me.setMediaFileName(this.ossImagePath+fileName);
                break;
            case VIDEO:
                me.setMediaPath(this.ossVideoPath);
                me.setMediaFileName(this.ossVideoPath+fileName);
                break;
            case AUDIO:
                me.setMediaPath(this.ossAudioPath);
                me.setMediaFileName(this.ossAudioPath+fileName);
                break;
            case THUMBNAIL:
                me.setMediaPath(this.ossAudioPath);
                me.setMediaFileName(this.ossAudioPath+fileName);
                break;
            default:
                me.setMediaPath(this.ossFilePath);
                me.setMediaFileName(this.ossFilePath+fileName);
                break;
        }
        me.setMediaName(originFileName);
        me.setMediaType(mti);
        me.setMediaUrl(this.ossUrl+me.getMediaFileName());
        
        byte[] buffer = TransferFileToBytes(file);
        
        
        bravoBiz.upload(this.ossBucket,me.getMediaFileName(), buffer);
        me = insertMedia(me);
        return me.getMediaUrl();
    }

    private byte[] TransferFileToBytes(File file) {
    	byte[] buffer = null;  
        try {  
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        } catch (IOException e) {
            e.printStackTrace();  
        }  
        return buffer;
    }

	public String uploadToLocal(MultipartFile file, String type){
        String filePath = "/Users/Jason/Documents/workspace/v2.0/sfec-build/sfec-admin-web/src/main/webapp/upload/"+ file.getOriginalFilename();
        Integer mti = MediaType.getIntMediaType(MediaType.get(type));
        MediaType mt = MediaType.get(type);
        Media me = new Media();
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        me.setMediaPath("/Users/Jason/Documents/workspace/v2.0/sfec-build/sfec-admin-web/src/main/webapp/upload/");
        me.setMediaFileName(file.getOriginalFilename());
        me.setMediaName(file.getOriginalFilename());
        me.setMediaType(mti);
        me.setMediaUrl("/upload/"+me.getMediaFileName());
        me = insertMedia(me);
        return me.getMediaUrl();
    }
}
