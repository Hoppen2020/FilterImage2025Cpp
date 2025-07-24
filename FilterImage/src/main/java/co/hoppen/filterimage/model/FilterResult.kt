package co.hoppen.filterimage.model

import android.graphics.Bitmap
import co.hoppen.filterimage.FilterType

/**
 * Created by YangJianHui on 2024/5/15.
 */
data class FilterResult(
    val filterType: FilterType,
    val resistance:Float,
    val score:Int = 0,
    val filterBitmap: Bitmap? = null,
    val state: FilterState = FilterState.NOT_CONVERTED,
    val savePath:String="",
    val resultData:String?=null) {
}

enum class FilterState{
    NOT_CONVERTED,
    SUCCESS,
    FAILURE
}
