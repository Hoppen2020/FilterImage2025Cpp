package co.hoppen.filterimage2025cpp

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnSystemUiVisibilityChangeListener
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by YangJianHui on 2025/7/22.
 */
abstract class BaseActivity<DB:ViewDataBinding> : AppCompatActivity() {

    private lateinit var viewDataBinding:DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView<DB>(this, contentLayoutId())
    }

    abstract fun contentLayoutId(): Int

    fun getDataBinding():DB{
        return viewDataBinding
    }
}