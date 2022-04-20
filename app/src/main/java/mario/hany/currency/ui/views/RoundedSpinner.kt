package mario.hany.currency.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.res.ResourcesCompat
import mario.hany.currency.R

open class RoundedSpinner : AppCompatSpinner, View.OnClickListener {
    constructor(context: Context): super(context)
    constructor(context: Context,attrs:AttributeSet?): super(context,attrs)
    constructor(context: Context,attrs: AttributeSet?,defStyleAttr:Int): super(context,attrs,defStyleAttr)

    init {
        background = ResourcesCompat.getDrawable(resources, R.drawable.new_spinner_shape, null)
    }

    fun setError() {
        if(background != null)
            background = ResourcesCompat.getDrawable(resources, R.drawable.new_spinner_shape_error, null)
    }

    open fun normalBg(){
        if(background != null)
            background = ResourcesCompat.getDrawable(resources, R.drawable.new_spinner_shape, null)
    }

    override fun onClick(p0: View?) {
        normalBg()
    }

}