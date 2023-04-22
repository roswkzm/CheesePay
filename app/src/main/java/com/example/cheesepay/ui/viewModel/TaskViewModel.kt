package com.example.cheesepay.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cheesepay.model.TaskDTO
import com.example.cheesepay.model.WorkerDTO
import com.example.cheesepay.util.CommonUtil
import com.example.cheesepay.util.CommonUtil.Companion.YearMonthDayDateFormat
import com.example.cheesepay.util.Event
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class TaskViewModel @Inject constructor(
    val commonUtil: CommonUtil
) : ViewModel() {

    private val _workerNameListData = MutableLiveData<ArrayList<String>>()
    val workerNameListData: LiveData<ArrayList<String>> = _workerNameListData
    private val _isShowProgressBar = MutableLiveData<Event<Boolean>>()
    val isShowProgressBar: LiveData<Event<Boolean>> = _isShowProgressBar
    private val _showDialogMsg = MutableLiveData<Event<String>>()
    val showDialogMsg: LiveData<Event<String>> = _showDialogMsg
    private val _userTaskListData = MutableLiveData<ArrayList<TaskDTO>>()
    val userTaskListData: LiveData<ArrayList<TaskDTO>> = _userTaskListData


    // 작업자에 대한 월별 데이터 리스트
    private var workerMonthTaskList : ArrayList<TaskDTO> = arrayListOf()

    private val _workerMonthTaskListData = MutableLiveData<Event<ArrayList<TaskDTO>>>()
    val workerMonthTaskListData: LiveData<Event<ArrayList<TaskDTO>>> = _workerMonthTaskListData

    val gson : Gson = Gson()

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
        val yearMonthDate = commonUtil.getYearMonth(taskDTO.date)
        db.collection("tasks").document(yearMonthDate).collection(taskDTO.date).document(taskDTO.name)
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

    fun deleteTaskInfo(taskDTO: TaskDTO){
        _isShowProgressBar.value = Event(true)
        val yearMonthDate = commonUtil.getYearMonth(taskDTO.date)
        db.collection("tasks").document(yearMonthDate).collection(taskDTO.date).document(taskDTO.name)
            .delete()
            .addOnCompleteListener {
                _showDialogMsg.value = Event("근무 기록 삭제에 성공하였습니다.")
                _isShowProgressBar.value = Event(false)
            }
            .addOnFailureListener {
                _showDialogMsg.value = Event("근무 기록 삭제에 실패하였습니다.")
                _isShowProgressBar.value = Event(false)
            }
    }

    fun getSelectDateTask(selectDate : String){
        var selectDateTasks : ArrayList<TaskDTO> = arrayListOf()
        val yearMonthDate = commonUtil.getYearMonth(selectDate)
        db.collection("tasks").document(yearMonthDate).collection(selectDate).addSnapshotListener { value, error ->
            selectDateTasks.clear()
            if (value == null) return@addSnapshotListener
            for (snapshot in value.documents){
                selectDateTasks.add(snapshot.toObject(TaskDTO::class.java)!!)
            }
            _userTaskListData.value = selectDateTasks
        }
    }

    fun getWorkerTaskDataSelectMonth(selectMonth : String, workerName : String){
        workerMonthTaskList.clear()
        val abc = selectMonth.split("-")
        var calendar = Calendar.getInstance()
        calendar.set(abc[0].toInt(), abc[1].toInt() -1 , 1)
        val monthEndDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i : Int in 1..monthEndDate){
            val day = String.format("%02d", i)
            val selectDate = "${selectMonth}-${day}"
            db.collection("tasks").document(selectMonth).collection(selectDate).document(workerName).addSnapshotListener { value, error ->
                if (value == null) return@addSnapshotListener

                if (value.data != null){
                    if (value.data!!.isNotEmpty()){
                        // FireStore는 자동 업데이트를 지원함 -> 자동 업데이트시에 데이터가 꼬이는 문제 발생 -> 직접 스캔한것이 아니면 return 함
                        if (!value.toString().contains("SYNCED")){
                            return@addSnapshotListener
                        }
                        Log.d("${workerName}의 업무 : ", value.data!!.toString())
                        var taskDTO : TaskDTO = dataParseTaskDTO(value.data!!)
                        workerMonthTaskList.add(taskDTO)
                    }
                }
                if (i == monthEndDate){
                    _workerMonthTaskListData.value = Event(workerMonthTaskList)
                }
            }
        }
    }

    fun dataParseTaskDTO(data: MutableMap<String, Any>): TaskDTO {
        var taskDTO = TaskDTO()
        taskDTO.name = data["name"] as String
        taskDTO.extraDescription = data["extraDescription"] as String?
        taskDTO.extraPay = data["extraPay"] as Long?
        taskDTO.hourPay = data["hourPay"] as Long
        taskDTO.workHour = data["workHour"] as Long
        taskDTO.date = data["date"] as String
        taskDTO.totalPay = data["totalPay"] as Long
        return taskDTO
    }
}