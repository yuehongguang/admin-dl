package com.github.wxiaoqi.security.admin.biz;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BravoBiz {

	@Value("${oss.AKId:LTAI8P90l4zCAzbQ}")
	String ossAKId;

	@Value("${oss.AKSecret:UewAcm32DnitSfibT1cICAcsZ6F5rW}")
	String ossAKSecret;

	@Value("${oss.EndPoint:oss-cn-beijing.aliyuncs.com}")
	String ossEndPoint;

	/**
	 * 将指定文件file上传到OSS指定bucket，名称为key
	 * 
	 * @param bucketName	bucket名称
	 * @param key	上传后的名称
	 * @param file	待上传的文件
	 */
	public void upload(String bucketName, String key, File file) {
		OSSClient client = getOSSClient();
		client.putObject(bucketName, key, file);
		client.shutdown();
	}

	/**
	 * 将指定文件byte[] 上传到OSS指定bucket,名称为key
	 * @param bucketName
	 * @param key
	 * @param file
     */
	public void upload(String bucketName,String key ,byte[] file){
		OSSClient client = getOSSClient();
		InputStream is = new ByteArrayInputStream(file);
		client.putObject(bucketName, key, is);
	}

	/**
	 * @param bucketName
	 * @param key
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File download(String bucketName, String key, String fileName)
			throws IOException {
		OSSClient client = getOSSClient();
		if (!client.doesObjectExist(bucketName, key)) {
			return null;
		}
		FileOutputStream writer = new FileOutputStream(fileName);
		InputStream in = client.getObject(bucketName, key).getObjectContent();
		writer.write(IOUtils.readStreamAsByteArray(in));
		in.close();
		writer.close();
		client.shutdown();
		return new File(fileName);
	}

	/**
	 * 删除指定bucket上的文件
	 * 
	 * @param bucketName	bucket名称
	 * @param key	文件名
	 */
	public void delete(String bucketName, String key) {
		OSSClient client = getOSSClient();
		client.deleteObject(bucketName, key);
		client.shutdown();
	}
	
	/**
	 * 获取一个OSSClient, 确保后续关闭
	 * 
	 * ossClient.shutdown();
	 * 
	 * @return client
	 */
	public OSSClient getOSSClient() {
		return new OSSClient(ossEndPoint, ossAKId, ossAKSecret);
	}

	/**
	 * 验证文件是否存在于OSS上
	 * 
	 * @param bucketName	
	 * @param key	文件名称
	 * @return
	 */
	public boolean objExist(String bucketName, String key) {
		OSSClient client = getOSSClient();
		boolean result = client.doesObjectExist(bucketName, key);
		client.shutdown();
		return result;
	}
}
