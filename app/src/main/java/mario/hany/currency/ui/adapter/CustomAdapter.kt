package mario.hany.currency.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import mario.hany.currency.ui.base.BaseViewHolder

class CustomAdapter<T,Y : ViewBinding>(var list: List<T>,
                                       private val design: Int, var itemCLickListener: ((T) -> Unit)?= null,
                                       val viewHolderFactory: (Y) -> BaseViewHolder<T>
): RecyclerView.Adapter<BaseViewHolder<T>>() {

    private lateinit var binding: ViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), design, parent, false)
        return viewHolderFactory(binding as Y)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(list[position], position, false)
        holder.itemView.setOnClickListener {
            itemCLickListener?.invoke(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}