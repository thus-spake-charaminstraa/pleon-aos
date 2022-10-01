package com.charaminstra.pleon.plant_register.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.common_ui.*
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantThumbnailBinding
import com.google.firebase.analytics.FirebaseAnalytics
import java.io.FileInputStream


class PlantThumbnailFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var binding: FragmentPlantThumbnailBinding
    private val viewModel: PlantRegisterViewModel by activityViewModels()

    private lateinit var permissionMsg: ErrorToast
    lateinit var photoFile: PLeonImageFile
    lateinit var bitmap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.warmingPlantDetectionModel()

        /*카메라권한요청*/
        RequestPermission.requestPermission(requireActivity())

        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        // logging
        val loggingBundle = Bundle()
        loggingBundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(PLANT_THUMBNAIL_VIEW , loggingBundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantThumbnailBinding.inflate(layoutInflater)
        //initObservers()
        permissionMsg = ErrorToast(requireContext())
        val navController = this.findNavController()

        binding.plantThumbnailBackBtn.setOnClickListener {
            activity?.finish()
        }
        binding.plantThumbnailImg.setOnClickListener {
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
            if (viewModel.thumbnailUrlResponse.value.isNullOrBlank()) {
                ErrorToast(requireContext()).showMsg(
                    resources.getString(R.string.plant_thumbnail_fragment_error),
                    binding.plantThumbnailAddImg.y
                )
            } else {
                viewModel.thumbnailToSpecies()
                navController.navigate(R.id.plant_thumbnail_fragment_to_plant_detection_waiting_fragment)
            }
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
                data ?: return
                val uri = data.data as Uri
                activity?.contentResolver?.openInputStream(uri).let {
                    bitmap = BitmapFactory.decodeStream(it)
                    binding.plantThumbnailImg.setImageBitmap(bitmap)
                    viewModel.thumbnailGalleryToUrl(bitmap)
                    viewModel.imgType = "gallery"
                }
            }
            REQUEST_TAKE_PHOTO -> {
                Glide.with(this).load(photoFile.currentPhotoPath).into(binding.plantThumbnailImg)
                viewModel.thumbnailCameraToUrl(FileInputStream(photoFile.currentPhotoPath))
                viewModel.imgType = "photo"
            }
            else -> {
                ErrorToast(requireContext()).showMsg(
                    resources.getString(com.charaminstra.pleon.common_ui.R.string.image_error),
                    binding.plantThumbnailAddImg.y
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.imgType == "gallery"){
            binding.plantThumbnailImg.setImageBitmap(bitmap)
        }else if(viewModel.imgType == "photo"){
            Glide.with(this).load(photoFile.currentPhotoPath).into(binding.plantThumbnailImg)
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