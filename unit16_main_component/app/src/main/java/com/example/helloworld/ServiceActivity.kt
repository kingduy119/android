package com.example.helloworld

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlin.random.Random

class ServiceActivity : AppCompatActivity() {
    private lateinit var btnMainActivity: Button
    private lateinit var btnContentActivity: Button
    private lateinit var btnStart: Button
    private lateinit var btnEnd: Button
    private lateinit var btnBind: Button

    private lateinit var mService: LocalService
    private var mBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LocalService.LocalBinder
            mService = binder.getService()
            mBound = true
        }
        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        title = "Service Activity"

        btnStart = findViewById(R.id.btnStartService)
        btnEnd = findViewById(R.id.btnEndService)
        btnBind = findViewById(R.id.btnBindService)
        btnMainActivity = findViewById(R.id.btnMain)
        btnContentActivity = findViewById(R.id.btnContent)

        btnStart.setOnClickListener {
            Intent(this, MyService::class.java).also { intent ->
                startService(intent)
            }
        }
        btnEnd.setOnClickListener {
            Intent(this, MyService::class.java).also { intent ->
                stopService(intent)
            }
        }
        btnBind.setOnClickListener {
            if(mBound) {
                val num = mService.randomNumber
                Toast.makeText(this, "number: $num", Toast.LENGTH_SHORT).show()
            }
        }
        // Move to activity
        btnMainActivity.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnContentActivity.setOnClickListener {
            var intent = Intent(this, ContentProviderActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ServiceActivity", "onStart")
        Intent(this, LocalService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        Log.d("ServiceActivity", "onStop")
        unbindService(connection)
        super.onStop()
    }
}

open class MyService : Service() {
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override  fun handleMessage(msg: Message) {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "onCreate")
        HandlerThread("MyService", 10).apply {
            start()
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand")
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }
        val message = intent?.getStringExtra("MyServiceKey")
        Log.d("MyServiceKey", message.toString())
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("MyService", "onBind")
        return null
    }

    override fun onDestroy() {
        Log.d("MyService", "onDestroy")
        super.onDestroy()
    }
}


class LocalService: Service() {
    private val binder = LocalBinder()
    private val mGenerator = Random
    val randomNumber: Int get() = mGenerator.nextInt(100)

    inner class LocalBinder: Binder() {
        fun getService(): LocalService = this@LocalService
    }

    override fun onCreate() {
        Log.d("LocalService", "onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("LocalService", "onBind")
        return binder
    }

    override fun onDestroy() {
        Log.d("LocalService", "onDestroy")
        super.onDestroy()
    }
}


