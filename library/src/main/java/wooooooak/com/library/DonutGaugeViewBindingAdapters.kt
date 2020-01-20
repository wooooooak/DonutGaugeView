package wooooooak.com.library

import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter


object DonutGaugeViewBindingAdapters {
    @BindingAdapter("app:dg_bottom_text")
    @JvmStatic
    fun setBottomText(view: DonutGaugeView, text: String) {
        view.bottomText = text
    }

    @BindingAdapter("app:dg_top_text")
    @JvmStatic
    fun setTopText(view: DonutGaugeView, text: String) {
        view.topText = text
    }

    @BindingAdapter("app:dg_start_value")
    @JvmStatic
    fun setStartValue(view: DonutGaugeView, value: Float) {
        view.currentValue = value.toInt()
        if (view.maxValue != 0.toFloat()) {
            view.updateValue(value)
        }
    }

    @BindingAdapter("app:dg_end_value")
    @JvmStatic
    fun setEndValue(view: DonutGaugeView, value: Float = 9999999999.toFloat()) {
        view.maxValue = value
        if (view.currentValue != 0) {
            view.initValue(view.currentValue.toFloat(), value)
        }
    }

    @BindingAdapter("app:dg_complete_circle_color")
    @JvmStatic
    fun setCompleteCircleColor(view: DonutGaugeView, @ColorRes resource: Int) {
        view.completeCircleColor = resource
    }
}