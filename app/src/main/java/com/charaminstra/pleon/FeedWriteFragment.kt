package com.charaminstra.pleon

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.databinding.FragmentFeedWriteBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.adapter.*
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.plant_register.PlantIdViewModel


@AndroidEntryPoint
class FeedWriteFragment : Fragment() {
    private lateinit var binding : FragmentFeedWriteBinding
    private val TAG = javaClass.name
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val plantIdViewModel: PlantIdViewModel by viewModels()
    private val feedWriteViewModel : FeedWriteViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var plant_adapter: PlantAdapter
    private lateinit var action_adapter: ActionAdapter
    private val cal = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private lateinit var sheetBehavior : BottomSheetBehavior<View>
    private var clickCount = 0

    private lateinit var plantId : String
    private lateinit var plantAction: ActionType
    private var url : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFeedWriteBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()

        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
        sheetBehavior.isHideable=false
        sheetBehavior.setPeekHeight(60)
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        sheetBehavior.addBottomSheetCallback(BSCB)

        binding.plantTagTv.setOnClickListener(SOCL)
        binding.actionTagTv.setOnClickListener(SOCL)
        binding.dateTv.text = dateFormat.format(cal.time).toString()
        binding.dateTv.setOnClickListener {
            popUpCalendar(it as TextView)
        }
        binding.cameraBtn.setOnClickListener{
            binding.image.visibility = View.VISIBLE
        }
        binding.completeBtn.setOnClickListener {
            feedWriteViewModel.postFeed(
                plantId,
                binding.dateTv.text.toString(),
                actionToKindType(plantAction).toString(),
                binding.contentEdit.text.toString(),
                url
            )
        }
        return binding.root
    }
    val BSCB : BottomSheetBehavior.BottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            // newState = 상태값
            when(newState) {
                // 사용자가 BottomSheet를 위나 아래로 드래그 중인 상태
                BottomSheetBehavior.STATE_DRAGGING -> { }
                // 드래그 동작 후 BottomSheet가 특정 높이로 고정될 때의 상태
                // SETTLING 후 EXPANDED, SETTLING 후 COLLAPSED, SETTLING 후 HIDDEN
                BottomSheetBehavior.STATE_SETTLING -> {
                    binding.bottomSheet.bottomSheetArrow.setImageResource(R.drawable.ic_bottom_sheet_up);
                }
                // 최대 높이로 보이는 상태
                BottomSheetBehavior.STATE_EXPANDED -> {
                    binding.bottomSheet.bottomSheetArrow.setImageResource(R.drawable.ic_bottom_sheet_down);
                }
                // peek 높이 만큼 보이는 상태
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    binding.bottomSheet.bottomSheetArrow.setImageResource(R.drawable.ic_bottom_sheet_up);
                }
                // 숨김 상태
                BottomSheetBehavior.STATE_HIDDEN -> { }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }
    val SOCL : View.OnClickListener = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private val recyclerListener = object : RecyclerView.OnItemTouchListener {
        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            //To change body of created functions use File | Settings | File Templates.
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            if(e.action == MotionEvent.ACTION_MOVE){

            }
            else{
                var child = rv.findChildViewUnder(e.getX(), e.getY())
                if(child != null){
                    var position = rv.getChildAdapterPosition(child)
                    var view = rv.layoutManager?.findViewByPosition(position)
                    view?.setBackgroundResource(R.color.button_bg)
                    for(i in 0..rv.adapter!!.itemCount){
                        var otherView = rv.layoutManager?.findViewByPosition(i)
                        if(otherView != view){
                            otherView?.setBackgroundResource(R.color.transparent)
                        }
                        else{

                        }
                    }
                }
            }
            return false
        }
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            //To change body of created functions use File | Settings | File Templates.
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()
        binding.bottomSheet.plantRecyclerview.adapter = plant_adapter
        binding.bottomSheet.actionRecyclerview.adapter= action_adapter

        /**/
        binding.bottomSheet.plantRecyclerview.addOnItemTouchListener(recyclerListener)
        binding.bottomSheet.actionRecyclerview.addOnItemTouchListener(recyclerListener)

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
        plant_adapter = PlantAdapter()
        plant_adapter.setType("FEED_PLANT")
        plant_adapter.onItemClicked = { plantId ->
            plantIdViewModel.loadData(plantId)
            clickCount++
            if(clickCount>=2){
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
//            val bundle = Bundle()
//            bundle.putString("id", plantId)
//            navController.navigate(R.id.view_pager_fragment_to_plant_detail_fragment, bundle)
        }

        action_adapter = ActionAdapter()
        action_adapter.refreshItems(
            listOf(
                ActionObject(ActionType.물,""),
                ActionObject(ActionType.통풍,"") ,
                ActionObject(ActionType.분무,""),
                ActionObject(ActionType.분갈이,""),
                ActionObject(ActionType.가지치기,""),
                ActionObject(ActionType.잎,""),
                ActionObject(ActionType.꽃,""),
                ActionObject(ActionType.영양제,""),
                ActionObject(ActionType.기타,"")
            )
        )
        action_adapter.onItemClicked = {actionType ->
            plantAction = actionType
            Log.i(TAG,"palntAction : "+plantAction)
            binding.actionTagTv.text= "#"+ plantAction
            clickCount++
            if(clickCount>=2){
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            plant_adapter.refreshItems(it)
        })
        plantIdViewModel.data.observe(viewLifecycleOwner, Observer {
            binding.plantTagTv.setText("@"+it.name)
            plantId = it.id!!
            Log.i(TAG,"palntId"+plantId)
        })
        feedWriteViewModel.postSuccess.observe(viewLifecycleOwner, Observer{
            if(it){
                navController.popBackStack()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        plantsViewModel.loadData()
    }

}
