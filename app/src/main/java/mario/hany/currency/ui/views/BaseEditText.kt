package mario.hany.currency.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import mario.hany.currency.R
import mario.hany.currency.domain.utils.getPxFromDp

open class BaseEditText : AppCompatEditText {
    constructor(context: Context): super(context)
    constructor(context: Context,attrs:AttributeSet?): super(context,attrs)
    constructor(context: Context,attrs: AttributeSet?,defStyleAttr:Int): super(context,attrs,defStyleAttr)

    init {
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        this.setLineSpacing(-10f, 0f)
        minHeight = context.getPxFromDp(36f)
        this.setPaddingRelative(
            context.getPxFromDp(12f),
            context.getPxFromDp(12f),
            context.getPxFromDp(12f),
            context.getPxFromDp(12f)
        )
        textSize = 12f
        background = ResourcesCompat.getDrawable(resources, R.drawable.edit_text_bg, null)
    }

    override fun setError(error: CharSequence?) {
        if(background != null)
            background = ResourcesCompat.getDrawable(resources, R.drawable.edit_text_error_bg, null)
        super.setError(null, null)
    }

    fun normalBg(){
        if(background != null)
            background = ResourcesCompat.getDrawable(resources, R.drawable.edit_text_bg, null)
    }

}