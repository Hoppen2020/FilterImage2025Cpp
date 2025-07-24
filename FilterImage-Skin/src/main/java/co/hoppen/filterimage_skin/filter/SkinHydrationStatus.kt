package co.hoppen.filterimage_skin.filter

import android.graphics.Bitmap
import co.hoppen.filterimage.BaseFilter
import co.hoppen.filterimage.model.Engine
import co.hoppen.filterimage.model.ResultData
import co.hoppen.filterimage_skin.SkinEngine

/**
 * Created by YangJianHui on 2025/7/24.
 */
class SkinHydrationStatus : BaseFilter() {
    override fun onFilter(
        oriBitmap: Bitmap,
        engine: Engine,
    ): Triple<Bitmap, Int, ResultData?> {

        val dst = oriBitmap.copy(oriBitmap.config!!,true)

        (engine as SkinEngine).test(oriBitmap,dst)

        return Triple(dst,50,null)
    }
}