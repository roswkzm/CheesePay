package com.example.cheesepay.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cheesepay.R
import com.example.cheesepay.databinding.ItemUserMonthTaskBinding
import com.example.cheesepay.databinding.ItemUserTaskBinding
import com.example.cheesepay.model.TaskDTO

class UserTaskAdapter(val fromFragmentName : String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var userTaskList : List<TaskDTO> = listOf()

    class CalendarViewHolder(val binding : ItemUserTaskBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    class WorkerMonthPayViewHolder(val binding : ItemUserMonthTaskBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            VIEW_TYPE_CALENDAR_FRAGMENT -> {
                val binding = DataBindingUtil.inflate<ItemUserTaskBinding>(LayoutInflater.from(parent.context)
                    , R.layout.item_user_task, parent, false)
                return CalendarViewHolder(binding)
            }
            VIEW_TYPE_WORKER_MONTH_PAY_FRAGMENT -> {
                val binding = DataBindingUtil.inflate<ItemUserMonthTaskBinding>(LayoutInflater.from(parent.context)
                    , R.layout.item_user_month_task, parent, false)
                return WorkerMonthPayViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemUserTaskBinding>(LayoutInflater.from(parent.context)
                    , R.layout.item_user_task, parent, false)
                return CalendarViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_CALENDAR_FRAGMENT){
            (holder as CalendarViewHolder).binding.userTask = userTaskList[position]
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(userTaskList[position]) }
            }
        }

        if (holder.itemViewType == VIEW_TYPE_WORKER_MONTH_PAY_FRAGMENT){
            (holder as WorkerMonthPayViewHolder).binding.userTask = userTaskList[position]
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(userTaskList[position]) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(fromFragmentName){
            CalendarFragment::class.java.simpleName -> VIEW_TYPE_CALENDAR_FRAGMENT
            WorkerMontyPayFragment::class.java.simpleName -> VIEW_TYPE_WORKER_MONTH_PAY_FRAGMENT
            else -> VIEW_TYPE_CALENDAR_FRAGMENT
        }
    }

    override fun getItemCount(): Int {
        return userTaskList.size
    }

    fun setTaskData(taskList : List<TaskDTO>){
        userTaskList = taskList
        notifyDataSetChanged()
    }

    private var onItemClickListener : ((TaskDTO) -> Unit)? = null
    fun setOnItemClickListener(listener : (TaskDTO) -> Unit){
        onItemClickListener = listener
    }

    companion object{
        private const val VIEW_TYPE_CALENDAR_FRAGMENT = 0
        private const val VIEW_TYPE_WORKER_MONTH_PAY_FRAGMENT = 1
    }
}