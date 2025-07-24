package co.hoppen.filterimage

import android.graphics.Bitmap
import co.hoppen.filterimage.model.Engine
import co.hoppen.filterimage.model.ResultData

/**
 * Created by YangJianHui on 2024/5/14.
 */
abstract class BaseFilter {

    abstract fun onFilter(oriBitmap: Bitmap,engine: Engine):Triple<Bitmap,Int, ResultData?>

}