package co.hoppen.filterimage

import android.graphics.Bitmap
import co.hoppen.filterimage.model.Engine
import co.hoppen.filterimage.model.FilterResult
import co.hoppen.filterimage.model.FilterState
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ReflectUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ThreadUtils

/**
 * Created by YangJianHui on 2024/5/14.
 */
abstract class FilterHelper(val engine: Engine) {

    fun execute(filterType: FilterType,
                oriBitmap: Bitmap,
                resistance:Float = 0f,
                savePath:String="",
                filterCallBack:(FilterResult)->Unit){
        val initialResult = FilterResult(filterType,resistance)
        try {
            ThreadUtils.executeBySingle(object : ThreadUtils.SimpleTask<FilterResult>(){
                override fun doInBackground(): FilterResult {
                    val copyOri = oriBitmap.copy(oriBitmap.config!!,true)
                    val newInstance = ReflectUtils.reflect(filterType.clazz)
                                                  .newInstance()
                                                  .get<BaseFilter>()
                    return newInstance.onFilter(copyOri,engine).run {
                            if (!copyOri.isRecycled)copyOri.recycle()
                            val resultBitmap = this.first
                            val score = this.second
                            if (!StringUtils.isEmpty(savePath)){
                                val save = ImageUtils.save(
                                    resultBitmap,
                                    savePath,
                                    Bitmap.CompressFormat.JPEG
                                )
                                LogUtils.e(save,savePath)
                            }
                            val data = if (this.third!=null) GsonUtils.toJson(this.third) else ""
                            initialResult.copy(score = score,
                                filterBitmap = resultBitmap,
                                savePath = savePath,
                                resultData = data)
                        }
                }

                override fun onSuccess(result: FilterResult) {
                    filterCallBack(result.copy(state = FilterState.SUCCESS))
                }

            })
        }catch (e:Exception){
            LogUtils.e(e.toString())
            filterCallBack(initialResult.copy(state = FilterState.FAILURE))
        }
    }


}