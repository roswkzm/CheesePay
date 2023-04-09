package com.example.cheesepay.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cheesepay.R
import com.example.cheesepay.databinding.FragmentSearchWorkerBinding
import com.example.cheesepay.ui.viewModel.SearchWorkerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchWorkerFragment : Fragment() {

    private var _binding : FragmentSearchWorkerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SearchWorkerViewModel>()
    lateinit var workerAdapter: SearchWorkerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_worker, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeUI()
    }

    private fun initUI(){
        workerAdapter = SearchWorkerAdapter()

        binding.rvWorker.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = workerAdapter
        }

        workerAdapter.setOnItemClickListener { it ->
            val action = SearchWorkerFragmentDirections.actionSearchWorkerFragmentToWorkerMontyPayFragment(it)
            findNavController().navigate(action)
        }

        viewModel.getWorkerData()
    }
    private fun subscribeUI(){
        viewModel.workerData.observe(viewLifecycleOwner, Observer { workerList ->
            workerAdapter.setWorkerData(workerList)
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}