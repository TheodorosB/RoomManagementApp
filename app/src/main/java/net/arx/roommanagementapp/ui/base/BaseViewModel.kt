package net.arx.roommanagementapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.arx.roommanagementapp.ui.livedata.LoadingLiveData

abstract class BaseViewModel : ViewModel() {

    protected fun launch(function: suspend () -> Unit): Job {
        return viewModelScope.launch {
            function.invoke()
        }
    }

    protected fun launchWithProgress(
        preload: suspend () -> Unit = {},
        postload: () -> Unit = {},
        function: suspend () -> Unit
    ) {
        viewModelScope.launch {
            preload.invoke()
            LoadingLiveData.postValue(true)
            function.invoke()
        }.apply {
            invokeOnCompletion {
                postload.invoke()
                LoadingLiveData.postValue(false)
            }
        }
    }

    protected fun launchWithProgressJob(
        preload: suspend () -> Unit = {},
        postload: () -> Unit = {},
        function: suspend () -> Unit
    ): Job {
        return viewModelScope.launch {
            preload.invoke()
            LoadingLiveData.postValue(true)
            function.invoke()
        }.apply {
            invokeOnCompletion {
                postload.invoke()
                LoadingLiveData.postValue(false)
            }
        }
    }
}