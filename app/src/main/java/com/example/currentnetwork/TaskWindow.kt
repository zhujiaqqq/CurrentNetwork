package com.example.currentnetwork


import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
import android.widget.TextView
import java.lang.Exception

class TaskWindow {

    companion object {
        private lateinit var sWindowParams: WindowManager.LayoutParams
        private var sWindowManager: WindowManager? = null
        private lateinit var sView: View

        private fun init(context: Context) {
            sWindowManager =
                context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            var type: Int? = null

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N)
                type = WindowManager.LayoutParams.TYPE_TOAST
            else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                type = WindowManager.LayoutParams.TYPE_PHONE
            else
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            sWindowParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                type,
                FLAG_NOT_FOCUSABLE + FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
            )

            sWindowParams.gravity = Gravity.START + Gravity.TOP

            sView = LayoutInflater.from(context).inflate(R.layout.window_tasks, null)
            sWindowManager?.addView(sView, sWindowParams)
        }

        public fun show(context: Context, text: String) {
            if (sWindowManager == null) {
                init(context)
            }
            val textView = sView.findViewById<TextView>(R.id.text)
            textView.text = text
        }

        public fun dismiss() {
            try {
                sWindowManager?.removeView(sView)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }


}