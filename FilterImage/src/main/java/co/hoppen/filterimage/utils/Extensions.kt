package co.hoppen.filterimage.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.blankj.utilcode.util.Utils

/**
 * Created by YangJianHui on 2024/12/4.
 */

fun String.createBitmapFromAssetsFile():Bitmap{
    val inputStream = Utils.getApp().resources.assets.open(this)
    val createBitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()
    return createBitmap
}

