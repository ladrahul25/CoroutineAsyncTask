package com.example.background.activities

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import com.example.background.R


class MainActivity : AppCompatActivity() {

    var task: MyAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.max = 10
    }

    fun startService(view: View) {
        //if (task?.status == AsyncTask.Status.RUNNING){
        if (task?.mStatus == CoroutinesAsyncTask.Status.RUNNING){
            task?.cancel(true)
        }
        task = MyAsyncTask(this)
        task?.execute(10)
    }

    class MyAsyncTask(private var activity: MainActivity?) : CoroutinesAsyncTask<Int, Int, String>() {


        override fun doInBackground(vararg params: Int?): String {
            for (count in 1..10){
                if (isCancelled)
                    break
                try {
                    Thread.sleep(1000)
                    publishProgress(count)
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
            return "Done!!!"
        }

        override fun onPostExecute(result: String?) {
            activity?.progressBar?.visibility = View.GONE
            activity?.output?.setText(result)
            activity?.btn?.setText("Restart")
        }

        override fun onPreExecute() {
            activity?.output?.setText("Tast starting..")
            activity?.progressBar?.visibility = View.VISIBLE
            activity?.progressBar?.progress = 0

        }

        override fun onProgressUpdate(vararg values: Int?) {
            activity?.output?.setText("count is ${values.get(0).toString()}")
            values[0]?.let { activity?.progressBar?.setProgress(it) }
        }

        override fun onCancelled(result: String?) {
            super.onCancelled(result)
        }
    }
}
