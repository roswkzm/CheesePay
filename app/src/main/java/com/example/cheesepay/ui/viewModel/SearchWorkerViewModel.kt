package com.example.cheesepay.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cheesepay.model.WorkerDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchWorkerViewModel @Inject constructor(

) : ViewModel() {

    private val _workerData = MutableLiveData<List<WorkerDTO>>()
    val workerData: LiveData<List<WorkerDTO>> = _workerData

    private val db = Firebase.firestore
    private var workerList : ArrayList<WorkerDTO> = arrayListOf()
    
    fun getWorkerData(){
        db.collection("workers").addSnapshotListener { value, error ->
            workerList.clear()
            if (value == null) return@addSnapshotListener
            for (snapshot in value!!.documents){
                var item = snapshot.toObject(WorkerDTO::class.java)
                workerList.add(item!!)
            }
            _workerData.value = workerList
        }
    }
}