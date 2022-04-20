package mario.hany.currency.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(view: ViewBinding): RecyclerView.ViewHolder(view.root) {
    abstract fun bind(model: T, position: Int, hasPlaceHolder: Boolean?)
}