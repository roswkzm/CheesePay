package com.example.cheesepay.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.cheesepay.R
import com.example.cheesepay.databinding.FragmentSearchWorkerBinding
import com.example.cheesepay.databinding.FragmentWorkerMontyPayBinding
import com.example.cheesepay.model.WorkerDTO

class WorkerMontyPayFragment : Fragment() {

    private var _binding : FragmentWorkerMontyPayBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<WorkerMontyPayFragmentArgs>()
    private lateinit var workerInfo : WorkerDTO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_worker_monty_pay, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workerInfo = args.workerInfo
        Log.d("ㅎㅇㅎㅇ", workerInfo.name)

        initUI()
        subscribeUI()
    }

    private fun initUI() {

    }

    private fun subscribeUI() {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}