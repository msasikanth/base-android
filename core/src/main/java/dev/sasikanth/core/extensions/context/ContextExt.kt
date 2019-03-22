package dev.sasikanth.core.extensions.context

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Get color
 */
@ColorInt
inline fun Context.resolveColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

/**
 * Resolve a attribute value and return data
 */
inline fun Context.resolveAttr(
    @AttrRes attrRes: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrRes, typedValue, resolveRefs)
    return typedValue.data
}
