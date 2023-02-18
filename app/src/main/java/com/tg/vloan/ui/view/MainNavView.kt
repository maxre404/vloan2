package com.tg.vloan.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.tg.vloan.base.BaseRelativeLayout
import com.tg.vloan.bean.MainConfig
import com.tg.vloan.databinding.ViewMainNavViewBinding
import com.tg.vloan.utils.ResourceUtils

/**
 * Created by frcx-hb on 2022/12/3 15:29.
 */
class MainNavView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseRelativeLayout<ViewMainNavViewBinding>(context, attrs, defStyleAttr) {
    private var mainConfig: MainConfig? = null
    fun setMainConfig(mainConfig: MainConfig) {
        this.mainConfig = mainConfig
        setText(ResourceUtils.getString(mainConfig.getNavTitle()))
        setViewSelected(false)
    }

    private fun setViewSelected(selected: Boolean) {
        setTextColor(ResourceUtils.getColor(ResourceUtils.getColorResId(if (selected) mainConfig?.getTitleColorSelected() else mainConfig?.getTitleColorUnselect())))
        setImageResource(ResourceUtils.getMipmapResId(if (selected) mainConfig?.getNavIconSelected() else mainConfig?.getNavIconUnselect()))
    }

    private fun setImageResource(@DrawableRes resourceId: Int) {
        getBinding().ivNav.setImageResource(resourceId)
    }

    private fun setText(text: String) {
        getBinding().tvNav.setText(text)
    }

    private fun setTextColor(@ColorInt color: Int) {
        getBinding().tvNav.setTextColor(color)
    }

    fun onSelected() {
        setViewSelected(true)
    }

    fun onUnselected() {
        setViewSelected(false)
    }

    override fun createViewBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): ViewMainNavViewBinding {
        return ViewMainNavViewBinding.inflate(layoutInflater, container, true)
    }
}