package com.charaminstra.pleon.my

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
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.REQUEST_GALLERY
import com.charaminstra.pleon.common.REQUEST_TAKE_PHOTO
import com.charaminstra.pleon.my.databinding.FragmentMyEditBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

@AndroidEntryPoint
class MyEditFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding : FragmentMyEditBinding
    private lateinit var currentPhotoPath : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyEditBinding.inflate(inflater)
        val navController = this.findNavController()
        /*카메라권한요청*/
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_TAKE_PHOTO
        )
        binding.myEditBackBtn.setOnClickListener {
            navController.popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        viewModel.getUserData()
    }

    private fun initListeners(){
        binding.myEditCompleteBtn.setOnClickListener {
            viewModel.updateUserData(binding.userNameInput.text.toString())
        }
        binding.editUserImage.setOnClickListener{
            val pop= PopupMenu(requireContext(),it)
            pop.menuInflater.inflate(com.charaminstra.pleon.common_ui.R.menu.image_menu, pop.menu)
            pop.setOnMenuItemClickListener {
                when(it.itemId){
                    com.charaminstra.pleon.common_ui.R.id.camera ->{
                        openCamera()
                    }
                    com.charaminstra.pleon.common_ui.R.id.gallery ->
                        openGallery()
                    com.charaminstra.pleon.common_ui.R.id.cancel ->{
                        Glide.with(binding.root)
                            .load(com.charaminstra.pleon.common_ui.R.drawable.ic_user)
                            .into(binding.editUserImage)
                        viewModel.setNoImg()
                    }
                }
                false
            }
            pop.show()
        }
    }

    private fun initObservers(){
        viewModel.userName.observe(viewLifecycleOwner, Observer{
            if(it!=null){
                binding.userNameInput.setText(it)
            }
        })
        viewModel.urlResponse.observe(viewLifecycleOwner, Observer{
            Glide.with(binding.root)
                .load(it)
                .into(binding.editUserImage)
//            if(it == ""){
//                Log.i(TAG, "userImgUrl is null $it")
//                Glide.with(binding.root)
//                    .load(R.drawable.ic_user)
//                    .into(binding.editUserImage)
//                binding.editUserImage.setPadding(10)
//            }else{
//                Log.i(TAG, "userImgUrl is not !! null $it")
//
//                binding.editUserImage.setPadding(0)
//            }
        })
        viewModel.updateUserDataSuccess.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().popBackStack()
            }else{
                /* 수정 실패 */
            }
        })
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
                    binding.editUserImage.setImageBitmap(bitmap)
                    // get image url
                    ByteArrayOutputStream().use { stream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
                        val inputStream = ByteArrayInputStream(stream.toByteArray())
                        viewModel.setImgToUrl(inputStream)
                    }
                }
            }
            REQUEST_TAKE_PHOTO -> {
                Glide.with(this).load(currentPhotoPath).into(binding.editUserImage)
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