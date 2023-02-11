package com.example.cheesepay.ui.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cheesepay.R
import com.example.cheesepay.databinding.FragmentAddWorkerBinding
import com.example.cheesepay.model.WorkerDTO
import com.example.cheesepay.ui.viewModel.AddWorkerViewModel
import com.example.cheesepay.ui.viewModel.MainViewModel
import com.example.cheesepay.util.Constants.PICK_IMAGE_FROM_ALBUM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddWorkerFragment : Fragment() {

    private var _binding : FragmentAddWorkerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddWorkerViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()
    private var photoUri : Uri? = null

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
        subscribeUI()
    }

    private fun initUI() {
        binding.spWorkerType.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.worker_type_list, R.layout.support_simple_spinner_dropdown_item)

        binding.ivProfile.setOnClickListener {
            val photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, PICK_IMAGE_FROM_ALBUM)
        }

        binding.btnSave.setOnClickListener {

            val workerName = binding.etName.text.toString()
            val workerType = binding.spWorkerType.selectedItem.toString()

            workerName.let {
                viewModel.uploadWorkerImage(photoUri, it)
            }
        }
    }

    private fun subscribeUI() {
        viewModel.imageDownloadUrl.observe(viewLifecycleOwner, Observer { url ->
            val workerName = binding.etName.text.toString()
            val workerType = binding.spWorkerType.selectedItem.toString()
            val socialNumber = "${binding.frontSocialNumber.text}-${binding.backSocialNumber.text}"
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val workerInfo : WorkerDTO = if (url != null){
                WorkerDTO(workerName, socialNumber, phoneNumber, workerType, url)
            } else {
                WorkerDTO(workerName, socialNumber, phoneNumber, workerType, "")
            }
            viewModel.uploadWorkerInfo(workerInfo)
        })

        viewModel.isSaveSuccess.observe(viewLifecycleOwner, Observer { it ->
            val isSuccess = it.getContentIfNotHandled()
            if (isSuccess!!){
                findNavController().popBackStack()
                findNavController().navigate(R.id.calendarFragment)
            }
        })

        viewModel.toastErrorMsg.observe(this, Observer { msg ->
            activityViewModel.showErrorMsg(msg)
        })

        viewModel.isShowProgressBar.observe(this, Observer { it ->
            var isShow = it.getContentIfNotHandled()
            activityViewModel.isShowProgressBar(isShow!!)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM && resultCode == Activity.RESULT_OK){
            photoUri = data?.data
            Glide.with(this).load(photoUri).apply(RequestOptions().circleCrop()).into(binding.ivProfile)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}