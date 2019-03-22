package dev.sasikanth.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import dev.sasikanth.core.R

class BaselineGridTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val fourDip: Float

    private var lineHeightMultiplierHint = 1f
    private var lineHeightHint = 0f
    private var maxLinesByHeight = false
    private var extraTopPadding = 0
    private var extraBottomPadding = 0

    init {
        val styleAttrs = context.obtainStyledAttributes(
            attrs, R.styleable.BaselineGridTextView, defStyleAttr, 0
        )

        lineHeightMultiplierHint = styleAttrs.getFloat(
            R.styleable.BaselineGridTextView_lineHeightMultiplierHint,
            1f
        )
        lineHeightHint = styleAttrs.getDimensionPixelSize(
            R.styleable.BaselineGridTextView_lineHeightHint,
            0
        ).toFloat()
        maxLinesByHeight = styleAttrs.getBoolean(R.styleable.BaselineGridTextView_maxLinesByHeight, false)
        styleAttrs.recycle()

        fourDip = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics
        )
        computeLineHeight()
    }

    override fun getCompoundPaddingTop(): Int =
    // include extra padding to place the first line's baseline on the grid
        super.getCompoundPaddingTop() + extraTopPadding

    override fun getCompoundPaddingBottom(): Int =
    // include extra padding to make the height a multiple of 4dp
        super.getCompoundPaddingBottom() + extraBottomPadding

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        extraTopPadding = 0
        extraBottomPadding = 0
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = measuredHeight
        height += ensureBaselineOnGrid()
        height += ensureHeightGridAligned(height)
        setMeasuredDimension(measuredWidth, height)
        checkMaxLines(height, MeasureSpec.getMode(heightMeasureSpec))
    }

    /**
     * Ensures line height is a multiple of 4dp.
     */
    private fun computeLineHeight() {
        val fm = paint.fontMetricsInt
        val fontHeight = Math.abs(fm.ascent - fm.descent) + fm.leading
        val desiredLineHeight = if (lineHeightHint > 0)
            lineHeightHint
        else
            lineHeightMultiplierHint * fontHeight

        val baselineAlignedLineHeight =
            (fourDip * Math.ceil((desiredLineHeight / fourDip).toDouble()).toFloat()).toInt()
        setLineSpacing((baselineAlignedLineHeight - fontHeight).toFloat(), 1f)
    }

    /**
     * Ensure that the first line of text sits on the 4dp grid.
     */
    private fun ensureBaselineOnGrid(): Int {
        val baseline = baseline.toFloat()
        val gridAlign = baseline % fourDip
        if (gridAlign != 0f) {
            extraTopPadding = (fourDip - Math.ceil(gridAlign.toDouble())).toInt()
        }
        return extraTopPadding
    }

    /**
     * Ensure that height is a multiple of 4dp.
     */
    private fun ensureHeightGridAligned(height: Int): Int {
        val gridOverhang = height % fourDip
        if (gridOverhang != 0f) {
            extraBottomPadding = (fourDip - Math.ceil(gridOverhang.toDouble())).toInt()
        }
        return extraBottomPadding
    }

    /**
     * When measured with an exact height, text can be vertically clipped mid-line. Prevent
     * this by setting the `maxLines` property based on the available space.
     */
    private fun checkMaxLines(height: Int, heightMode: Int) {
        if (!maxLinesByHeight || heightMode != MeasureSpec.EXACTLY) return

        val textHeight = height - compoundPaddingTop - compoundPaddingBottom
        val completeLines = Math.floor((textHeight / lineHeight).toDouble()).toInt()
        maxLines = completeLines
    }
}
