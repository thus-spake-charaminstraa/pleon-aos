package com.charaminstra.pleon.plant_register.ui

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.ImageViewModel
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_GALLERY_REQUEST_CODE = 2000
const val REQUEST_TAKE_PHOTO = 3000

@AndroidEntryPoint
class PlantRegisterFragment : Fragment() {
    private val plantRegisterViewModel: PlantRegisterViewModel by activityViewModels()
    private val imageViewModel : ImageViewModel by viewModels()
    private lateinit var binding: FragmentPlantRegisterBinding
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
        binding.thumbnail.setOnClickListener {
            val pop= PopupMenu(requireContext(),it)
            pop.menuInflater.inflate(R.menu.image_menu, pop.menu)
            pop.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.camera ->{
                        openCamera()
                    }
                    R.id.gallery ->
                        openGallery()
                    R.id.cancel ->
                        binding.thumbnail.setImageBitmap(null)

                }
                false
            }
            pop.show()
        }
        binding.adoptDayInput.setOnClickListener {
            popUpCalendar(it as TextView)
        }
        binding.waterDayInput.setOnClickListener {
            popUpCalendar(it as TextView)
        }

        binding.nextBtn.setOnClickListener {
            plantRegisterViewModel.setName(binding.nameInput.text.toString())
            plantRegisterViewModel.setSpecies(binding.speciesInput.text.toString())
            plantRegisterViewModel.setAdopt_date(binding.adoptDayInput.text.toString())
            plantRegisterViewModel.setWater_date(binding.waterDayInput.text.toString())
            imageViewModel.urlResponse.observe(viewLifecycleOwner, Observer {
                plantRegisterViewModel.setThumbnail(it)
                Log.i("imageurl",it)
            })
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
            DEFAULT_GALLERY_REQUEST_CODE -> {
                data?:return
                val uri = data.data as Uri
                Log.i("image",uri.path.toString())
                activity?.contentResolver?.openInputStream(uri).let {
                    // image veiw set image bit map
                    binding.thumbnail.setImageBitmap(BitmapFactory.decodeStream(it))
                    // get image url
                    imageViewModel.postImage(it!!)
                }
            }
            REQUEST_TAKE_PHOTO -> {
                val bitmap = data?.extras?.get("data") as Bitmap
                binding.thumbnail.setImageBitmap(bitmap)
                ByteArrayOutputStream().use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
                    val inputStream = ByteArrayInputStream(stream.toByteArray())
                    imageViewModel.postImage(inputStream)
                }
            }
            else -> {
                Toast.makeText(requireContext(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
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
            startActivityForResult(intent, DEFAULT_GALLERY_REQUEST_CODE)
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
            startActivityForResult(intent, REQUEST_TAKE_PHOTO)
        }else{
            Toast.makeText(context, "카메라 권한 설정이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }


    fun popUpCalendar(view: TextView) {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var datePickerDialog = DatePickerDialog(requireContext(), { _, y, m, d ->
            view.text = dateFormat.format(cal.time)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
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

}