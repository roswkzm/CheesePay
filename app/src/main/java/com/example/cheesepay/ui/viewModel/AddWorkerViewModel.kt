package com.example.cheesepay.ui.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cheesepay.model.WorkerDTO
import com.example.cheesepay.util.Event
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddWorkerViewModel @Inject constructor(

) : ViewModel() {

    private val _toastErrorMsg = MutableLiveData<String>()
    val toastErrorMsg: LiveData<String> = _toastErrorMsg
    private val _imageDownloadUrl = MutableLiveData<String?>()
    val imageDownloadUrl: LiveData<String?> = _imageDownloadUrl
    private val _isShowProgressBar = MutableLiveData<Event<Boolean>>()
    val isShowProgressBar: LiveData<Event<Boolean>> = _isShowProgressBar
    private val _isSaveSuccess = MutableLiveData<Event<Boolean>>()
    val isSaveSuccess: LiveData<Event<Boolean>> = _isSaveSuccess

    fun uploadWorkerImage(photoUri : Uri?, workerName : String){
        _isShowProgressBar.value = Event(true)
        if (photoUri != null){
            var storageRef = FirebaseStorage.getInstance().reference.child("userProfileImages").child(workerName)
            storageRef.putFile(photoUri!!).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
                return@continueWithTask storageRef.downloadUrl
            }.addOnSuccessListener { uri ->
                _imageDownloadUrl.value = uri.toString()
            }.addOnFailureListener { error ->
                _toastErrorMsg.value = "사진 업로드에 실패하였습니다."
                _isShowProgressBar.value = Event(false)
            }
        } else {
            _imageDownloadUrl.value = null
        }
    }

    fun uploadWorkerInfo(workerInfo : WorkerDTO) {
        val db = Firebase.firestore
        db.collection("workers").document(workerInfo.name)
            .set(workerInfo)
            .addOnSuccessListener { documentReference ->
                _isSaveSuccess.value = Event(true)
            }
            .addOnFailureListener { e ->
                _toastErrorMsg.value = "직원정보 저장에 실패하였습니다."
            }
        _isShowProgressBar.value = Event(false)
    }
}