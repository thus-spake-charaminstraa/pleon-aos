package com.charaminstra.pleon

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.charaminstra.pleon.plant_register.ImageViewModel
import com.charaminstra.pleon.plant_register.PlantIdViewModel
import com.charaminstra.pleon.plant_register.ui.DEFAULT_GALLERY_REQUEST_CODE
import com.charaminstra.pleon.plant_register.ui.REQUEST_TAKE_PHOTO
import com.charaminstra.pleon.viewmodel.FeedWriteViewModel
import com.charaminstra.pleon.viewmodel.PlantsViewModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class FeedWriteFragment : Fragment() {
    private lateinit var binding : FragmentFeedWriteBinding
    private val TAG = javaClass.name
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val plantIdViewModel: PlantIdViewModel by viewModels()
    private val feedWriteViewModel : FeedWriteViewModel by viewModels()
    private val imageViewModel : ImageViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var plant_adapter: PlantAdapter
    private lateinit var action_adapter: ActionAdapter
    private val cal = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private lateinit var sheetBehavior : BottomSheetBehavior<View>
    private var plantId : String? = null
    private var plantAction: ActionType? = null
    private var url : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFeedWriteBinding.inflate(layoutInflater)

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_TAKE_PHOTO
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()

        imageViewModel.urlResponse.observe(viewLifecycleOwner, Observer {
            url = it
        })

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
        binding.image.visibility = View.GONE
        binding.cameraBtn.setOnClickListener{
            popUpImageMenu(it)
            binding.image.visibility = View.VISIBLE
        }
        binding.image.setOnClickListener {
            popUpImageMenu(it)
        }
        binding.completeBtn.setOnClickListener {
            plantId?.let { it1 ->
                plantAction?.let { it2 ->
                    feedWriteViewModel.postFeed(
                        it1,
                        binding.dateTv.text.toString(),
                        it2.action,
                        binding.contentEdit.text.toString(),
                        url
                    )
                }
            }
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

        binding.bottomSheet.nextBtn.setOnClickListener {
            if(plantId != null && plantAction != null){
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

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
        plant_adapter.onItemClicked = { Id ->
            plantId = Id
            plantIdViewModel.loadData(plantId!!)
        }

        action_adapter = ActionAdapter()
        action_adapter.refreshItems(
            listOf(
                ActionObject(ActionType.물,R.drawable.ic_action_water),
                ActionObject(ActionType.통풍,R.drawable.ic_action_air),
                ActionObject(ActionType.분무,R.drawable.ic_action_spray),
                ActionObject(ActionType.분갈이,R.drawable.ic_action_repot),
                ActionObject(ActionType.가지치기,R.drawable.ic_action_prune),
                ActionObject(ActionType.잎,R.drawable.ic_action_leaf),
                ActionObject(ActionType.꽃,R.drawable.ic_action_flower),
                ActionObject(ActionType.영양제,R.drawable.ic_action_fertilize),
                ActionObject(ActionType.기타,R.drawable.ic_action_etc)
            )
        )
        action_adapter.onItemClicked = { actionType ->
            plantAction = actionType
            Log.i(TAG,"palntAction : "+plantAction)
            binding.actionTagTv.text= resources.getString(R.string.action_tag) + plantAction
        }

    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            plant_adapter.refreshItems(it)
        })
        plantIdViewModel.data.observe(viewLifecycleOwner, Observer {
            binding.plantTagTv.text = resources.getString(R.string.plant_tag) + it.name
            plantId = it.id!!
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

    // 갤러리 화면에서 이미지를 선택한 경우 현재 화면에 보여준다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            DEFAULT_GALLERY_REQUEST_CODE -> {
                data?:return
                val uri = data.data as Uri
                activity?.contentResolver?.openInputStream(uri).let {
                    Log.i("gallery image inputstream : ",it.toString())
                    val bitmap = BitmapFactory.decodeStream(it)
                    // image veiw set image bit map
                    binding.image.setImageBitmap(bitmap)
                    // get image url
                    ByteArrayOutputStream().use { stream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
                        val inputStream = ByteArrayInputStream(stream.toByteArray())
                        imageViewModel.postImage(inputStream)
                    }
                }
            }
            REQUEST_TAKE_PHOTO -> {
                val bitmap = data?.extras?.get("data") as Bitmap
                binding.image.setImageBitmap(bitmap)
                ByteArrayOutputStream().use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
                    val inputStream = ByteArrayInputStream(stream.toByteArray())
                    Log.i("photo image inputstream", inputStream.toString())
                    imageViewModel.postImage(inputStream)
                }
            }
            else -> {
                Toast.makeText(requireContext(), resources.getString(R.string.image_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, DEFAULT_GALLERY_REQUEST_CODE)
    }

    private fun checkPermission() : Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
        if(permissionCheck == PackageManager.PERMISSION_GRANTED)
            return true
        else
            return false

    }

    private fun openCamera(){
        Log.i("permission",checkPermission().toString())
        if(checkPermission()){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_TAKE_PHOTO)
        }else{
            Toast.makeText(context, resources.getString(R.string.camera_permission_msg), Toast.LENGTH_SHORT).show()
        }
    }

    fun popUpImageMenu(view: View){
        val pop= PopupMenu(requireContext(),view)
        pop.menuInflater.inflate(com.charaminstra.pleon.plant_register.R.menu.image_menu, pop.menu)
        pop.setOnMenuItemClickListener {
            when(it.itemId){
                com.charaminstra.pleon.plant_register.R.id.camera ->
                    openCamera()
                com.charaminstra.pleon.plant_register.R.id.gallery ->
                    openGallery()
                com.charaminstra.pleon.plant_register.R.id.cancel ->
                    binding.image.setImageBitmap(null)
            }
            false
        }
        pop.show()
    }

}

