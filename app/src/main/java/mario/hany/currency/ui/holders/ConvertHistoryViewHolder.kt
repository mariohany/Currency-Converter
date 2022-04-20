package mario.hany.currency.ui.holders

import mario.hany.currency.data.local.RateHistoryEntity
import mario.hany.currency.databinding.ConvertItemBinding
import mario.hany.currency.domain.utils.formatPrice
import mario.hany.currency.ui.base.BaseViewHolder

class ConvertHistoryViewHolder(private val binding: ConvertItemBinding): BaseViewHolder<RateHistoryEntity>(binding) {
    override fun bind(model: RateHistoryEntity, position: Int, hasPlaceHolder: Boolean?) {
        binding.run {
            fromCurTxt.text = model.fromCur
            toCurTxt.text = model.toCur
            fromValue.text = model.fromAmount.formatPrice()
            toValue.text = model.toAmount.formatPrice()
        }
    }
}