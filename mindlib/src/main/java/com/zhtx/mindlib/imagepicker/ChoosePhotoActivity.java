package com.zhtx.mindlib.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zhtx.mindlib.utils.MPhoto;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;

/**
 * 作者: Coding Farmer_5199.
 * @date 2017/12/12.
 * 描述：
 */

public class ChoosePhotoActivity extends Activity {

    private ArrayList<ImageItem> images = null; // 选择的图片集合

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        choose(getIntent().getIntExtra("position", MPhoto.TGA_IMAGE));
    }

    private void choose(int position) {
        switch (position) {
            case MPhoto.TGA_IMAGE:
                // 选择图片跳转
                Log.e("imagepick", position + "");
                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, false); // 不打开拍照
                startActivityForResult(intent, MPhoto.TGA_IMAGE);
                break;
            case MPhoto.TGA_CAMERA:
                // 拍照
                Intent intent1 = new Intent(this, ImageGridActivity.class);
                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                intent1.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 打开拍照
                startActivityForResult(intent1, MPhoto.TGA_CAMERA);
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MPhoto.OnChooseCallback callback = MPhoto.getChooseCallback();
        // 选取图片
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            callback.onChooseSuccess(images);
        } else {
            callback.onChooseFailure("图片选择失败");
        }
        finish();
    }
}
