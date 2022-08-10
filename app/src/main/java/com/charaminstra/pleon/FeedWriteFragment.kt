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
import android.os.Environment
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
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.adapter.*
import com.charaminstra.pleon.databinding.FragmentFeedWriteBinding
import com.charaminstra.pleon.plant_register.ui.REQUEST_GALLERY
import com.charaminstra.pleon.plant_register.ui.REQUEST_TAKE_PHOTO
import com.charaminstra.pleon.viewmodel.FeedWriteViewModel
import com.charaminstra.pleon.viewmodel.PlantsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class FeedWriteFragment : Fragment() {
    private lateinit var binding : FragmentFeedWriteBinding
    private val TAG = javaClass.name
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedWriteViewModel : FeedWriteViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var plant_adapter: PlantAdapter
    private lateinit var action_adapter: ActionAdapter
    private val cal = Calendar.getInstance()
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var sheetBehavior : BottomSheetBehavior<View>
    private lateinit var currentPhotoPath : String

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
        dateFormat = SimpleDateFormat(resources.getString(com.charaminstra.pleon.common_ui.R.string.date_format))
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
        binding.imageCard.visibility = View.GONE
        binding.cameraBtn.setOnClickListener{
            popUpImageMenu(it)
            binding.imageCard.visibility = View.VISIBLE
        }
        binding.imageCard.setOnClickListener {
            popUpImageMenu(it)
        }
        binding.completeBtn.setOnClickListener {
            if(feedWriteViewModel.plantId?.value != null &&
                feedWriteViewModel.kind.value != null){
                feedWriteViewModel.postFeed(
                    binding.dateTv.text.toString(),
                    binding.contentEdit.text.toString()
                )
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
        binding.bottomSheet.plantRecyclerview.addOnItemTouchListener(recyclerListener)
        binding.bottomSheet.actionRecyclerview.addOnItemTouchListener(recyclerListener)

        binding.bottomSheet.nextBtn.setOnClickListener {
            if(feedWriteViewModel.plantId.value != null &&
                feedWriteViewModel.kind.value != null){
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                /* 자동 완성 */
                binding.contentEdit.setText(feedWriteViewModel.plantName.value +
                        feedWriteViewModel.plantActionDesc.value)
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
            //plantId = Id
            feedWriteViewModel.setPlantId(Id)
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
            feedWriteViewModel.setPlantAction(actionType)
            binding.actionTagTv.text= resources.getString(R.string.action_tag) + actionType.toString()
        }

    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            plant_adapter.refreshItems(it)
        })
        feedWriteViewModel.plantId.observe(viewLifecycleOwner, Observer {
            feedWriteViewModel.getPlantName()
        })
        feedWriteViewModel.plantName.observe(viewLifecycleOwner, Observer {
            binding.plantTagTv.text = resources.getString(R.string.plant_tag) + feedWriteViewModel.plantName.value
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
            REQUEST_GALLERY -> {
                data?:return
                val uri = data.data as Uri
                activity?.contentResolver?.openInputStream(uri).let {
                    val bitmap = BitmapFactory.decodeStream(it)
                    binding.image.setImageBitmap(bitmap)
                    // image veiw set image bit map
                    binding.image.setImageBitmap(bitmap)
                    Log.i("inputstream",it.toString())
                    feedWriteViewModel.postImage(it!!)
                    Log.i("gallery image inputstream : ",it.toString())
                    // image veiw set image bit map

                    // get image url
                    ByteArrayOutputStream().use { stream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
                        val inputStream = ByteArrayInputStream(stream.toByteArray())
                        feedWriteViewModel.postImage(inputStream)
                    }
                }
            }
            REQUEST_TAKE_PHOTO -> {
                Glide.with(this).load(currentPhotoPath).into(binding.image)
                val inputStream = FileInputStream(currentPhotoPath)
                feedWriteViewModel.postImage(inputStream)
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    resources.getString(com.charaminstra.pleon.common_ui.R.string.image_error),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, REQUEST_GALLERY)
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
            val photoFile = createImageFile()
            if(photoFile != null ){
                    val uri = FileProvider.getUriForFile(requireContext(),"com.charaminstra.pleon.fileprovider", photoFile)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO)
            }
        }else{
            Toast.makeText(context,
                resources.getString(com.charaminstra.pleon.common_ui.R.string.camera_permission_msg),
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFile(): File {
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "image", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
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
                com.charaminstra.pleon.plant_register.R.id.cancel ->{
                    binding.imageCard.visibility = View.GONE
                    binding.image.setImageBitmap(null)
                }
            }
            false
        }
        pop.show()
    }

}

