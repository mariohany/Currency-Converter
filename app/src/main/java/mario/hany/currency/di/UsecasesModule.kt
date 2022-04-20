package mario.hany.currency.di

import mario.hany.currency.domain.usecase.convert.ConvertUseCase
import mario.hany.currency.domain.usecase.history.HistoryUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { ConvertUseCase(get()) }
    single { HistoryUseCase(get()) }

}