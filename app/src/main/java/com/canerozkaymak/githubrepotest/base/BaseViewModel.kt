package com.canerozkaymak.githubrepotest.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

abstract class BaseViewModel() : ViewModel() {
    protected val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    val errorMessage = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }

    //handling service errors
    fun handleError(error: Throwable) {
        if(error is HttpException){
            when(error.code()){
                404 -> errorMessage.value = "Username not found"
            }
        }
        else {
            errorMessage.value = error.message
        }
    }
}