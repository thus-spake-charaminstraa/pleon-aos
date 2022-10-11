package com.charaminstra.pleon.garden.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.certified.customprogressindicatorlibrary.CustomProgressIndicator
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.common_ui.DateUtils
import com.charaminstra.pleon.common_ui.PLeonMsgDialog
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.PopUpImageMenu
import com.charaminstra.pleon.garden.PlantEditViewModel
import com.charaminstra.pleon.garden.R
import com.charaminstra.pleon.garden.databinding.FragmentPlantEditBinding
import com.charaminstra.pleon.plant_register.getOrientation
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class PlantEditFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private val viewModel: PlantEditViewModel by viewModels()
    private lateinit var binding : FragmentPlantEditBinding
    private lateinit var navController: NavController
    private lateinit var id: String
    private lateinit var dateFormat: SimpleDateFormat
    lateinit var photoFile: PLeonImageFile
    private lateinit var permissionMsg: ErrorToast
    lateinit var plantImgUri: Uri
    lateinit var indicator: CustomProgressIndicator
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        /*카메라권한요청*/
        RequestPermission.requestPermission(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantEditBinding.inflate(layoutInflater)
        setIndicator()
        permissionMsg = ErrorToast(requireContext())
        binding.plantEditBackBtn.setOnClickListener {
            navController.popBackStack()
        }
        dateFormat = SimpleDateFormat(resources.getString(com.charaminstra.pleon.common_ui.R.string.date_send_format))
        id = arguments?.getString("id")!!

        initListeners()
        initObservers()

        binding.speciesInput.isEnabled=false
        binding.adoptDayInput.setOnClickListener {
            popUpCalendar()
        }
        navController = this.findNavController()

        /* spinner */
        val lightItems = resources.getStringArray(R.array.light_list)
        val airItems = resources.getStringArray(R.array.air_list)
        val lightAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, lightItems)
        val airAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, airItems)
        binding.lightInput.adapter = lightAdapter
        binding.airInput.adapter = airAdapter

        viewModel.getPlantData(id)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        indicator.stopAnimation()
    }

    // 갤러리 화면에서 이미지를 선택한 경우 현재 화면에 보여준다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_GALLERY -> {
                data ?: return
                plantImgUri = data.data as Uri
                val inputStream = requireContext().contentResolver?.openInputStream(plantImgUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val orientation = getOrientation(requireContext(), plantImgUri!!)
                val matrix = Matrix()
                when(orientation){
                    90 -> matrix.postRotate(90F)
                    180 -> matrix.postRotate(180F)
                    270 -> matrix.postRotate(270F)
                }
                val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0 ,bitmap.width, bitmap.height, matrix, true)
                viewModel.setBitmap(rotateBitmap)
            }
            REQUEST_TAKE_PHOTO -> {
                val inputStream = requireContext().contentResolver?.openInputStream(plantImgUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val orientation = getOrientation(requireContext(), plantImgUri!!)
                val matrix = Matrix()
                when(orientation){
                    90 -> matrix.postRotate(90F)
                    180 -> matrix.postRotate(180F)
                    270 -> matrix.postRotate(270F)
                }
                val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0 ,bitmap.width, bitmap.height, matrix, true)
                viewModel.setBitmap(rotateBitmap)
            }
            else -> {
                ErrorToast(requireContext()).showMsgDown(
                    resources.getString(com.charaminstra.pleon.common_ui.R.string.image_error),
                    binding.plantEditAddImg.y
                )
            }
        }
    }
    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, REQUEST_GALLERY)
    }
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = PLeonImageFile(requireActivity())
        plantImgUri = FileProvider.getUriForFile(
            requireContext(),
            "com.charaminstra.pleon.fileprovider",
            photoFile.create()
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT,  plantImgUri)
        startActivityForResult(intent, REQUEST_TAKE_PHOTO)
    }

    fun popUpCalendar() {
        binding.plantEditDatePickerDialog.visibility=View.VISIBLE

        binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.black_sub_color))
        binding.plantEditBtns.visibility = View.GONE

        binding.plantEditAddImg.visibility = View.GONE
        binding.plantEditImg.visibility = View.GONE
        binding.speciesInput.visibility = View.GONE
        binding.plantNameInput.visibility = View.GONE
        binding.adoptDayInput.visibility = View.GONE
        binding.airInput.visibility = View.GONE
        binding.lightInput.visibility = View.GONE

        binding.plantEditDatePickerCancelBtn.setOnClickListener {
            binding.plantEditDatePickerDialog.visibility=View.GONE

            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.plantEditBtns.visibility = View.VISIBLE

            binding.plantEditAddImg.visibility = View.VISIBLE
            binding.plantEditImg.visibility = View.VISIBLE
            binding.speciesInput.visibility = View.VISIBLE
            binding.plantNameInput.visibility = View.VISIBLE
            binding.adoptDayInput.visibility = View.VISIBLE
            binding.airInput.visibility = View.VISIBLE
            binding.lightInput.visibility = View.VISIBLE
        }
        //확인 버튼
        binding.plantEditDatePickerOkBtn.setOnClickListener {
            binding.adoptDayInput.setText(DateUtils(requireContext()).datePickerToView(binding.plantEditDatePicker.date))

            binding.plantEditDatePickerDialog.visibility=View.GONE
            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.plantEditBtns.visibility = View.VISIBLE

            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.completeBtn.isClickable = true

            binding.plantEditAddImg.visibility = View.VISIBLE
            binding.plantEditImg.visibility = View.VISIBLE
            binding.speciesInput.visibility = View.VISIBLE
            binding.plantNameInput.visibility = View.VISIBLE
            binding.adoptDayInput.visibility = View.VISIBLE
            binding.airInput.visibility = View.VISIBLE
            binding.lightInput.visibility = View.VISIBLE
        }
    }

    private fun initObservers(){
        viewModel.plantImgBitmap.observe(viewLifecycleOwner, Observer {
            binding.plantEditImg.setImageBitmap(it)
        })
        viewModel.plantData.observe(viewLifecycleOwner, Observer { it ->
            Glide.with(binding.root)
                .load(it.thumbnail)
                .circleCrop()
                .into(binding.plantEditImg)
            binding.plantNameInput.setText(it.name)
            binding.speciesInput.setText(it.species)
            binding.adoptDayInput.text = dateFormat.format(it.adopt_date)

            when(it.light){
                LightType.BRIGHT.apiString -> binding.lightInput.setSelection(0)
                LightType.HALF_BRIGHT.apiString -> binding.lightInput.setSelection(1)
                LightType.LAMP.apiString -> binding.lightInput.setSelection(2)
                LightType.DARK.apiString -> binding.lightInput.setSelection(3)
            }

            when(it.air){
                AirType.YES.apiString -> binding.airInput.setSelection(0)
                AirType.WINDOW.apiString -> binding.airInput.setSelection(1)
                AirType.NO.apiString -> binding.airInput.setSelection(2)
            }

        })
        viewModel.patchSuccess.observe(viewLifecycleOwner, Observer{
            if(it){
                navController.popBackStack()
            }else{
                Toast.makeText(requireContext(),"수정에 실패하였습니다.",Toast.LENGTH_SHORT)
            }
        })
        viewModel.deleteSuccess.observe(viewLifecycleOwner, Observer{
            if(it){
                navController.navigate(R.id.plant_edit_fragment_delete)
            }else{
                Toast.makeText(requireContext(),"삭제에 실패하였습니다.",Toast.LENGTH_SHORT)
            }
        })
        viewModel.urlResponse.observe(viewLifecycleOwner, Observer {
            if(viewModel.plantImgEdit){
                viewModel.patchData(
                    id,
                    binding.plantNameInput.text.toString(),
                    DateUtils(requireContext()).viewToSendServer(binding.adoptDayInput.text.toString()))
                navController.popBackStack()
            }else{

            }
        })
    }

    private fun initListeners(){
        binding.airInput.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                when(pos){
                    0 -> viewModel.setAir("yes")
                    1 -> viewModel.setAir("window")
                    2 -> viewModel.setAir("no")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        binding.lightInput.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                when(pos){
                    0 -> viewModel.setLight("bright")
                    1 -> viewModel.setLight("half_bright")
                    2 -> viewModel.setLight("lamp")
                    3 -> viewModel.setLight("dark")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        binding.plantEditImg.setOnClickListener {
            val imageMenuDlg = PopUpImageMenu(requireContext())
            imageMenuDlg.setOnCameraClickedListener {
                if (RequestPermission.checkPermission(requireActivity())) {
                    openCamera()
                } else {
                    ErrorToast(requireContext()).showCameraPermission()
                }
            }
            imageMenuDlg.setOnGalleryClickedListener {
                openGallery()
            }
            imageMenuDlg.start()
        }
        binding.completeBtn.setOnClickListener{
            if(binding.plantNameInput.text.isNullOrEmpty()){
                ErrorToast(requireContext()).showMsgDown(resources.getString(R.string.plant_edit_fragment_name_error),binding.plantNameInput.y)
            }else if(viewModel.plantImgBitmap.value != null){
                indicator.apply {
                    visibility = View.VISIBLE
                    startAnimation()
                }
                viewModel.plantImgBitmapToUrl()
                viewModel.plantImgEdit = true
            }else{
                viewModel.patchData(
                    id,
                    binding.plantNameInput.text.toString(),
                    DateUtils(requireContext()).viewToSendServer(binding.adoptDayInput.text.toString()))
            }
        }
        binding.deleteBtn.setOnClickListener {
            val dlg = PLeonMsgDialog(requireContext())
            dlg.setOnOKClickedListener {
                viewModel.deleteData(id)
                navController.navigate(R.id.plant_edit_fragment_delete)
            }
            dlg.start(
                resources.getString(com.charaminstra.pleon.common_ui.R.string.dialog_title),
                resources.getString(com.charaminstra.pleon.common_ui.R.string.dialog_desc),
                resources.getString(com.charaminstra.pleon.common_ui.R.string.dialog_cancel_btn),
                resources.getString(com.charaminstra.pleon.common_ui.R.string.dialog_delete_btn)
            )
        }
    }

    fun setIndicator(){
        indicator = binding.indicator
        indicator.setProgressIndicatorColor("#8d8d97")
        indicator.setTrackColor("#35c97a")
        indicator.setImageResource(com.charaminstra.pleon.common_ui.R.drawable.img_logo)
        indicator.setText("loading ... ")
        indicator.setTextSize(15.0F)
    }
}