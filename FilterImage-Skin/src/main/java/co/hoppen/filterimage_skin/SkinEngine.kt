package co.hoppen.filterimage_skin

import android.graphics.Bitmap
import co.hoppen.filterimage.model.Engine

class SkinEngine: Engine(){

    external fun stringFromJNI(): String

    external fun test(src: Bitmap, dst: Bitmap)

    companion object {
        init {
            System.loadLibrary("filterimage_skin")
        }
    }

}