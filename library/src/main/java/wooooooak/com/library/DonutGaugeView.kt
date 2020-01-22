package wooooooak.com.library

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class DonutGaugeView @JvmOverloads constructor(
    private val _context: Context,
    private val _attributeSet: AttributeSet? = null,
    _defStyle: Int = 0
) : View(
    _context,
    _attributeSet,
    _defStyle
) {
    var currentValue = 0
    var maxValue: Float? = 1000f

    var backgroundCircleColor = ContextCompat.getColor(context, R.color.dg_background)
    var frontCircleColor = ContextCompat.getColor(context, R.color.dg_blue)
    var unCompleteCircleColor = ContextCompat.getColor(context, R.color.dg_blue)
    var completeCircleColor = ContextCompat.getColor(context, R.color.dg_complete)

    private var currentRatio = 0f

    var donutSize = resources.getDimensionPixelSize(R.dimen.donut_size)
    var donutStrokeWidth = resources.getDimensionPixelSize(R.dimen.donut_stroke_size)

    private lateinit var unitText: String
    var unitTextColor = ContextCompat.getColor(context, R.color.dg_black)
    var unitTextSize = resources.getDimensionPixelSize(R.dimen.dg_default_text_size)

    var paintMiddleTextColor = ContextCompat.getColor(context, R.color.dg_black)
    var middleTextColor = ContextCompat.getColor(context, R.color.dg_black)
    var onExceedMiddleTextColor = ContextCompat.getColor(context, R.color.dg_blue)
    var middleTextSize = resources.getDimensionPixelSize(R.dimen.dg_middle_text_size)

    var topText = ""
    var topTextColor = ContextCompat.getColor(context, R.color.dg_black)
    var topTextSize = resources.getDimensionPixelSize(R.dimen.dg_default_text_size)

    var bottomText = ""
    var bottomTextColor = ContextCompat.getColor(context, R.color.dg_gray)
    var bottomTextSize = resources.getDimensionPixelSize(R.dimen.dg_default_text_size)

    var marginTopMiddleText =
        resources.getDimensionPixelSize(R.dimen.dg_top_middle_text_margin)
    var marginMiddleBottomText =
        resources.getDimensionPixelSize(R.dimen.dg_middle_bottom_text_margin)

    private var animationDuration = 1000L

    init {
        initAttributes()
        setBackgroundColor(Color.TRANSPARENT)
        maxValue?.let {
            initValue(currentValue.toFloat(), it)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(widthMeasureSpec)

        var width = resources.getDimensionPixelSize(R.dimen.donut_size)
        var height = resources.getDimensionPixelSize(R.dimen.donut_size)
        if (heightMode == MeasureSpec.EXACTLY) {
            donutSize = heightSize
            height = heightSize
            width = widthSize
        }

        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val rectF = RectF(40f, 40f, donutSize - 40f, donutSize - 40f)
            val paint = Paint().apply {
                color = backgroundCircleColor
                isAntiAlias = true
                style = Paint.Style.STROKE
                strokeWidth = donutStrokeWidth.toFloat()
            }
            it.drawArc(rectF, 0f, 360f, false, paint)

            paint.apply {
                color = frontCircleColor
                strokeJoin = Paint.Join.ROUND
                strokeCap = Paint.Cap.ROUND
            }
            it.drawArc(rectF, -90f, currentRatio, false, paint)

            drawInnerContent(paint, it)
        }
    }

    fun updateValue(value: Float) {
        maxValue?.let { total ->
            frontCircleColor = if (value >= total) completeCircleColor else unCompleteCircleColor
            paintMiddleTextColor = if (value >= total) onExceedMiddleTextColor else middleTextColor
            val startRatio = currentRatio
            val endRatio = (value / total) * 360
            val valueOffset = endRatio - startRatio
            ValueAnimator.ofFloat(0f, 1f).apply {
                duration = animationDuration
                interpolator = LinearInterpolator()
                addUpdateListener {
                    val offset = it.animatedValue as Float
                    currentRatio = if (total == 0f) 360f else startRatio + (valueOffset * offset)
                    currentValue += ((value.toInt() - currentValue) * offset).toInt()
                    invalidate()
                }
            }.start()
        }
    }

    private fun drawInnerContent(paint: Paint, canvas: Canvas) {
        // Start Middle Text rendering
        paint.apply {
            textSize = middleTextSize.toFloat()
            color = paintMiddleTextColor
            typeface = ResourcesCompat.getFont(_context, R.font.noto_sans_semi_bold)
            strokeWidth = 2f
            style = Paint.Style.FILL_AND_STROKE
        }
        val middleText = currentValue.toString()
        val middleTextXPos = width / 2 - (paint.measureText(middleText + unitText) / 2)
        val middleTextYPos = (height / 2 - ((paint.descent() + paint.ascent()) / 2))
        canvas.drawText(middleText, middleTextXPos, middleTextYPos, paint)

        // Start Unit Text rendering
        paint.run {
            val unitTextXPos =
                middleTextXPos + paint.measureText(middleText) + resources.getDimension(
                    R.dimen.dg_middle_text_margin
                )
            textSize = unitTextSize.toFloat()
            color = unitTextColor
            canvas.drawText(unitText, unitTextXPos, middleTextYPos, paint)
        }

        // Start Top Text rendering
        paint.run {
            style = Paint.Style.FILL
            color = topTextColor
            textSize = topTextSize.toFloat()
            val topText = topText
            val topTextXPos = width / 2 - (paint.measureText(topText) / 2)
            val topTextYPos = middleTextYPos - paint.textSize - marginTopMiddleText
            canvas.drawText(topText, topTextXPos, topTextYPos, paint)
        }
        // Start Bottom Text rendering
        paint.run {
            color = bottomTextColor
            textSize = bottomTextSize.toFloat()
            val bottomText = bottomText
            val bottomTextXPos = width / 2 - (paint.measureText(bottomText) / 2)
            val bottomTextYPos = middleTextYPos + paint.textSize + marginMiddleBottomText
            canvas.drawText(bottomText, bottomTextXPos, bottomTextYPos, paint)
        }
    }

    fun initValue(_currentValue: Float, _maxValue: Float) {
        maxValue = _maxValue
        val endRatio = (_currentValue / _maxValue) * 360
        if (_currentValue >= _maxValue) {
            ValueAnimator.ofFloat(0f, 1f).apply {
                duration = animationDuration
                interpolator = LinearInterpolator()
                addUpdateListener {
                    val offset = it.animatedValue as Float
                    currentRatio = 360 * offset
                    currentValue = (_currentValue * offset).toInt()
                    invalidate()
                }
            }.start()
            frontCircleColor = if(maxValue == 0f) unCompleteCircleColor else completeCircleColor
            paintMiddleTextColor = onExceedMiddleTextColor
            invalidate()
        } else {
            frontCircleColor = unCompleteCircleColor
            paintMiddleTextColor = middleTextColor
            ValueAnimator.ofFloat(0f, 1f).apply {
                duration = animationDuration
                interpolator = LinearInterpolator()
                addUpdateListener {
                    val offset = it.animatedValue as Float
                    currentRatio = endRatio * offset
                    currentValue = (_currentValue * offset).toInt()
                    invalidate()
                }
            }.start()
        }
    }

    private fun initAttributes() {
        _attributeSet?.let {
            _context.obtainStyledAttributes(_attributeSet, R.styleable.DonutGaugeView)?.run {
                donutStrokeWidth =
                    getDimensionPixelSize(
                        R.styleable.DonutGaugeView_dg_stroke_width,
                        donutStrokeWidth
                    )
                currentValue =
                    getFloat(R.styleable.DonutGaugeView_dg_start_value, currentValue.toFloat())
                        .toInt()
                maxValue = getFloat(R.styleable.DonutGaugeView_dg_end_value, 0f)
                backgroundCircleColor = getColor(
                    R.styleable
                        .DonutGaugeView_dg_back_circle_color, backgroundCircleColor
                )
                frontCircleColor =
                    getColor(R.styleable.DonutGaugeView_dg_front_circle_color, frontCircleColor)
                unCompleteCircleColor = frontCircleColor
                completeCircleColor =
                    getColor(
                        R.styleable.DonutGaugeView_dg_complete_circle_color,
                        completeCircleColor
                    )
                unitText = getString(R.styleable.DonutGaugeView_dg_unit_text) ?: ""
                unitTextSize = getDimensionPixelSize(
                    R.styleable
                        .DonutGaugeView_dg_unit_text_size, unitTextSize
                )
                unitTextColor =
                    getColor(R.styleable.DonutGaugeView_dg_unit_text_color, unitTextColor)
                topText = getString(R.styleable.DonutGaugeView_dg_top_text) ?: ""
                topTextSize = getDimensionPixelSize(
                    R.styleable.DonutGaugeView_dg_top_text_size,
                    topTextSize
                )
                topTextColor = getColor(R.styleable.DonutGaugeView_dg_top_text_color, topTextColor)
                bottomText = getString(R.styleable.DonutGaugeView_dg_bottom_text) ?: ""
                bottomTextColor =
                    getColor(R.styleable.DonutGaugeView_dg_bottom_text_color, bottomTextColor)
                bottomTextSize = getDimensionPixelSize(
                    R.styleable
                        .DonutGaugeView_dg_bottom_text_size, bottomTextSize
                )
                marginTopMiddleText = getDimensionPixelSize(
                    R.styleable
                        .DonutGaugeView_dg_top_middle_margin, marginTopMiddleText
                )
                marginMiddleBottomText = getDimensionPixelSize(
                    R.styleable
                        .DonutGaugeView_dg_middle_bottom_margin, marginMiddleBottomText
                )
                animationDuration = getInt(
                    R.styleable.DonutGaugeView_dg_anim_duration,
                    animationDuration.toInt()
                ).toLong()
                middleTextColor =
                    getColor(R.styleable.DonutGaugeView_dg_middle_text_color, middleTextColor)
                onExceedMiddleTextColor =
                    getColor(R.styleable.DonutGaugeView_dg_on_exceed_middle_text_color, middleTextColor)
                middleTextSize = getDimensionPixelSize(
                    R.styleable.DonutGaugeView_dg_middle_text_size,
                    middleTextSize
                )
            }
        }
    }
}
