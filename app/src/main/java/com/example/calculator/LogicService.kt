package com.example.calculator

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class LogicService : Service() {
    private val mBinder : IBinder  = LocalBinder();

    inner class LocalBinder : Binder() {
         fun getService() : LogicService {
            return this@LogicService;
        }
    }
    fun add(n1:Double , n2:Double) : Double {
        return n1+n2
    }
    fun substract(n1:Double , n2:Double) : Double{
        return n1-n2
    }
    fun multiplication(n1:Double , n2:Double) : Double {
        return n1*n2
    }
    fun division(n1:Double , n2:Double): Double {
        return n1/n2
    }
    override fun onBind(intent: Intent): IBinder {
        return mBinder;
    }
}

