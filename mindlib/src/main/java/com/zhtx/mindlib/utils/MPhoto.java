package com.zhtx.mindlib.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.zhtx.mindlib.R;
import com.zhtx.mindlib.imagepicker.AliOss;
import com.zhtx.mindlib.imagepicker.ChoosePhotoActivity;
import com.zhtx.mindlib.imagepicker.GlideImageLoader;
import com.zhtx.mindlib.widge.MDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zxy.tiny.Tiny;

import java.io.File;
import java.util.ArrayList;


/**
 * 作者: Coding Farmer_5199.
 * @date 2017/12/13.
 * 描述：
 */

public class MPhoto {

    public static final int TGA_IMAGE = 100;      // 相册
    public static final int TGA_CAMERA = 101;     // 拍照

    private static String path;     // 图片路径
    private static Tiny.FileCompressOptions compressOptions;    // 图片压缩
    private static ImagePicker imagePicker;                     // 图片选择框架
    private static OnResultCallback resultCallback;             // 图片压缩上传结果回调
    private static Builder mBuilder;

    public static OnResultCallback getCallback() {
        return resultCallback;
    }

    public static void init(Builder builder) {
        mBuilder = builder;
        resultCallback = builder.resultCallback;

        // 判断是否有设置图片选择参数
        if (builder.imagePicker != null) {
            imagePicker = builder.imagePicker;
        } else {
            initImagePicker();
        }

        if (builder.aspectX != 0 && builder.aspectY != 0) {
            imagePicker.setCrop(true);
            Display display = ((Activity) builder.context).getWindowManager().getDefaultDisplay();
            Point outSize = new Point();
            display.getSize(outSize);
            int x;
            int y;
            x = (outSize.x * 9) / 10;
            y = builder.aspectY * (x / builder.aspectX);
            imagePicker.setFocusWidth(x);   //裁剪框的宽度。单位像素
            imagePicker.setFocusHeight(y);  //裁剪框的高度。单位像素
            imagePicker.setOutPutX(x);
            imagePicker.setOutPutY(y);

        }
        if (builder.cropStyles != null) {
            imagePicker.setCrop(true);
            imagePicker.setStyle(builder.cropStyles);
        }

        // 判断是否有图片压缩参数 图片压缩默认150k内
        if (builder.compressOptions == null) {
            compressOptions = new Tiny.FileCompressOptions();
            compressOptions.size = 150;
        } else {
            compressOptions = builder.compressOptions;
        }
        path = builder.path;
        showDialog();

    }

    public static OnChooseCallback getChooseCallback() {
        // 图片选择结果回调
        return new OnChooseCallback() {
            @Override
            public void onChooseSuccess(ArrayList<ImageItem> resultList) {
                uploadSinglePic(resultList);
            }

            @Override
            public void onChooseFailure(String errorMsg) {
                resultCallback.onFailure("选择图片失败");
            }
        };
    }

    /**
     * 上传单张图片
     *
     * @param resultList 图片集合
     */
    private static void uploadSinglePic(ArrayList<ImageItem> resultList) {
        final ProgressDialog progressDialog = new ProgressDialog(mBuilder.context);
        progressDialog.setTitle("图片上传中，请稍候...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setProgress(20);
        progressDialog.setMax((120));
        // 图片路径
        String picPath = resultList.get(0).path;
        picCompress(picPath);
        ImageCompress.Tiny(picPath, new ImageCompress.TinyCallback() {
            @Override
            public void onCallback(boolean isSuccess, Bitmap bitmap, final String outfile) {
                if (isSuccess) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            new AliOss(mBuilder.context, outfile) {
                                @Override
                                protected void uploadProgress(long currentSize, long totalSize) {
                                    progressDialog.setProgress((int) currentSize + 20);
                                    progressDialog.setMax((int) totalSize + 20);
                                    if (currentSize == totalSize) {
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void uploadSuccess(String myPicUrl) {
                                    resultCallback.onSuccess(myPicUrl);
                                }

                                @Override
                                protected void uploadFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                                    progressDialog.dismiss();
                                    resultCallback.onFailure("图片上传阿里云失败");
                                }
                            }.start(path);
                        }
                    }).start();
                } else {
                    progressDialog.dismiss();
                    resultCallback.onFailure("图片压缩失败");
                }
            }
        }, compressOptions);
    }

    /**
     * 初始化选择图片、拍照
     */
    private static void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setCrop(false);             // 裁剪
        imagePicker.setMultiMode(false);        // 单选
        imagePicker.setShowCamera(true);        // 显示相机
    }

    /**
     * 压缩图片参数设置
     *
     * @param picPath 图片路径
     */
    private static void picCompress(String picPath) {
        // 计算图片压缩目标大小
        File outputFile = new File(picPath);
        long fileSize = outputFile.length();
        double scale = Math.sqrt((float) fileSize / (1024 * compressOptions.size));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picPath, options);
        int height = options.outHeight;
        int width = options.outWidth;
        compressOptions.width = (int) (width / scale);
        compressOptions.height = (int) (height / scale);
        compressOptions.config = Bitmap.Config.ARGB_8888;
    }

    /**
     * 构建图片选择所需要的参数
     */
    public static class Builder {
        private Context context;            // 上下文
        private String title;               // 弹窗标题
        private ImagePicker imagePicker;    // 图片选择框架
        private Tiny.FileCompressOptions compressOptions;   // 图片压缩参数
        private OnResultCallback resultCallback;    // 图片上传结果回调
        private String path;    // 图片路径
        private int aspectX, aspectY;
        private CropImageView.Style cropStyles;

        public Builder init(Context context) {
            this.context = context;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setImagePicker(ImagePicker imagePicker) {
            this.imagePicker = imagePicker;
            return this;
        }

        public Builder setCompressOptions(Tiny.FileCompressOptions compressOptions) {
            this.compressOptions = compressOptions;
            return this;
        }

        public Builder setResultCallback(OnResultCallback callback) {
            this.resultCallback = callback;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setAspectXY(int aspectX, int aspectY) {
            this.aspectY = aspectY;
            this.aspectX = aspectX;
            return this;
        }

        public Builder setCropStyle(CropImageView.Style cropStyles) {
            this.cropStyles = cropStyles;
            return this;
        }
    }

    private static ArrayList<String> datas = new ArrayList<>();

    /**
     * 弹出选择框
     */
    private static void showDialog() {
        datas.clear();
        datas.add("拍照");
        datas.add("图库");
        final MDialog mDialog = new MDialog(mBuilder.context);

        mDialog.withTitie(TextUtils.isEmpty(mBuilder.title) ? "图片选择" : mBuilder.title)
                .showCustomPanelLine()
                .setCustomView(R.layout.dialog_son_listview, new MDialog.CustomInter() {
                    @Override
                    public void custom(View customView) {
                        ListView listView = (ListView) customView.findViewById(R.id.dialog_listview);
                        listView.setVerticalScrollBarEnabled(false);
                        listView.addFooterView(new ViewStub(customView.getContext()));
                        listView.setAdapter(
                                new CommonAdapter<String>(customView.getContext(), R.layout.item_textcenter, datas) {
                                    @Override
                                    protected void convert(ViewHolder viewHolder, String item, int position) {
                                        viewHolder.setText(R.id.tv_item_center, item);
                                    }
                                }
                        );
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                switch (datas.get(position)) {
                                    case "拍照":
                                        mBuilder.context.startActivity(new Intent(mBuilder.context, ChoosePhotoActivity.class).putExtra("position", TGA_CAMERA));
                                        break;
                                    case "图库":
                                        mBuilder.context.startActivity(new Intent(mBuilder.context, ChoosePhotoActivity.class).putExtra("position", TGA_IMAGE));
                                        break;
                                    default:
                                }
                                mDialog.dismiss();
                            }
                        });

                        mDialog.setMaxHeight(customView);
                    }
                })
                .setBtn1Text("取消")
                .setBtn1(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                }).show();
    }


    /**
     * 处理结果
     */
    public interface OnResultCallback {
        void onSuccess(String data);

        void onFailure(String errorMsg);
    }


    /**
     * 选择图片结果
     */
    public interface OnChooseCallback {
        void onChooseSuccess(ArrayList<ImageItem> resultList);

        void onChooseFailure(String errorMsg);
    }
}
