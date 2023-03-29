package com.example.cheesepay.ui.view

import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.cheesepay.R
import com.example.cheesepay.databinding.FragmentCalendarBinding
import com.example.cheesepay.util.CommonUtil
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private var _binding : FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var calendarView : MaterialCalendarView

    @Inject
    lateinit var commonUtil: CommonUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeUI()
    }

    private fun initUI() {
        calendarView = binding.calendarView

        setCalendarView()
        binding.selectButton.setOnClickListener {
            if (isDateSelect()){
                Log.d("ㅎㅇㅎㅇ", calendarView.selectedDate.toString())
            } else{
                Toast.makeText(requireContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addButton.setOnClickListener {
            if (isDateSelect()){
                var selectDate = commonUtil.getSelectDateTime(calendarView.selectedDate.date)
                val action = CalendarFragmentDirections.actionCalendarFragmentToAddTaskFragment(selectDate)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isDateSelect(): Boolean {
        if (calendarView.selectedDates.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun setCalendarView(){

        val sundayDecorator = object : DayViewDecorator{
            private val calendar = Calendar.getInstance()
            override fun shouldDecorate(day: CalendarDay?): Boolean {
                day?.copyTo(calendar)
                val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
                return weekDay == Calendar.SUNDAY
            }
            override fun decorate(view: DayViewFacade?) {
                view?.addSpan(object:ForegroundColorSpan(Color.RED){})
            }
        }

        val saturdayDecorator = object : DayViewDecorator{
            private val calendar = Calendar.getInstance()
            override fun shouldDecorate(day: CalendarDay?): Boolean {
                day?.copyTo(calendar)
                val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
                return weekDay == Calendar.SATURDAY
            }
            override fun decorate(view: DayViewFacade?) {
                view?.addSpan(object:ForegroundColorSpan(Color.BLUE){})
            }
        }

        calendarView.apply {
            addDecorators(sundayDecorator, saturdayDecorator)
        }
    }

    private fun subscribeUI(){

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}