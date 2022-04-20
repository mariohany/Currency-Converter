package mario.hany.currency.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import mario.hany.currency.R
import mario.hany.currency.data.local.RateHistoryEntity
import mario.hany.currency.data.models.HistorySection
import mario.hany.currency.databinding.ConvertItemBinding
import mario.hany.currency.databinding.DetailsFragmentBinding
import mario.hany.currency.databinding.HistorySectionItemBinding
import mario.hany.currency.ui.adapter.CustomAdapter
import mario.hany.currency.ui.base.BaseFragment
import mario.hany.currency.ui.holders.ConvertHistoryViewHolder
import mario.hany.currency.ui.holders.HistorySectionViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment : BaseFragment() {
    private val viewModel: DetailsViewModel by viewModel()
    private var _binding: DetailsFragmentBinding? = null
    private var baseCurr: String? = null
    private val binding get() = _binding!!
    private val historyList: MutableList<HistorySection> by lazy { mutableListOf() }
    private val historyAdapter: CustomAdapter<HistorySection, HistorySectionItemBinding> by lazy { CustomAdapter(historyList, R.layout.history_section_item){
        HistorySectionViewHolder(it)
    } }

    private val suggestedList: MutableList<RateHistoryEntity> by lazy { mutableListOf() }
    private val suggestedAdapter: CustomAdapter<RateHistoryEntity, ConvertItemBinding> by lazy { CustomAdapter(suggestedList, R.layout.convert_item){
        ConvertHistoryViewHolder(it)
    } }

    override var functionsToBeLoaded: () -> Unit = {
        baseCurr?.let { viewModel.getSuggestedList(it) }
        viewModel.getHistoryList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(BASE_CURRENCY)?.let {
            baseCurr = it
            viewModel.getSuggestedList(it)
        }
        viewModel.getHistoryList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindViews()
        bindObservers()
    }

    private fun bindViews() {
        _binding?.run {
            LinearLayoutManager(requireContext()).also {
                historyRv.layoutManager = it
                historyRv.adapter = historyAdapter
            }

            LinearLayoutManager(requireContext()).also {
                suggestedRv.layoutManager = it
                suggestedRv.adapter = suggestedAdapter
            }
        }
    }

    private fun bindObservers() {
        viewModel.historyViewState.observe(viewLifecycleOwner){
            when(it){
                is HistoryViewState.Loading -> {
                    _binding?.progress?.isVisible = true
                    _binding?.normalView?.isVisible = false
                }
                is HistoryViewState.Suggested -> {
                    _binding?.progress?.isVisible = false
                    suggestedList.clear()
                    suggestedList.addAll(it.result)
                    suggestedAdapter.notifyDataSetChanged()
                    _binding?.normalView?.isVisible = true
                }
                is HistoryViewState.History -> {
                    _binding?.progress?.isVisible = false
                    historyList.clear()
                    val group = it.value.groupBy { it.date }
                    val temp = mutableListOf<HistorySection>()
                    group.forEach {
                        temp.add(HistorySection(it.key, it.value))
                    }
                    temp.sortByDescending { it.date }
                    if (temp.isNotEmpty() && temp.size >= 3) {
                        initChart(temp.subList(0, 3))
                        historyList.addAll(temp.subList(0, 3))
                    }else{
                        initChart(temp)
                        historyList.addAll(temp)
                    }
                    historyAdapter.notifyDataSetChanged()
                    _binding?.normalView?.isVisible = true
                }
                is HistoryViewState.Error -> {
                    _binding?.progress?.isVisible = false
                    _binding?.normalView?.isVisible = true
                    it.msg?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initChart(list: MutableList<HistorySection>) {
        _binding?.run {
            val cartesian: Cartesian = AnyChart.column()

            val data: MutableList<DataEntry> = ArrayList()
            list.forEach {
                data.add(ValueDataEntry(it.date, it.convertList.size))
            }

            val column: Column = cartesian.column(data)

            column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0.0)
                .offsetY(5.0)
                .format("{%Value}{groupsSeparator: }")

            cartesian.animation(true)

            cartesian.yScale().minimum(0.0)

            cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }")

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
            cartesian.interactivity().hoverMode(HoverMode.BY_X)

            cartesian.xAxis(0).title("Date")
            cartesian.yAxis(0).title("Count")

            anyChart.setChart(cartesian)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val BASE_CURRENCY = "BASE_CURRENCY"
    }
}