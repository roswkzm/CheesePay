package com.example.cheesepay.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cheesepay.model.TaskDTO
import com.example.cheesepay.model.WorkerDTO
import com.example.cheesepay.util.Event
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(

) : ViewModel() {

    private val _workerNameListData = MutableLiveData<ArrayList<String>>()
    val workerNameListData: LiveData<ArrayList<String>> = _workerNameListData
    private val _isShowProgressBar = MutableLiveData<Event<Boolean>>()
    val isShowProgressBar: LiveData<Event<Boolean>> = _isShowProgressBar
    private val _showDialogMsg = MutableLiveData<Event<String>>()
    val showDialogMsg: LiveData<Event<String>> = _showDialogMsg

    private val db = Firebase.firestore
    private var workerNameList : ArrayList<String> = arrayListOf()

    fun getWorkerData(){
        db.collection("workers").addSnapshotListener { value, error ->
            workerNameList.clear()
            if (value == null) return@addSnapshotListener
            for (snapshot in value!!.documents){
                var item = snapshot.toObject(WorkerDTO::class.java)
                workerNameList.add(item!!.name)
            }
            _workerNameListData.value = workerNameList
        }
    }

    fun uploadTaskInfo(taskDTO : TaskDTO){
        _isShowProgressBar.value = Event(true)
        db.collection("tasks").document(taskDTO.date).collection(taskDTO.name).document()
            .set(taskDTO)
            .addOnSuccessListener {
                _showDialogMsg.value = Event("근무 기록 저장에 성공하였습니다.")
                _isShowProgressBar.value = Event(false)
            }
            .addOnFailureListener {
                _showDialogMsg.value = Event("근무 기록 저장에 실패하였습니다.")
                _isShowProgressBar.value = Event(false)
            }
    }
}