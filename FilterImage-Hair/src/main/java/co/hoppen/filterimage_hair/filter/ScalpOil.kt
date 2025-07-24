package co.hoppen.filterimage_hair.filter

import android.graphics.Bitmap
import co.hoppen.filterimage.BaseFilter
import co.hoppen.filterimage.model.Engine
import co.hoppen.filterimage.model.ResultData
import co.hoppen.filterimage_hair.HairEngine

/**
 * Created by YangJianHui on 2025/7/24.
 */
class ScalpOil : BaseFilter() {
    override fun onFilter(
        oriBitmap: Bitmap,
        engine: Engine,
    ): Triple<Bitmap, Int, ResultData?> {

        val dst = oriBitmap.copy(oriBitmap.config!!,true)

        (engine as HairEngine).test(oriBitmap,dst)

        return Triple(dst,50,null)

    }
}