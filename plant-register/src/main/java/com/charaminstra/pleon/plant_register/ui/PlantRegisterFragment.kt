package com.charaminstra.pleon.plant_register.ui

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
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.plant_register.PlantIdViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_GALLERY = 2000
const val REQUEST_TAKE_PHOTO = 3000

@AndroidEntryPoint
class PlantRegisterFragment : Fragment() {
    private val plantIdViewModel: PlantIdViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantRegisterBinding
    private lateinit var currentPhotoPath : String
    private lateinit var dateFormat: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = FragmentPlantRegisterBinding.inflate(layoutInflater)

        /*카메라권한요청*/
        requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_TAKE_PHOTO
        )

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = this.findNavController()

        checkFromActivity()
        binding.backBtn.setOnClickListener {
            activity?.finish()
        }
        binding.speciesBtn.setOnClickListener{
            navController.navigate(R.id.plant_register_fragment_to_plant_search_fragment)
        }
        binding.thumbnail.setOnClickListener {
            popUpImageMenu(it)
        }
        binding.adoptDayInput.setOnClickListener {
            popUpCalendar(it as TextView)
        }
        binding.waterDayInput.setOnClickListener {
            popUpCalendar(it as TextView)
        }

        binding.nextBtn.setOnClickListener {
            plantIdViewModel.setName(binding.nameInput.text.toString())
            plantIdViewModel.setSpecies(binding.speciesBtn.text.toString())
            plantIdViewModel.setAdopt_date(binding.adoptDayInput.text.toString())
            plantIdViewModel.setWater_date(binding.waterDayInput.text.toString())
            navController.navigate(R.id.plant_register_fragment_to_plant_light_fragment)
        }

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
//                startCrop(data?.data as Uri)
                data?:return
                val uri = data.data as Uri
                Log.i("image",uri.path.toString())
                /**/
                activity?.contentResolver?.openInputStream(uri).let {
                    Log.i("gallery image inputstream",it.toString())
                    val bitmap = BitmapFactory.decodeStream(it)
                    // image veiw set image bit map
                    binding.thumbnail.setImageBitmap(bitmap)
                    // get image url
                    ByteArrayOutputStream().use { stream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
                        val inputStream = ByteArrayInputStream(stream.toByteArray())
                        plantIdViewModel.setImgToUrl(inputStream)
                    }
                }
            }
            REQUEST_TAKE_PHOTO -> {
                Glide.with(this).load(currentPhotoPath).into(binding.thumbnail)
                val inputStream = FileInputStream(currentPhotoPath)
                plantIdViewModel.setImgToUrl(inputStream)
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
        val permissionCheck =ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
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
        dateFormat = SimpleDateFormat(resources.getString(com.charaminstra.pleon.common_ui.R.string.date_format))
        var datePickerDialog = DatePickerDialog(requireContext(),
            com.charaminstra.pleon.common_ui.R.style.PleonDatePickerStyle,
            { _, y, m, d ->
            cal.set(y,m,d)
            view.text = dateFormat.format(cal.time)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).apply {
            datePicker.maxDate = cal.timeInMillis
        }
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.BLACK)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLACK)
    }

    fun checkFromActivity(){
        Log.i("intent.from",activity?.intent?.getStringExtra("from").toString())
        if(activity?.intent?.getStringExtra("from").toString()=="login"){
            binding.skipBtn.visibility = View.VISIBLE
            binding.skipBtn.setOnClickListener {
                activity?.finish()
            }
        }
    }

    fun popUpImageMenu(view: View){
        val pop= PopupMenu(requireContext(),view)
        pop.menuInflater.inflate(R.menu.image_menu, pop.menu)
        pop.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.camera ->
                    openCamera()
                R.id.gallery ->
                    openGallery()
                R.id.cancel ->
                    binding.thumbnail.setImageBitmap(null)
            }
            false
        }
        pop.show()
    }

    override fun onResume() {
        super.onResume()
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("plant_species")?.
                observe(viewLifecycleOwner){
                    binding.speciesBtn.text = it
        }
    }

}