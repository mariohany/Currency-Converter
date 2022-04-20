package mario.hany.currency

import android.app.Application
import mario.hany.currency.di.networkModule
import mario.hany.currency.di.repositoriesModule
import mario.hany.currency.di.useCasesModule
import mario.hany.currency.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CurrencyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CurrencyApplication)
            modules(
                listOf(
                    networkModule, repositoriesModule, viewModelsModule, useCasesModule
                )
            )
        }
    }
}