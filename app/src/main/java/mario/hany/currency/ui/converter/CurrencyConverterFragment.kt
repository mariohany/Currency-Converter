package mario.hany.currency.ui.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import mario.hany.currency.R
import mario.hany.currency.databinding.CurrencyConverterFragmentBinding
import mario.hany.currency.ui.base.BaseFragment
import mario.hany.currency.ui.details.DetailsFragment.Companion.BASE_CURRENCY
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyConverterFragment : BaseFragment() {

    private val viewModel: ConvertViewModel by viewModel()
    private var _binding: CurrencyConverterFragmentBinding? = null
    private val binding get() = _binding!!
    private var fromTextWatcher: TextWatcher? = null
    private var toTextWatcher: TextWatcher? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrencyConverterFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override var functionsToBeLoaded: () -> Unit = {
        viewModel.getRates()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindViews()
        bindObservers()
    }

    private fun bindViews() {
        _binding?.run {
            initFromTextWatcher()
            initToTextWatcher()

            swapBtn.setOnClickListener {
                val fromPos = fromSpinner.selectedItemPosition
                val toPos = toSpinner.selectedItemPosition
                val fromValue = fromEt.text
                val toValue = toEt.text
                fromSpinner.setSelection(toPos)
                toSpinner.setSelection(fromPos)
                fromEt.text = toValue
                toEt.text = fromValue
            }

            fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    val amount:Double = fromEt.text?.toString()?.replace(",","")?.toDoubleOrNull() ?:0.0
                    calculateRate(amount, true)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    val amount:Double = fromEt.text?.toString()?.replace(",","")?.toDoubleOrNull() ?:0.0
                    calculateRate(amount, true)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            detailsBtn.setOnClickListener {
                Bundle().also {
                    it.putString(BASE_CURRENCY, fromSpinner.selectedItem as String)
                    findNavController().navigate(R.id.convert_to_details, it)
                }
            }

            fromEt.setOnFocusChangeListener { _, b ->
                if (b){
                    toEt.removeTextChangedListener(toTextWatcher)
                    fromEt.removeTextChangedListener(fromTextWatcher)
                    fromEt.addTextChangedListener(fromTextWatcher)
                }
            }

            toEt.setOnFocusChangeListener { _, b ->
                if (b) {
                    fromEt.removeTextChangedListener(fromTextWatcher)
                    toEt.removeTextChangedListener(toTextWatcher)
                    toEt.addTextChangedListener(toTextWatcher)
                }
            }

            fromEt.performClick()
        }
    }

    fun calculateRate(amount:Double, isFromTxt:Boolean){
        _binding?.run {
            viewModel.calculateRate(
                fromSpinner.selectedItem as String,
                toSpinner.selectedItem as String,
                amount,
                isFromTxt
            )
        }
    }

    private fun initFromTextWatcher() {
        _binding?.run {
            fromTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(it: Editable?) {
                    val amount = it?.toString()?.replace(",","")?.toDoubleOrNull() ?:0
                    if (amount != 0) {
                        calculateRate(amount as Double, true)
                    } else {
                        toEt.setText("0")
                    }
                }
            }
        }
    }

    private fun initToTextWatcher() {
        _binding?.run {
            toTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(it: Editable?) {
                    val amount = it?.toString()?.replace(",","")?.toDoubleOrNull() ?: 0
                    if (amount != 0) {
                        calculateRate(amount as Double, false)
                    } else {
                        fromEt.setText("0")
                    }
                }
            }
        }
    }

    private fun bindObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is ConvertViewState.Loading -> {
                    _binding?.progress?.isVisible = true
                }
                is ConvertViewState.Rates -> {
                    calculateRate(1.0, true)
                }
                is ConvertViewState.SuccessFrom -> {
                    _binding?.progress?.isVisible = false
                    _binding?.fromEt?.setText(it.value.toString())
                }
                is ConvertViewState.SuccessTo -> {
                    _binding?.progress?.isVisible = false
                    _binding?.toEt?.setText(it.value.toString())
                }
                is ConvertViewState.Error -> {
                    _binding?.progress?.isVisible = false
                    it.msg?.let { msg ->
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}