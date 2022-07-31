package com.charaminstra.pleon

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.charaminstra.pleon.databinding.FragmentFeedBinding
import com.charaminstra.pleon.databinding.FragmentFeedWriteBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class FeedWriteFragment : Fragment() {
    private lateinit var binding : FragmentFeedWriteBinding

    private lateinit var viewModel: FeedWriteViewModel
    private lateinit var bottomSheetDialog : BottomSheetDialog
    private val cal = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedWriteBinding.inflate(layoutInflater)
        binding.test.setOnClickListener {
            bottomSheetDialog.show()
        }
        binding.dateTv.text = dateFormat.format(cal.time)
        binding.dateTv.setOnClickListener {
            popUpCalendar(it as TextView)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedWriteViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun popUpCalendar(view: TextView) {


        var datePickerDialog = DatePickerDialog(requireContext(), { _, y, m, d ->
            cal.set(y,m,d)
            view.text = dateFormat.format(cal.time)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).apply {
            datePicker.maxDate = cal.timeInMillis
        }
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.BLACK)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLACK)
    }

}