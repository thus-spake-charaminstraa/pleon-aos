package com.charaminstra.pleon

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.charaminstra.pleon.adapter.CommonAdapter
import com.charaminstra.pleon.databinding.FragmentFeedWriteBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FeedWriteFragment : Fragment() {
    private lateinit var binding : FragmentFeedWriteBinding
    private val viewModel: PlantsViewModel by viewModels()
    //private lateinit var viewModel: FeedWriteViewModel
    private lateinit var bottomSheetDialog : BottomSheetDialog
    private lateinit var adapter: CommonAdapter
    private val cal = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedWriteBinding.inflate(layoutInflater)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        //binding.bottomSheet.plantRecyclerview = adapter
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
        binding.test.setOnClickListener {
            bottomSheetDialog.show()
        }
        binding.dateTv.text = dateFormat.format(cal.time)
        binding.dateTv.setOnClickListener {
            popUpCalendar(it as TextView)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()
        //binding.bottomSheet.plantRecyclerview.adapter = adapter
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

    private fun initList() {
        adapter = CommonAdapter()
        adapter.setType("FEED_PLANT")
        adapter.onItemClicked = { plantId ->
//            val bundle = Bundle()
//            bundle.putString("id", plantId)
            //navController.navigate(R.id.view_pager_fragment_to_plant_detail_fragment, bundle)
        }
    }

    private fun observeViewModel() {
        viewModel.plantsList.observe(viewLifecycleOwner, Observer {
            adapter.refreshItems(it)
        })
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        viewModel.loadData()
    }

}