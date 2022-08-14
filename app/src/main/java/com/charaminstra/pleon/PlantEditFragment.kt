package com.charaminstra.pleon

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.lights.Light
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common_ui.CustomDialog
import com.charaminstra.pleon.databinding.FragmentPlantEditBinding
import com.charaminstra.pleon.plant_register.*
import com.charaminstra.pleon.R
import com.charaminstra.pleon.plant_register.ui.REQUEST_GALLERY
import com.charaminstra.pleon.plant_register.ui.REQUEST_TAKE_PHOTO
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PlantEditFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private val viewModel: PlantIdViewModel by viewModels()
    private lateinit var binding : FragmentPlantEditBinding
    private lateinit var navController: NavController
    private lateinit var id: String
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var currentPhotoPath : String
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = FragmentPlantEditBinding.inflate(layoutInflater)
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        dateFormat = SimpleDateFormat(resources.getString(com.charaminstra.pleon.common_ui.R.string.date_format))
        id = arguments?.getString("id")!!



        initListeners()
        initObservers()

        /*카메라권한요청*/
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_TAKE_PHOTO
        )
        binding.speciesInput.isEnabled=false
        binding.adoptDayInput.setOnClickListener {
            popUpCalendar(it as TextView)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()

        /* spinner */
        val lightItems = resources.getStringArray(R.array.light_list)
        val airItems = resources.getStringArray(R.array.air_list)
        val lightAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, lightItems)
        val airAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, airItems)
        binding.lightInput.adapter = lightAdapter
        binding.airInput.adapter = airAdapter

        viewModel.loadData(id)
        return binding.root
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
                Log.i("image",uri.path.toString())
                activity?.contentResolver?.openInputStream(uri).let {
                    val bitmap = BitmapFactory.decodeStream(it)
                    // image veiw set image bit map
                    binding.thumbnail.setImageBitmap(bitmap)
                    // get image url
                    ByteArrayOutputStream().use { stream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
                        val inputStream = ByteArrayInputStream(stream.toByteArray())
                        viewModel.setImgToUrl(inputStream)
                    }
                }
            }
            REQUEST_TAKE_PHOTO -> {
                Glide.with(this).load(currentPhotoPath).into(binding.thumbnail)
                val inputStream = FileInputStream(currentPhotoPath)
                viewModel.setImgToUrl(inputStream)
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    resources.getString(com.charaminstra.pleon.common_ui.R.string.image_error),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_TAKE_PHOTO){
        }else{
            Toast.makeText(requireContext(), "앱 실행을 위해서는 권한을 설정해야 합니다.", Toast.LENGTH_SHORT).show()
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

    fun popUpCalendar(view: TextView) {
        val cal = Calendar.getInstance()
        var datePickerDialog = DatePickerDialog(requireContext(),
            com.charaminstra.pleon.common_ui.R.style.PleonDatePickerStyle, { _, y, m, d ->
            cal.set(y,m,d)
            view.text = dateFormat.format(cal.time)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).apply {
            datePicker.maxDate = cal.timeInMillis
        }
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
    }

    private fun initObservers(){
        viewModel.data.observe(this, Observer { it ->
            Glide.with(binding.root)
                .load(it.thumbnail)
                .into(binding.thumbnail)
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
        viewModel.patchSuccess.observe(this, Observer{
            if(it){
                navController.popBackStack()
            }else{
                Toast.makeText(requireContext(),"수정에 실패하였습니다.",Toast.LENGTH_SHORT)
            }
        })
        viewModel.deleteSuccess.observe(this, Observer{
            if(it){
                navController.navigate(com.charaminstra.pleon.R.id.plant_edit_fragment_delete)
            }else{
                Toast.makeText(requireContext(),"삭제에 실패하였습니다.",Toast.LENGTH_SHORT)
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
        binding.thumbnail.setOnClickListener {
            val pop= PopupMenu(requireContext(),it)
            pop.menuInflater.inflate(com.charaminstra.pleon.plant_register.R.menu.image_menu, pop.menu)
            pop.setOnMenuItemClickListener {
                when(it.itemId){
                    com.charaminstra.pleon.plant_register.R.id.camera ->{
                        openCamera()
                    }
                    com.charaminstra.pleon.plant_register.R.id.gallery ->
                        openGallery()
                    com.charaminstra.pleon.plant_register.R.id.cancel ->
                        binding.thumbnail.setImageBitmap(null)

                }
                false
            }
            pop.show()
        }
        binding.completeBtn.setOnClickListener{
            viewModel.patchData(
                id,
                binding.plantNameInput.text.toString(),
                binding.adoptDayInput.text.toString())
        }
        binding.deleteBtn.setOnClickListener {
            val dlg = CustomDialog(requireContext())
            dlg.setOnOKClickedListener {
                viewModel.deleteData(id)
            }
            dlg.start(
                resources.getString(com.charaminstra.pleon.common_ui.R.string.dialog_title),
                resources.getString(com.charaminstra.pleon.common_ui.R.string.dialog_desc),
                resources.getString(com.charaminstra.pleon.common_ui.R.string.dialog_cancel_btn),
                resources.getString(com.charaminstra.pleon.common_ui.R.string.dialog_delete_btn)
            )
        }
    }
}