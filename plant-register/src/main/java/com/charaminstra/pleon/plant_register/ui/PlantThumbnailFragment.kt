package com.charaminstra.pleon.plant_register.ui

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
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.certified.customprogressindicatorlibrary.CustomProgressIndicator
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.common_ui.*
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantThumbnailBinding
import com.charaminstra.pleon.plant_register.getOrientation
import com.google.firebase.analytics.FirebaseAnalytics

class PlantThumbnailFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var binding: FragmentPlantThumbnailBinding
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var permissionMsg: ErrorToast
    lateinit var photoFile: PLeonImageFile
    var cameraUri: Uri? = null

    lateinit var indicator: CustomProgressIndicator

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
        setIndicator()

        initObservers()
        initListeners()
        permissionMsg = ErrorToast(requireContext())
        navController = this.findNavController()

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
        return binding.root
    }

    // 갤러리 화면에서 이미지를 선택한 경우 현재 화면에 보여준다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_CROP -> {

            }
            REQUEST_GALLERY -> {
                data ?: return
                cameraUri = data.data as Uri
                val inputStream = requireContext().contentResolver?.openInputStream(cameraUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val orientation = getOrientation(requireContext(), cameraUri!!)
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
                val inputStream = requireContext().contentResolver?.openInputStream(cameraUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val orientation = getOrientation(requireContext(), cameraUri!!)
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
                    binding.plantThumbnailAddImg.y
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        indicator.stopAnimation()
    }

    private fun initObservers(){
        viewModel.thumbnailUrlResponse.observe(viewLifecycleOwner, Observer {
            viewModel.thumbnailToSpecies()
            indicator.stopAnimation()
            if(viewModel.thumbnailNextStep){
                navController.navigate(R.id.plant_thumbnail_fragment_to_plant_detection_waiting_fragment)
                viewModel.thumbnailNextStep = false
            }else{}
        })
        viewModel.thumbnailBitmap.observe(viewLifecycleOwner, Observer{
            binding.plantThumbnailImg.setImageBitmap(it)
        })
    }

    private fun initListeners(){
        binding.plantRegisterNextBtn.setOnClickListener {
            viewModel.thumbnailNextStep = true
            viewModel.thumbnailBitmapToUrl()
            indicator.apply {
                visibility = View.VISIBLE
                startAnimation()
            }
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


    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, REQUEST_GALLERY)

    }
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = PLeonImageFile(requireActivity())
        cameraUri = FileProvider.getUriForFile(
            requireContext(),
            "com.charaminstra.pleon.fileprovider",
            photoFile.create()
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
        startActivityForResult(intent, REQUEST_TAKE_PHOTO)
    }
}