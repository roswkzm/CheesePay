package com.example.cheesepay.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cheesepay.R
import com.example.cheesepay.databinding.DialogConfirmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmDialog : DialogFragment() {

    private var _binding : DialogConfirmBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ConfirmDialogArgs>()
    private val listener : BtnClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromWhere = args.fromWhere
        val message = args.message

        binding.tvMessage.text = message

        binding.btnOk.setOnClickListener {
            val action = ConfirmDialogDirections.actionConfirmDialogToCalendarFragment2()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    interface BtnClickListener{
        fun btnClick();
    }
}