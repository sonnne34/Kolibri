package kolibri.example.kolibri.Singleton;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.media.Image;
import android.net.Uri;
import android.renderscript.Sampler;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageSingleton extends Application {
    private static ImageSingleton imageSingleton;
    private HashMap <Uri, File> pictureMain = new HashMap <>()  ;


    public static synchronized ImageSingleton getImageSingleton() {
        if (imageSingleton == null) {
            imageSingleton = new ImageSingleton();
        }
        return imageSingleton;
    }

    private ImageSingleton() {

    }

    public boolean proverkaNaNalichie (){

        if(pictureMain.keySet().equals("") ){
            return true;
        }else {
            return false;
        }


    }

    public void addPictureMain(Uri picture, File pictureOne) {
        pictureMain.put(picture,pictureOne);
    }
    public File getPictureMain () {
        return pictureMain.get(pictureMain);
    }

    public void showFile (){
        Log.d("File","Image = " + pictureMain);
    }

}

