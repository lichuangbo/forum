package cn.edu.tit.forum.provider;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.*;
import exception.CustomizeErrorCode;
import exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/16
 */
@Component
public class OBSProvider {
    @Value("${huaweicloud.obs.endPoint}")
    private String endPoint;

    @Value("${huaweicloud.obs.access-key}")
    private String accessKey;

    @Value("${huaweicloud.obs.secret-key}")
    private String secretKey;

    @Value("${huaweicloud.obs.bucketName}")
    private String bucketName;

    public String upload(InputStream fileStream, String contentType, String fileName) {
        // 使用UUID生成上传图片名
        String generatedFileName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

        // 配置config
        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(endPoint);
        // 设置metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(accessKey, secretKey, config);
        // 创建对象
        PutObjectResult response = obsClient.putObject(bucketName, generatedFileName, fileStream, metadata);

        if (response != null) {
            Long expireSeconds = 365 * 24 * 60 * 60L;

            TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
            request.setBucketName(bucketName);
            request.setObjectKey(generatedFileName);

            TemporarySignatureResponse signatureResponse = obsClient.createTemporarySignature(request);
            return signatureResponse.getSignedUrl();
        }
        return generatedFileName;
    }
}
