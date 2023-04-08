package com.example.cheesepay.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cheesepay.R
import com.example.cheesepay.databinding.ItemUserTaskBinding
import com.example.cheesepay.model.TaskDTO

class UserTaskAdapter() : RecyclerView.Adapter<UserTaskAdapter.CustomViewHolder>() {
    var userTaskList : List<TaskDTO> = listOf()

    class CustomViewHolder(val binding : ItemUserTaskBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemUserTaskBinding>(LayoutInflater.from(parent.context)
            , R.layout.item_user_task, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.userTask = userTaskList[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(userTaskList[position]) }
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
}