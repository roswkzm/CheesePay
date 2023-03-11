package com.example.cheesepay.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cheesepay.R
import com.example.cheesepay.databinding.ItemSearchWorkerBinding
import com.example.cheesepay.model.WorkerDTO

class SearchWorkerAdapter() : RecyclerView.Adapter<SearchWorkerAdapter.CustomViewHolder>() {

    private var workerList : List<WorkerDTO> = listOf()

    class CustomViewHolder(val binding : ItemSearchWorkerBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemSearchWorkerBinding>(LayoutInflater.from(parent.context), R.layout.item_search_worker, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.worker = workerList[position]
    }

    override fun getItemCount(): Int {
        return workerList.size
    }

    fun setWorkerData(workerList : List<WorkerDTO>){
        this.workerList = workerList;
        notifyDataSetChanged()
    }

}