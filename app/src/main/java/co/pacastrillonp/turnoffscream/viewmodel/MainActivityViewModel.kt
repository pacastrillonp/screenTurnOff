package co.pacastrillonp.turnoffscream.viewmodel

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Context.*
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.provider.Settings.System.SCREEN_BRIGHTNESS
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import co.pacastrillonp.turnoffscream.utils.DeviceAdmin
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val context: Context) : ViewModel() {

    //    private lateinit var sensorManager: SensorManager
    private lateinit var powerManager: PowerManager
    //    private lateinit var windowManager: WindowManager
    private lateinit var wakeManager: PowerManager.WakeLock

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var activityManager: ActivityManager

    init {
        try {
            devicePolicyManager = (context.getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager?)!!

            activityManager = (context.getSystemService(ACTIVITY_SERVICE) as ActivityManager?)!!


            powerManager = (context.getSystemService(POWER_SERVICE) as PowerManager?)!!

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // Outputs
    private val _canTurnOnOutput = MediatorLiveData<Boolean>().apply {
        value = true
    }
    val canTurnOnOutput: LiveData<Boolean> get() = _canTurnOnOutput

    private val _canTurnOffOutput = MediatorLiveData<Boolean>().apply {
        value = true
    }
    val canTurnOffOutput: LiveData<Boolean> get() = _canTurnOffOutput


    fun turnOffScream() {
        try {
            val componentName = ComponentName(context, DeviceAdmin::class.java)
            val active = devicePolicyManager.isAdminActive(componentName)
            if (active) {
                devicePolicyManager.lockNow()
            } else {
                Toast.makeText(context, "You need to enable the Admin Device Features", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    fun turnOnScream() {
        try {
            powerManager = (context.getSystemService(POWER_SERVICE) as PowerManager?)!!
            wakeManager = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Your Tag")
            wakeManager.acquire(2000)
            wakeManager.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun brightnessOffScream() {


//        changeScreenBrightness(context, 0)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun brightnessOnScream() {
        changeScreenBrightness(context, 255)
    }

    private fun changeScreenBrightness(context: Context, screenBrightnessValue: Int) {
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        Settings.System.putInt(context.contentResolver, SCREEN_BRIGHTNESS, screenBrightnessValue)

    }

}