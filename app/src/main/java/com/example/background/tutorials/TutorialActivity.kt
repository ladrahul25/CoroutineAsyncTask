package com.example.background.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.background.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        Log.d("Alpha","debug: running ${Thread.currentThread().name} thread!")

        GlobalScope.launch {
            getResponseFromAPI_1()
            val job = launch(Dispatchers.IO) {
                getResponseFromAPI_2()
            }
            job.join()
        }
    }

    suspend fun getResponseFromAPI_1(): String{
        Log.d("Alpha","debug: calling api 1 from "+Thread.currentThread().name)
        delay(1000L) // like Thread.sleep but runs on thread pool without stopping main thread
        Log.d("Alpha","debug: 1 received response")
        return "Success"
    }

    suspend fun getResponseFromAPI_2(): String{
        Log.d("Alpha","debug: calling api 2 from "+Thread.currentThread().name)
        delay(1000L) // like Thread.sleep but runs on thread pool without stopping main thread
        Log.d("Alpha","debug: 2 received response")
        return "Success"
    }
}
