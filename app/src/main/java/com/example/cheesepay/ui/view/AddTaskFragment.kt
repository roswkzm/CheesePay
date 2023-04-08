package com.example.cheesepay.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cheesepay.R
import com.example.cheesepay.databinding.FragmentAddTaskBinding
import com.example.cheesepay.model.TaskDTO
import com.example.cheesepay.ui.viewModel.TaskViewModel
import com.example.cheesepay.util.CommonUtil
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.round

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private var _binding : FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddTaskFragmentArgs>()
    private val viewModel by activityViewModels<TaskViewModel>()

    @Inject
    lateinit var commonUtil: CommonUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeUI()
    }

    private fun initUI(){
        // 작업일자 세팅
        binding.tvDate.text = args.selectDate
        // 직원 선택 스피너 세팅
        viewModel.getWorkerData()
        // 시간 선택 스피너 세팅
        binding.spSelectHour.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.integer_array, R.layout.support_simple_spinner_dropdown_item)

        binding.btnSave.setOnClickListener {
            saveTask()
        }

        setEditTextChangeListener()
    }

    private fun subscribeUI(){
        viewModel.workerNameListData.observe(viewLifecycleOwner, Observer { workerNameList ->
            var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, workerNameList)
            binding.spSelectWorker.adapter = adapter
        })

        viewModel.showDialogMsg.observe(this, Observer { it ->
            it.getContentIfNotHandled()?.let { message ->
                val action = AddTaskFragmentDirections.actionAddTaskFragmentToConfirmDialog(message, AddTaskFragment::class.java.simpleName)
                findNavController().navigate(action)
            }
        })

        viewModel.isShowProgressBar.observe(this, Observer { it ->
            it.getContentIfNotHandled()?.let { isShow ->
                if (isShow){
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun getHourPay() = binding.etHourPay.text.toString()
    private fun getWorkHour() = binding.spSelectHour.selectedItem.toString()
    private fun getExtraPay() = binding.etExtraPay.text.toString()
    private fun getWithHoldingTax() = binding.tvWithholdingTax.text.toString()

    private fun saveTask(){
        if (!isDataAllSet()){
            Toast.makeText(requireContext(), "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val taskDTO : TaskDTO = TaskDTO(
            binding.tvDate.text.toString(),
            binding.spSelectWorker.selectedItem.toString(),
            getHourPay().toInt(),
            getWorkHour().toInt(),
            getExtraPay().toInt(),
            binding.etExtraDescription.text.toString(),
            binding.tvTotalPay.text.toString().toInt()
        )

        viewModel.uploadTaskInfo(taskDTO)
    }

    private fun setEditTextChangeListener() {
        binding.etHourPay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                calculateTax()
                setTotalPay()
            }

        })

        binding.spSelectHour.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                calculateTax()
                setTotalPay()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.etExtraPay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                setTotalPay()
            }

        })
    }

    private fun calculateTax(){
        try {
            binding.tvWithholdingTax.text = commonUtil.getWithHoldingTax(getHourPay().toInt(), getWorkHour().toInt()).toString()
        } catch (e : Exception) {
            binding.tvWithholdingTax.text = "???"
        }
    }

    private fun isDataAllSet() : Boolean {
        if (!getHourPay().isNullOrEmpty() && !getWorkHour().isNullOrEmpty() && !getExtraPay().isNullOrEmpty()){
            return true
        }
        return false
    }

    private fun setTotalPay(){
        if (isDataAllSet()){
            binding.tvTotalPay.text = ((getHourPay().toInt() * getWorkHour().toInt()).plus(getExtraPay().toInt()).minus(getWithHoldingTax().toInt())).toString()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}