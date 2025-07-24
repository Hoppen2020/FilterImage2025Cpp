package co.hoppen.filterimage_hair

import android.graphics.Bitmap
import co.hoppen.filterimage.model.Engine

class HairEngine: Engine(){

    /**
     * A native method that is implemented by the 'filterimage_hair' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun test(src: Bitmap, dst: Bitmap)

    companion object {
        // Used to load the 'filterimage_hair' library on application startup.
        init {
            System.loadLibrary("filterimage_hair")
        }
    }
}