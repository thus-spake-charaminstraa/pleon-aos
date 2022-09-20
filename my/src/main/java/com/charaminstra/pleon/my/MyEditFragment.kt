package com.charaminstra.pleon.my

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.PLeonImageFile
import com.charaminstra.pleon.common.REQUEST_GALLERY
import com.charaminstra.pleon.common.REQUEST_TAKE_PHOTO
import com.charaminstra.pleon.common.RequestPermission
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.PopUpImageMenu
import com.charaminstra.pleon.my.databinding.FragmentMyEditBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileInputStream

@AndroidEntryPoint
class MyEditFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding : FragmentMyEditBinding
    private lateinit var permissionMsg: ErrorToast
    lateinit var photoFile: PLeonImageFile
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
        val navController = this.findNavController()
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

    private fun initListeners(){
        binding.myEditCompleteBtn.setOnClickListener {
            if(binding.userNameInput.text.isNullOrEmpty()){
                ErrorToast(requireContext()).showMsg(resources.getString(R.string.my_edit_fragment_name_error),binding.userNameInput.y)
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
            Glide.with(binding.root)
                .load(it)
                .into(binding.editUserImage)
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
                data ?: return
                val uri = data.data as Uri
                activity?.contentResolver?.openInputStream(uri).let {
                    val bitmap = BitmapFactory.decodeStream(it)
                    binding.editUserImage.setImageBitmap(bitmap)
                    viewModel.galleryToUrl(bitmap)
                }
            }
            REQUEST_TAKE_PHOTO -> {
                Glide.with(this).load(photoFile.currentPhotoPath).into(binding.editUserImage)
                viewModel.cameraToUrl(FileInputStream(photoFile.currentPhotoPath))
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
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "com.charaminstra.pleon.fileprovider",
            photoFile.create()
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_TAKE_PHOTO)
    }



}