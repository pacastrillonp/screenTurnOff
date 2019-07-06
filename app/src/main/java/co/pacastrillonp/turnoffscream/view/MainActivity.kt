package co.pacastrillonp.turnoffscream.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.PowerManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import co.pacastrillonp.turnoffscream.databinding.ActivityMainBinding
import co.pacastrillonp.turnoffscream.di.util.viewModelProvider
import co.pacastrillonp.turnoffscream.utils.DeviceAdmin
import co.pacastrillonp.turnoffscream.viewmodel.MainActivityViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject



class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var devicePolicyManager: DevicePolicyManager
    private val resultEnable = 11

    private var isActive: Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel = viewModelProvider(viewModelFactory)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, co.pacastrillonp.turnoffscream.R.layout.activity_main
        ).apply {
            viewModel = mainActivityViewModel
            lifecycleOwner = this@MainActivity
        }

        devicePolicyManager = (getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?)!!

        val componentName = ComponentName(this, DeviceAdmin::class.java)
        isActive = devicePolicyManager.isAdminActive(componentName)
        if (!isActive) {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Additional text explaining why we need this permission"
            )
            startActivityForResult(intent, resultEnable)
        }


    }

    override fun onPause() {
        super.onPause()
        val handler = Handler()
        handler.postDelayed({
            turnOnScream()
        }, 10000)
    }

    @SuppressLint("InvalidWakeLockTag", "WakelockTimeout")
    fun turnOnScream() {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val appKeyguardManager = keyguardManager.newKeyguardLock("MyKeyguardLock")
        appKeyguardManager.disableKeyguard()

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK
                    or PowerManager.ACQUIRE_CAUSES_WAKEUP
                    or PowerManager.ON_AFTER_RELEASE, "MyWakeLock"
        )
        wakeLock.acquire()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            resultEnable -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this@MainActivity, "You have enabled the Admin Device features", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this@MainActivity, "Problem to enable the Admin Device features", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
