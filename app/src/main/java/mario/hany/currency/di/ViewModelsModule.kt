package mario.hany.currency.di

import mario.hany.currency.ui.converter.ConvertViewModel
import mario.hany.currency.ui.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel{ ConvertViewModel(get()) }
    viewModel{ DetailsViewModel(get(), get()) }
}