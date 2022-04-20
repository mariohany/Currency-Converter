package mario.hany.currency.di

import mario.hany.currency.ui.converter.ConvertViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel{ ConvertViewModel(get()) }
}