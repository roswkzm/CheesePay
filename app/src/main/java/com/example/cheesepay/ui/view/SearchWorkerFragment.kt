package com.example.cheesepay.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.cheesepay.R
import com.example.cheesepay.databinding.FragmentSearchWorkerBinding
import com.example.cheesepay.ui.viewModel.SearchWorkerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchWorkerFragment : Fragment() {

    private var _binding : FragmentSearchWorkerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SearchWorkerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_worker, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}