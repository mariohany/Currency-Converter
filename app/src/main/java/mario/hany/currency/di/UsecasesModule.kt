package mario.hany.currency.di

import mario.hany.currency.domain.usecase.convert.ConvertUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { ConvertUseCase(get()) }

}