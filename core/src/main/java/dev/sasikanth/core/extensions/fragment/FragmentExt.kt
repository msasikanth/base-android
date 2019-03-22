package dev.sasikanth.core.extensions.fragment

import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import dev.sasikanth.core.extensions.context.resolveAttr
import dev.sasikanth.core.extensions.context.resolveColor

/**
 * Get color
 */
@ColorInt
inline fun Fragment.resolveColor(@ColorRes colorRes: Int): Int {
    return requireContext().resolveColor(colorRes)
}

/**
 * Resolve a attribute value and return data
 */
inline fun Fragment.resolveAttr(
    @AttrRes attrRes: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    return requireContext().resolveAttr(attrRes, typedValue, resolveRefs)
}
