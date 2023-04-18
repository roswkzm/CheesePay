package com.example.cheesepay.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.cheesepay.R
import com.example.cheesepay.databinding.FragmentWorkerMontyPayBinding
import com.example.cheesepay.model.WorkerDTO
import com.example.cheesepay.ui.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager


class WorkerMontyPayFragment : Fragment() {

    private var _binding : FragmentWorkerMontyPayBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<WorkerMontyPayFragmentArgs>()
    private val viewModel by activityViewModels<TaskViewModel>()
    private lateinit var workerInfo : WorkerDTO
    private lateinit var yearMonthPickerDialog : YearMonthPickerDialog
    private lateinit var calendar : Calendar
    lateinit var userTaskAdapter : UserTaskAdapter

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
        binding.worker = workerInfo

        initUI()
        subscribeUI()
    }

    private fun initUI() {
        binding.btnSelectMonth.setOnClickListener {
            yearMonthPickerDialog.show()
        }

        setYearMonthPickerDialog()

        userTaskAdapter = UserTaskAdapter(WorkerMontyPayFragment::class.java.simpleName)
        binding.rvMonthTask.apply {
            adapter = userTaskAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        // 근무기록 RecyclerView Item 클릭시 수정 페이지로 이동
        userTaskAdapter.setOnItemClickListener {
            val action = WorkerMontyPayFragmentDirections.actionWorkerMontyPayFragmentToModifyTaskFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setYearMonthPickerDialog(){
        calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM")

        yearMonthPickerDialog = YearMonthPickerDialog(requireContext()
        ) { year, month ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)

            binding.btnSelectMonth.text = dateFormat.format(calendar.time)
            binding.tvSelectMonth.text = dateFormat.format(calendar.time)

            viewModel.getWorkerTaskDataSelectMonth(dateFormat.format(calendar.time), workerInfo.name)
        }
    }

    private fun subscribeUI() {
        viewModel.workerMonthTaskListData.observe(viewLifecycleOwner, Observer { taskList ->
            userTaskAdapter.setTaskData(taskList)
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}