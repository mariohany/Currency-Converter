package mario.hany.currency.ui.views

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import mario.hany.currency.domain.utils.getPxFromDp
import mario.hany.currency.domain.utils.replaceArabicNumbers
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

open class CurrencyEditText : BaseEditText {
    private val maxLength: Int = 10
    private var previousCleanString: String? = null
    constructor(context: Context): super(context)
    constructor(context: Context,attrs:AttributeSet?): super(context,attrs)
    constructor(context: Context,attrs: AttributeSet?,defStyleAttr:Int): super(context,attrs,defStyleAttr)

    private val watcher: TextWatcher = doAfterTextChanged {
        val str = it.toString()
        val cleanString = str.replace("[,]".toRegex(), "")
        if (cleanString == previousCleanString || cleanString.isEmpty()) {
            return@doAfterTextChanged
        }
        previousCleanString = cleanString
        val formattedString: String = formatInteger(cleanString)
        setText(formattedString)
        handleSelection()
    }

    init {
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        this.setLineSpacing(-10f, 0f)
        gravity = 0x10
        minHeight = context.getPxFromDp(36f)
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        param.marginStart = 18
        param.marginEnd = 18
        layoutParams = param
        this.setPaddingRelative(
            context.getPxFromDp(12f),
            context.getPxFromDp(12f),
            context.getPxFromDp(12f),
            context.getPxFromDp(12f)
        )
        textSize = 14f
        inputType = InputType.TYPE_CLASS_PHONE
        filters = arrayOf(InputFilter.LengthFilter(maxLength))
        keyListener = DigitsKeyListener.getInstance("0123456789٠١٢٣٤٥٦٧٨٩")
    }

    override fun getText(): Editable? {
        return super.getText().replaceArabicNumbers()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            addTextChangedListener(watcher)
        } else {
            removeTextChangedListener(watcher)
        }
    }

    private fun formatInteger(str: String): String {
        return try {
            val parsed = BigDecimal(str)
            val formatter = DecimalFormat("#,###,###.##", DecimalFormatSymbols(Locale.US))
            formatter.format(parsed)
        }catch (e:Exception){
            ""
        }
    }

    private fun handleSelection() {
        if (text?.length ?:0 <= maxLength) {
            setSelection(text?.length ?: 0)
        } else {
            setSelection(maxLength)
        }
    }
}