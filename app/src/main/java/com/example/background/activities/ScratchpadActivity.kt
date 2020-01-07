package com.example.background.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.background.R
import kotlinx.coroutines.*

class ScratchpadActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scratchpad)
    }

    fun startExecution(view: View) {
        /*val jonNew = GlobalScope.launch {
            Log.d("Alpha","debug: step 1 "+Thread.currentThread().name)
            val result: Deferred<String> = async {
                Log.d("Alpha","debug: step 2 "+Thread.currentThread().name)
                getResultFromAPI()
            }
            val job = launch {
                Log.d("Alpha","debug: step 7 " +
                        ""+Thread.currentThread().name)
                val result2 = getResultFromAPI()
                Log.d("Alpha","debug: step 8 $result2 "+Thread.currentThread().name)
            }
            Log.d("Alpha","debug: step 4 "+Thread.currentThread().name)
            Log.d("Alpha","debug: setp 5 result="+result.await())
            job.join()
        }
        Log.d("Alpha","debug: step 6 "+Thread.currentThread().name)*/

        GlobalScope.launch {
            /*Log.d("Alpha","debug: step 0 "+Thread.currentThread().name)
            delay(1000L)
            Log.d("Alpha","debug: step 0.1 "+Thread.currentThread().name)
            val job = launch {
                getResultFromAPI()
            }
            Log.d("Alpha","debug: step 1 "+Thread.currentThread().name)
            job.join() // from here synchronous execution will start
            Log.d("Alpha","debug: step 2 "+Thread.currentThread().name)*/


            Log.d("Alpha","debug: step 1 "+Thread.currentThread().name)
            //val a = async { getResultFromAPI() }
            val a = getResultFromAPI()
            Log.d("Alpha","debug: step 2 "+Thread.currentThread().name)
            //val b = async { getResultFromAPI() }
            val b = getResultFromAPI()
            Log.d("Alpha","debug: step 3 "+Thread.currentThread().name)
            //val c = a.await() + b.await()
            val c = a + b
            Log.d("Alpha","debug: step 4 sum = $c "+Thread.currentThread().name)
        }
        Log.d("Alpha","debug: step 5 "+Thread.currentThread().name)
        //job.join()

    }

    suspend fun getResultFromAPI(): String {
        delay(1000L)
        return "Success"
    }

}