package com.example.background

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.background.Constant.Status
import kotlinx.android.synthetic.main.activity_main.*
import com.example.background.R


class MainActivity : AppCompatActivity() {

    var task: MyAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startService(view: View) {
        if (task?.status == Status.RUNNING){
            task?.cancel(true)
        }
        task = MyAsyncTask(this)
        task?.execute(10)
    }

    class MyAsyncTask(private var activity: MainActivity?) : CoroutinesAsyncTask<Int, Int, String>("MysAsyncTask") {

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
            activity?.output?.text = result
            activity?.btn?.text = "Restart"
        }

        override fun onPreExecute() {
            activity?.output?.text = "Tast starting.."
            activity?.progressBar?.visibility = View.VISIBLE
            activity?.progressBar?.max = 10
            activity?.progressBar?.progress = 0

        }

        override fun onProgressUpdate(vararg values: Int?) {
            activity?.output?.text = "count is ${values.get(0).toString()}"
            values[0]?.let { activity?.progressBar?.setProgress(it) }
        }
    }
}
