package cn.edu.tit.forum.provider;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
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

    public String upload(InputStream fileStream, String fileName) {
        String generatedFileName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            return null;
        }

        ObsClient obsClient = new ObsClient(accessKey, secretKey, endPoint);
        obsClient.putObject(bucketName, generatedFileName, fileStream);
        return generatedFileName;
    }
}
