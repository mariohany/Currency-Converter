package mario.hany.currency.ui.holders

import androidx.recyclerview.widget.LinearLayoutManager
import mario.hany.currency.R
import mario.hany.currency.data.local.RateHistoryEntity
import mario.hany.currency.data.models.HistorySection
import mario.hany.currency.databinding.ConvertItemBinding
import mario.hany.currency.databinding.HistorySectionItemBinding
import mario.hany.currency.ui.adapter.CustomAdapter
import mario.hany.currency.ui.base.BaseViewHolder

class HistorySectionViewHolder(private val binding: HistorySectionItemBinding): BaseViewHolder<HistorySection>(binding) {
    private val list: MutableList<RateHistoryEntity> by lazy { mutableListOf() }
    private val adapter: CustomAdapter<RateHistoryEntity, ConvertItemBinding> by lazy { CustomAdapter(list, R.layout.convert_item){
        ConvertHistoryViewHolder(it)
    } }
    override fun bind(model: HistorySection, position: Int, hasPlaceHolder: Boolean?) {
        binding.run {
            dateTitle.text = model.date
            list.clear()
            list.addAll(model.convertList)
            LinearLayoutManager(itemView.context).also {
                historyRv.layoutManager = it
                historyRv.adapter = adapter
            }
        }
    }
}