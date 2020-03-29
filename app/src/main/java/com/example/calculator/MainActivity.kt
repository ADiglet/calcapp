package com.example.calculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.BIND_AUTO_CREATE

import android.content.Intent

import android.widget.Toast

import android.content.ComponentName
import android.content.Context

import android.os.IBinder

import android.content.ServiceConnection

import androidx.core.app.ComponentActivity

import androidx.core.app.ComponentActivity.ExtraData

import androidx.core.content.ContextCompat.getSystemService

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.example.calculationsinbackground.PiComputeTask
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        addButton.setOnClickListener {
            if (mBound) {
                var number1 = editText2.text.toString().toDoubleOrNull()
                var number2 = editText3.text.toString().toDoubleOrNull()
                if (number1 != null && number2 !=null) {

                    editText4.setText(logicService.add(number1, number2).toString())
                }

            }
        }
        subButton.setOnClickListener {
            if (mBound) {
                var number1 = editText2.text.toString().toDoubleOrNull()
                var number2 = editText3.text.toString().toDoubleOrNull()
                if (number1 != null && number2 !=null) {

                    editText4.setText(logicService.substract(number1, number2).toString())
                }

            }
        }
        divButton.setOnClickListener {
            if (mBound) {
                var number1 = editText2.text.toString().toDoubleOrNull()
                var number2 = editText3.text.toString().toDoubleOrNull()
                if (number1 != null && number2 !=null) {

                    editText4.setText(logicService.division(number1, number2).toString())
                }

            }
        }
        mulButton.setOnClickListener {
            if (mBound) {
                var number1 = editText2.text.toString().toDoubleOrNull()
                var number2 = editText3.text.toString().toDoubleOrNull()
                if (number1 != null && number2 !=null) {

                    editText4.setText(logicService.multiplication(number1, number2).toString())
                }

            }

        }
        piButton.setOnClickListener {
            progressBar.progress = 0
            PiComputeTask(this).execute();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    private lateinit var logicService:LogicService;
    private var mBound : Boolean = false;
    private val logicConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className : ComponentName ,  service: IBinder) {
            var binder : LogicService.LocalBinder = service as LogicService.LocalBinder ;
            logicService = binder.getService();
            mBound = true;
            Toast.makeText(this@MainActivity, "Logic Service Connected!", Toast.LENGTH_SHORT).show();
    }
    override fun onServiceDisconnected(className : ComponentName ) {
        mBound = false;
        Toast.makeText(this@MainActivity, "Logic Service Disconnected!", Toast.LENGTH_SHORT).show();
        }
    };
    override fun onStart() {
        super.onStart();
        if (!mBound) {
            Intent(this, LogicService::class.java).also { intent ->
                bindService(intent, logicConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }
    override fun onStop() {
        super.onStop();
        if (mBound) {
            mBound = false;
                unbindService(logicConnection);
        }
    }


}