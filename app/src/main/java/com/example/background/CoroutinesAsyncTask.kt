package com.example.background.activities

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class CoroutinesAsyncTask<Params, Progress, Result>{

    var mStatus: Status = Status.PENDING
    abstract fun doInBackground(vararg params: Params?): Result
    open fun onProgressUpdate(vararg values: Progress?) {}
    open fun onPostExecute(result: Result?) {}
    open fun onPreExecute() {}
    open fun onCancelled(result: Result?) {}
    protected var isCancelled = false

    fun execute(vararg params: Params){

        if (mStatus != Status.PENDING) {
            when (mStatus) {
                Status.RUNNING -> throw IllegalStateException("Cannot execute task:" + " the task is already running.")
                Status.FINISHED -> throw IllegalStateException("Cannot execute task:"
                        + " the task has already been executed "
                        + "(a task can be executed only once)")
            }
        }

        mStatus = Status.RUNNING

        // it can be used to setup UI - it should have access to Main Thread
        GlobalScope.launch(Dispatchers.Main){
            onPreExecute()
        }

        // doInBackground works on background thread(default)
        GlobalScope.launch(Dispatchers.Default){
            val result = doInBackground(*params)
            mStatus = Status.FINISHED
            withContext(Dispatchers.Main){
                // onPostExecute works on main thread to show output
                Log.d("Alpha","after do in back "+mStatus.name+"--"+isCancelled)
                if (!isCancelled){onPostExecute(result)}
            }
        }
    }

    fun cancel(mayInterruptIfRunning : Boolean){
        isCancelled = true
        mStatus = Status.FINISHED
        GlobalScope.launch(Dispatchers.Main){
            // onPostExecute works on main thread to show output
            Log.d("Alpha","after cancel "+mStatus.name+"--"+isCancelled)
            onPostExecute(null)
        }
    }

    fun publishProgress(vararg progress: Progress) {
        //need to update main thread
        GlobalScope.launch(Dispatchers.Main){
            if (!isCancelled){
                onProgressUpdate(*progress)
            }
        }
    }

    enum class Status {
        PENDING,
        RUNNING,
        FINISHED
    }

}