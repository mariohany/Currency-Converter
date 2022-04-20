package mario.hany.currency.di

import mario.hany.currency.data.repo.CurrencyRepo
import mario.hany.currency.domain.repo.ICurrencyRepo
import org.koin.dsl.module

val repositoriesModule = module {
    single<ICurrencyRepo> { CurrencyRepo(get(), get()) }
}