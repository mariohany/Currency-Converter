package mario.hany.currency.domain.utils

import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.util.TypedValue
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Context.getPxFromDp(dp: Float): Int {
    val r: Resources = this.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics).toInt()
}

fun Editable?.replaceArabicNumbers(): Editable? {
    this?.toString()?.replace("٠", "0")
        ?.replace("١", "1")
        ?.replace("٢", "2")
        ?.replace("٣", "3")
        ?.replace("٤", "4")
        ?.replace("٥", "5")
        ?.replace("٦", "6")
        ?.replace("٧", "7")
        ?.replace("٨", "8")
        ?.replace("٩", "9")
    return this
}

fun getCurrentDate():String{
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
}

fun Double?.formatPrice(): String {
    return try {
        val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
        val formatter: DecimalFormat = numberFormat as DecimalFormat
        formatter.applyPattern("#,###,###.##")
        formatter.format(this)
    } catch (ex: NumberFormatException) {
        this.toString()
    }
}