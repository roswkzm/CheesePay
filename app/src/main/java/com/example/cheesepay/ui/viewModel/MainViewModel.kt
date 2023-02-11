package com.example.cheesepay.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cheesepay.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _showErrorMsg = MutableLiveData<String>()
    val showErrorMsg: LiveData<String> = _showErrorMsg
    private val _isShowProgressBar = MutableLiveData<Event<Boolean>>()
    val isShowProgressBar: LiveData<Event<Boolean>> = _isShowProgressBar

    fun showErrorMsg(errorMessage : String) {
        _showErrorMsg.value = errorMessage
    }

    fun isShowProgressBar(isShow : Boolean) {
        _isShowProgressBar.value = Event(isShow)
    }
}