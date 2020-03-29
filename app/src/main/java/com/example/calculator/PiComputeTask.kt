package com.example.calculationsinbackground

import android.os.AsyncTask
import com.example.calculator.MainActivity
import kotlinx.android.synthetic.main.content_main.*
import java.lang.ref.WeakReference

class PiComputeTask internal constructor(ctx: MainActivity) : AsyncTask<Void, Int, Double>() {
    private val activityReference: WeakReference<MainActivity> = WeakReference(ctx)

    override fun doInBackground(vararg params: Void): Double {
        fun mcPi(n: Int): Double {
            var inside = 0
            (1..n).forEach { i ->
                publishProgress(i);
                val x = Math.random()
                val y = Math.random()
                if (x * x + y * y <= 1.0) inside++
            }
            return 4.0 * inside / n
        }

        return mcPi(1_000_00)
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        val activity = activityReference.get()
        if (activity == null || activity.isFinishing) return
        activity.progressBar.progress = values[0]!!
    }

    override fun onPostExecute(result: Double) {
        super.onPostExecute(result)
        val activity = activityReference.get()
        if (activity == null || activity.isFinishing) return
        activity.editText2.setText(result.toString())
    }
}