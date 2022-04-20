package mario.hany.currency.ui.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import mario.hany.currency.domain.utils.ConnectionStateMonitor

abstract class BaseFragment: Fragment() {
    private var networkSnackBar: Snackbar? = null
    private lateinit var connectionLiveData : ConnectionStateMonitor

    abstract var functionsToBeLoaded:()->Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionStateMonitor(requireContext())
        connectionLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                functionsToBeLoaded.invoke()
                networkSnackBar?.setAction(null, null)
                    ?.setDuration(Snackbar.LENGTH_SHORT)
                    ?.setText("Back Online Again")
                    ?.show()
            } else {
                networkSnackBar = Snackbar.make(view, "No Internet Connection !!",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Setting") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
                        }else{
                            startActivity( Intent(Settings.ACTION_WIFI_SETTINGS))
                        }
                    }
                networkSnackBar?.show()
            }
        }
    }
}