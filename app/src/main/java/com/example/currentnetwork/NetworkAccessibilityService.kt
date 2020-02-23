package com.example.currentnetwork


import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.net.wifi.WifiManager
import android.view.accessibility.AccessibilityEvent


class NetworkAccessibilityService : AccessibilityService() {
    override fun onCreate() {
        super.onCreate()
        service = this
    }

    override fun onDestroy() {
        super.onDestroy()
        service = null
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val wifiManager: WifiManager =
            applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val connectionInfo = wifiManager.connectionInfo
        val info = StringBuilder()
        info.append("ip:").append(intIP2StringIP(connectionInfo.ipAddress)).append("\n")
        info.append("bssid:").append(connectionInfo.bssid).append("\n")
        info.append("ssid:").append(connectionInfo.ssid).append("\n")
        info.append("rssi:").append(connectionInfo.rssi)
        TaskWindow.show(applicationContext, info.toString())
    }


    companion object {
        var service: NetworkAccessibilityService? = null
        fun isStart(): Boolean {
            return service != null
        }

        /**
         * 将得到的int类型的IP转换为String类型
         *
         * @param ip
         * @return
         */
        fun intIP2StringIP(ip: Int): String? {
            return (ip and 0xFF).toString() + "." +
                    (ip shr 8 and 0xFF) + "." +
                    (ip shr 16 and 0xFF) + "." +
                    (ip shr 24 and 0xFF)
        }
    }

}
