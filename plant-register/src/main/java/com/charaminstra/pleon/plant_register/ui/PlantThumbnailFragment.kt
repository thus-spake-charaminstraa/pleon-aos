package com.charaminstra.pleon.plant_register.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common_ui.PLeonMsgDialog
import com.charaminstra.pleon.common_ui.PopUpImageMenu
import com.charaminstra.pleon.common_ui.showErrorToast
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantThumbnailBinding
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

class PlantThumbnailFragment : Fragment() {
    private lateinit var binding: FragmentPlantThumbnailBinding
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var currentPhotoPath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantThumbnailBinding.inflate(layoutInflater)
        val navController = this.findNavController()

        binding.plantThumbnailBackBtn.setOnClickListener {
            activity?.finish()
        }

        binding.plantThumbnailImg.setOnClickListener {
            val dlg = PopUpImageMenu(requireContext())
            dlg.setOnCameraClickedListener {
                /*카메라권한요청*/
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_TAKE_PHOTO
                )
                openCamera()
            }

            dlg.setOnGalleryClickedListener {
                openGallery()
            }
            dlg.start()
        }

        binding.plantRegisterSkipBtn.setOnClickListener {
            val dlg = PLeonMsgDialog(requireContext())
            dlg.setOnOKClickedListener {
                activity?.finish()
            }
            dlg.start(
                resources.getString(R.string.plant_register_skip_dialog_title),
                resources.getString(R.string.plant_register_skip_dialog_desc),
                resources.getString(R.string.plant_register_skip_dialog_cancel_btn),
                resources.getString(R.string.plant_register_skip_dialog_skip_btn)
            )
        }

        binding.plantRegisterNextBtn.setOnClickListener {
            //navController.navigate(R.id.plant_thumbnail_fragment_to_plant_species_fragment)
            if(viewModel.urlResponse.value.isNullOrBlank()){
                Toast(activity).showErrorToast(resources.getString(R.string.plant_thumbnail_fragment_error),binding.plantThumbnailAddImg.y,requireActivity())
            }else{
                //test
                navController.navigate(R.id.plant_thumbnail_fragment_to_plant_species_fragment)
            }
        }

        return binding.root
    }


    //수정시작할부분
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
                    Log.i("gallery image inputstream",it.toString())
                    val bitmap = BitmapFactory.decodeStream(it)
                    // image veiw set image bit map
                    binding.plantThumbnailImg.setImageBitmap(bitmap)
                    // get image url
                    ByteArrayOutputStream().use { stream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
                        val inputStream = ByteArrayInputStream(stream.toByteArray())
                        viewModel.setImgToUrl(inputStream)
                    }
                }
            }
            REQUEST_TAKE_PHOTO -> {
                Glide.with(this).load(currentPhotoPath).into(binding.plantThumbnailImg)
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
}