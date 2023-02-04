package com.example.cheesepay.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.cheesepay.R
import com.example.cheesepay.databinding.FragmentAddWorkerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddWorkerFragment : Fragment() {

    private var _binding : FragmentAddWorkerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_worker, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}