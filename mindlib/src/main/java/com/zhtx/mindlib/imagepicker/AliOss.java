package com.zhtx.mindlib.imagepicker;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


/**
 * 阿里云上传图片
 * aliyun-oss-sdk-android-2.0.1
 * okhttp-2.6.0
 * okio-1.6.0
 */
public abstract class AliOss {

    public static final String IMAGE_USER = "Userimage_test";             // 用戶图像
    public static final String IMAGE_SHOP = "Shop_test";                  // 店铺图像
    public static final String IMAGE_COMMODITY = "Commodityimage_test";   // 商品图片
    public static final String IMAGE_EVALUATE = "evaluate_test";          // 商品评论
    public static final String IMAGE_PUBLIC = "publicimage_test";         // 公共使用的图片
    public static final String VIDEO_PUBLIC = "publicvideo_test";         // 公共使用的视频
    public static final String IMAGE_MESSAGE = "Refund_test";             // 退货退款图片
    public static final String IMAGE_REFUNDS = "news_test";               // 消息图片

//    public static final String IMAGE_USER = "Userimage";             // 用戶图像
//    public static final String IMAGE_SHOP = "Shop";                  // 店铺图像
//    public static final String IMAGE_COMMODITY = "Commodityimage";   // 商品图片
//    public static final String IMAGE_EVALUATE = "evaluate";          // 商品评论
//    public static final String IMAGE_PUBLIC = "publicimage";         // 公共使用的图片
//    public static final String IMAGE_MESSAGE = "Refund";             // 退货退款图片
//    public static final String IMAGE_REFUNDS = "news";               // 消息图片


    private final String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    private final String accessKeyId = "LTAIFJ28KNH0pO5Q";
    private final String accessKeySecret = "jqIiD2E1txXPWECVPxiWr3YtltxgBm";
    private final String BucketName = "qqsdg";
    private final String aliyunPath1 = "http://qqsdg.oss-cn-shenzhen.aliyuncs.com/";

    private Context mContext;
    private String picName;     // 上传文件目录名字
    private String picurlpath;  // 本地图片路径
    private byte[] picurl;        // byte图片数据


    public AliOss(Context mContext, String picurlpath) {
        this.mContext = mContext;
        this.picurlpath = picurlpath;
    }

    public AliOss(Context mContext, byte[] picurl) {
        this.mContext = mContext;
        this.picurl = picurl;
    }

    /**
     * 阿里云基础配置
     * @return
     */
    public OSS getOss() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        return new OSSClient(this.mContext, endpoint, credentialProvider, conf);
    }


    /**
     * 开始上传图片
     * @param path 上传路径类型
     */
    public void start(String path) {
        if (TextUtils.isEmpty(path)){
            path = IMAGE_PUBLIC;
        }
        // 上传文件目录名字
        String dateFolder = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        if (path.equals(IMAGE_PUBLIC)) {
            picName = path + "/pic" + dateFolder + new Random().nextInt(100) + ".jpg";
        } else if (path.equals(VIDEO_PUBLIC)){
            picName = path + "/video" + dateFolder + new Random().nextInt(100) + ".mp4";
        }

        // 构造上传请求
        PutObjectRequest put;
        if ( TextUtils.isEmpty(picurlpath)) {
            put = new PutObjectRequest(BucketName, picName, picurl);
        } else {
            put = new PutObjectRequest(BucketName, picName,  picurlpath);
        }
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                uploadProgress(currentSize, totalSize);
            }
        });

        OSSAsyncTask task = getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("AliOss", "---上传图片成功---" + aliyunPath1 + picName);
                uploadSuccess(aliyunPath1 + picName);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                uploadFailure(request, clientExcepion, serviceException);
            }
        });

        task.waitUntilFinished(); // 等待直到任务完成
    }


    /**
     * 上传进度
     *
     * @param currentSize
     * @param totalSize
     */
    protected abstract void uploadProgress(long currentSize, long totalSize);

    /**
     * 上传成功
     *
     * @param myPicUrl
     */
    protected abstract void uploadSuccess(String myPicUrl);

    /**
     * 上传失败
     *
     * @param request
     * @param clientExcepion
     * @param serviceException
     */
    protected abstract void uploadFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException);
}
