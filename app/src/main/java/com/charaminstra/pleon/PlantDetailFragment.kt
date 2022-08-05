package com.charaminstra.pleon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.calendar.DayViewContainer
import com.charaminstra.pleon.databinding.FragmentPlantDetailBinding
import com.charaminstra.pleon.plant_register.PlantIdViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import dagger.hilt.android.AndroidEntryPoint
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*


@AndroidEntryPoint
class PlantDetailFragment : Fragment() {
    private val viewModel: PlantIdViewModel by viewModels()
    private lateinit var binding : FragmentPlantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPlantDetailBinding.inflate(layoutInflater)
        lateinit var plantId : String
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        /*plant Id*/
        arguments?.getString("id")?.let {
            plantId = it
            viewModel.loadData(plantId)
            binding.editBtn.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id",plantId)
                navController.navigate(R.id.plant_detail_to_plant_edit_fragment,bundle)
            }
        }
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)
        binding.calendarMonth.text =currentMonth.toString()
        /* month set */
        binding.calendarView.monthScrollListener = {

        }
        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()


            }
        }
        binding.calendarMonthPrevBtn.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.let {
                binding.calendarView.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
        binding.calendarMonthNextBtn.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.let {
                binding.calendarView.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
        binding.calendarView.monthScrollListener = { month ->
            val title = "${monthTitleFormatter.format(month.yearMonth)} ${month.yearMonth.year}"
            binding.calendarMonth.text = title
//            selectedDate?.let {
//                // Clear selection if we scroll to a new month.
//                selectedDate = null
//                binding.exFiveCalendar.notifyDateChanged(it)
//                updateAdapterForDate(null)
//            }
        }



        viewModel.data.observe(this, Observer {
            binding.plantName.text = it.name
            Glide.with(binding.root)
                .load(it.thumbnail)
                .into(binding.plantImage)
            binding.plantSpeciesDesc.text = it.species
            binding.plantAdoptDayDesc.text = it.adopt_date
            binding.plantMood.text = "HAPPY"
            binding.plantDDayDesc.text = it.d_day.toString()

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


}