package com.google.android.gms.samples.vision.face.facetracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.security.auth.callback.Callback;

public class MyFaceDetecter extends Detector<Face> {

    private FaceDetector detector;

    public Bitmap getBit() {
        return bit;
    }

    private Bitmap bit;

    public MyFaceDetecter(FaceDetector detector){
        this.detector = detector;
    }

    @Override
    public SparseArray<Face> detect(Frame frame) {
        Log.i("xxxxx","xxxxx");
        frame.getGrayscaleImageData().array();
        byte[] byteArray = frame.getGrayscaleImageData().array();
        String sendString;
        try {
//将byte转为String
            sendString = new String(byteArray, "UTF-8");
            try {
//将String转回byte
                byte[] data = sendString.getBytes("UTF-8");
// 为UTF8编吗
// 把二进制图片转成位图
                YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, 200,
                        200, null); // 20、20分别是图的宽度与高度
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
// 80--JPG图片的质量[0-100],100最高
                yuvimage.compressToJpeg(new Rect(0, 0, 20, 20), 80, baos2);
                byte[] jdata = baos2.toByteArray();
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(jdata, 0,
                        jdata.length);
                bit = bitmap2;
            } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        frame.getBitmap()
//        frame.getGrayscaleImageData().array(); NV21 Array
        callback.onCallback();
        return detector.detect(frame);
    }




    private Callback callback;

    public interface Callback{
        void onCallback();
    }

    public void setCallback(MyFaceDetecter.Callback callback){
        this.callback = callback;
    }

}
