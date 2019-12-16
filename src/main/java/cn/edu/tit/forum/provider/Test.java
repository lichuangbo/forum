package cn.edu.tit.forum.provider;

import com.obs.services.ObsClient;
import com.obs.services.model.ObsBucket;

import java.io.IOException;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/16
 */
public class Test {
    public static void main(String[] args) {
        String endPoint = "https://obs.cn-north-4.myhuaweicloud.com";
        String ak = "WQMZEDN6NYKAT6TYAVGV";
        String sk = "hQQy6dD2XP0zIJO5JTBMsHMIakG4WpzIOXoZoQxX";
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        // 使用访问OBS
        ObsBucket obsBucket = createBucket(obsClient, "cn-edu-tit-forum", "cn-north-4");

        // 关闭obsClient
        try {
            obsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //obsClient.putObject("bu-ck-et", "object", new ByteArrayInputStream("Hello OBS".getBytes()));
    }


    private static ObsBucket createBucket(ObsClient obsClient, String g_bucketName, String g_bucketLoc) {
        ObsBucket obsBucket = new ObsBucket(g_bucketName, g_bucketLoc);
        obsClient.createBucket(obsBucket);
        System.out.println("Create bucket:" + g_bucketName + " successfully!");
        return obsBucket;
    }
}
