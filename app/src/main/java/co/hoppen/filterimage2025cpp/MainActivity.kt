package co.hoppen.filterimage2025cpp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.createBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.hoppen.filterimage.FilterHelper
import co.hoppen.filterimage.model.Engine
import co.hoppen.filterimage.utils.createBitmapFromAssetsFile
import co.hoppen.filterimage2025cpp.databinding.ActivityMainBinding
import co.hoppen.filterimage_hair.HairFilterHelper
import co.hoppen.filterimage_hair.HairFilterType
import co.hoppen.filterimage_skin.SkinEngine
import co.hoppen.filterimage_skin.SkinFilterHelper
import co.hoppen.filterimage_skin.SkinFilterType

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun contentLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val src = "2604.jpg".createBitmapFromAssetsFile()

        val dst = createBitmap(src.width,src.height,Bitmap.Config.ARGB_8888)

        val filterHelper = HairFilterHelper()

        getDataBinding().image.apply {
            setImageBitmap(src)
            setOnClickListener {
                filterHelper.execute(
                    HairFilterType.SCALP_OIL,
                    src,
                    0f,
                    ""){
                    setImageBitmap(it.filterBitmap!!)
                }
            }
        }

//        object :FilterHelper(){
//            override val engine: Engine = Engine()
//        }.execute(HairFilterType.TEST_FILTER,src,0f,"",{
//            LogUtils.e(it)
//        })

    }

}
