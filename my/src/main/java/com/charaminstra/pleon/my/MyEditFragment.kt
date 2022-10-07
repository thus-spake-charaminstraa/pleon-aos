package com.charaminstra.pleon.my

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.certified.customprogressindicatorlibrary.CustomProgressIndicator
import com.charaminstra.pleon.common.PLeonImageFile
import com.charaminstra.pleon.common.REQUEST_GALLERY
import com.charaminstra.pleon.common.REQUEST_TAKE_PHOTO
import com.charaminstra.pleon.common.RequestPermission
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.PopUpImageMenu
import com.charaminstra.pleon.my.databinding.FragmentMyEditBinding
import com.charaminstra.pleon.plant_register.getOrientation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyEditFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding : FragmentMyEditBinding
    private lateinit var navController: NavController

    private lateinit var permissionMsg: ErrorToast
    lateinit var photoFile: PLeonImageFile
    lateinit var myUri: Uri
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
        binding = FragmentMyEditBinding.inflate(inflater)
        setIndicator()
        navController = this.findNavController()
        permissionMsg = ErrorToast(requireContext())
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

    override fun onPause() {
        super.onPause()
        indicator.stopAnimation()
    }

    private fun initListeners(){
        binding.myEditCompleteBtn.setOnClickListener {
            if(binding.userNameInput.text.isNullOrEmpty()){
                ErrorToast(requireContext()).showMsg(resources.getString(R.string.my_edit_fragment_name_error),binding.userNameInput.y)
            }else if(viewModel.imgBitmap.value != null){
                indicator.apply {
                    visibility = View.VISIBLE
                    startAnimation()
                }
                viewModel.myBitmapToUrl()
                viewModel.imgEdit = true
                }else{
                    viewModel.updateUserData(binding.userNameInput.text.toString())
                }

        }
        binding.editUserImage.setOnClickListener{
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
    }

    private fun initObservers(){
        viewModel.userName.observe(viewLifecycleOwner, Observer{
            binding.userNameInput.setText(it)
        })
        viewModel.urlResponse.observe(viewLifecycleOwner, Observer{
            if(!viewModel.imgEdit){
                Glide.with(binding.root)
                    .load(it)
                    .into(binding.editUserImage)
            }else{
                viewModel.updateUserData(binding.userNameInput.text.toString())
            }
        })
        viewModel.updateUserDataSuccess.observe(viewLifecycleOwner, Observer {
            if(it){
                navController.popBackStack()
            }else{
                /* 수정 실패 */
            }
        })
        viewModel.imgBitmap.observe(viewLifecycleOwner, Observer {
            binding.editUserImage.setImageBitmap(it)
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
                data ?: return
                myUri = data.data as Uri
                val inputStream = requireContext().contentResolver?.openInputStream(myUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val orientation = getOrientation(requireContext(), myUri!!)
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
                val inputStream = requireContext().contentResolver?.openInputStream(myUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val orientation = getOrientation(requireContext(), myUri!!)
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
                ErrorToast(requireContext()).showMsg(
                    resources.getString(com.charaminstra.pleon.common_ui.R.string.image_error),
                    binding.editUserImage.y
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
        myUri = FileProvider.getUriForFile(
            requireContext(),
            "com.charaminstra.pleon.fileprovider",
            photoFile.create()
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, myUri)
        startActivityForResult(intent, REQUEST_TAKE_PHOTO)
    }

    private fun setIndicator(){
        indicator = binding.indicator
        indicator.setProgressIndicatorColor("#8d8d97")
        indicator.setTrackColor("#35c97a")
        indicator.setImageResource(com.charaminstra.pleon.common_ui.R.drawable.img_logo)
        indicator.setText("loading ... ")
        indicator.setTextSize(15.0F)
    }

}