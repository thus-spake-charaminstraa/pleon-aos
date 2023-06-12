package com.charaminstra.pleon.feed.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.certified.customprogressindicatorlibrary.CustomProgressIndicator
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.common_ui.*
import com.charaminstra.pleon.feed.ActionAdapter
import com.charaminstra.pleon.feed.FeedPlantAdapter
import com.charaminstra.pleon.feed.viewmodel.FeedWriteViewModel
import com.charaminstra.pleon.feed.databinding.FragmentFeedWriteBinding
import com.charaminstra.pleon.plant_register.getOrientation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class FeedWriteFragment : Fragment() {
    private lateinit var binding : FragmentFeedWriteBinding
    private val TAG = javaClass.name
    private val feedWriteViewModel : FeedWriteViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var plant_adapter: FeedPlantAdapter
    private lateinit var action_adapter: ActionAdapter
    private val cal = Calendar.getInstance()
    private lateinit var sheetBehavior : BottomSheetBehavior<View>
    lateinit var photoFile: PLeonImageFile
    lateinit var imgUri: Uri
    lateinit var indicator: CustomProgressIndicator

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())
        binding = FragmentFeedWriteBinding.inflate(layoutInflater)
        setIndicator()
        binding.feedWriteBackBtn.setOnClickListener {
            navController.popBackStack()
        /*카메라권한요청*/
        RequestPermission.requestPermission(requireActivity())
    }}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()

        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.feedWriteImgRoot.visibility = View.GONE
        binding.feedWriteImgDeleteBtn.visibility = View.GONE

        binding.feedWritePlantTagTv.setOnClickListener(SOCL)
        binding.feedWriteActionTagTv.setOnClickListener(SOCL)
        binding.feedWriteDate.text = DateUtils(requireContext()).dateToView(cal.time)
        binding.feedWriteDate.setOnClickListener {
            val dlg=PLeonDatePicker(requireContext())
            dlg.setOnOKClickedListener {
                binding.feedWriteDate.text = dlg.date
            }
            dlg.start(resources.getString(com.charaminstra.pleon.common_ui.R.string.date_picker_title))
        }
        binding.feedWriteImgAddBtn.setOnClickListener{
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
        //사진 x
        binding.feedWriteImgDeleteBtn.setOnClickListener{
            binding.feedWriteImg.setImageBitmap(null)
            feedWriteViewModel.clearBitmap()

            binding.feedWriteImgRoot.visibility = View.GONE
            binding.feedWriteImgDeleteBtn.visibility = View.GONE
            binding.feedWriteImgAddBtn.visibility= View.VISIBLE
        }
        binding.completeBtn.setOnClickListener {
            if(binding.feedWriteContent.text.isNullOrBlank()){
                ErrorToast(requireContext()).showMsgCenter(binding.feedWriteContent.hint.toString())
            }else if(feedWriteViewModel.imgBitmap.value != null){
                indicator.apply {
                    visibility = View.VISIBLE
                    startAnimation()
                }
                feedWriteViewModel.imgBitmapToUrl()
            }else{
                indicator.stopAnimation()
                feedWriteViewModel.postFeed(
                    DateUtils(requireContext()).viewToSendServer(binding.feedWriteDate.text.toString()),
                    binding.feedWriteContent.text.toString()
                )
            }
            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(FEED_WRITE_COMPLETE_BTN_CLICK, bundle)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    val BSCB : BottomSheetBehavior.BottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            // newState = 상태값
            when(newState) {
                // 사용자가 BottomSheet를 위나 아래로 드래그 중인 상태
                BottomSheetBehavior.STATE_DRAGGING -> { }
                // 드래그 동작 후 BottomSheet가 특정 높이로 고정될 때의 상태
                // SETTLING 후 EXPANDED, SETTLING 후 COLLAPSED, SETTLING 후 HIDDEN
                BottomSheetBehavior.STATE_SETTLING -> {

                }
                // 최대 높이로 보이는 상태
                BottomSheetBehavior.STATE_EXPANDED -> {
                    hideKeyboard(binding.feedWriteContent, requireContext())
                }
                // peek 높이 만큼 보이는 상태
                BottomSheetBehavior.STATE_COLLAPSED -> {
                }
                // 숨김 상태
                BottomSheetBehavior.STATE_HIDDEN -> {
                }
            }
        }
        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }
    val SOCL : View.OnClickListener = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(BOTTOM_SHEET_UP, bundle)

            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()
        binding.bottomSheet.plantRecyclerview.adapter = plant_adapter
        binding.bottomSheet.actionRecyclerview.adapter = action_adapter

        binding.bottomSheet.nextBtn.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(BOTTOM_SHEET_NEXT_BTN_CLICK, bundle)
        }
    }

    private fun initList() {
        plant_adapter = FeedPlantAdapter()
        plant_adapter.onItemClicked = { id, name ->
            feedWriteViewModel.plantId = id
            binding.feedWritePlantTagTv.text= resources.getString(com.charaminstra.pleon.feed_common.R.string.plant_tag) + name
        }
        action_adapter = ActionAdapter()
        action_adapter.onItemClicked = { actionType ->
            Log.i("action type", actionType.toString())
            feedWriteViewModel.plantAction = actionType
            binding.feedWriteActionTagTv.text = actionType.name_kr
            binding.feedWriteActionTagTv.text= resources.getString(com.charaminstra.pleon.feed_common.R.string.action_tag) + feedWriteViewModel.plantAction!!.name_kr
            binding.feedWriteContent.setText(feedWriteViewModel.plantAction?.auto_content!!)
        }
        feedWriteViewModel.getActionList()
        feedWriteViewModel.getPlantList()
    }

    private fun observeViewModel() {
        feedWriteViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            plant_adapter.refreshItems(it)
            binding.feedWritePlantTagTv.text = resources.getString(com.charaminstra.pleon.feed_common.R.string.plant_tag) + feedWriteViewModel.plantName
        })
        feedWriteViewModel.postSuccess.observe(viewLifecycleOwner, Observer{
            if(it){
                navController.popBackStack()
            }
        })
        feedWriteViewModel.actionList.observe(viewLifecycleOwner, Observer {
            binding.feedWriteActionTagTv.text= resources.getString(com.charaminstra.pleon.feed_common.R.string.action_tag) + feedWriteViewModel.plantAction!!.name_kr
            binding.feedWriteContent.setText(feedWriteViewModel.plantAction?.auto_content!!)
            action_adapter.refreshItems(it)
        })
        feedWriteViewModel.imgBitmap.observe(viewLifecycleOwner, Observer{
            binding.feedWriteImg.setImageBitmap(it)
        })
        feedWriteViewModel.urlResponse.observe(viewLifecycleOwner,Observer{
            feedWriteViewModel.postFeed(
                DateUtils(requireContext()).viewToSendServer(binding.feedWriteDate.text.toString()),
                binding.feedWriteContent.text.toString()
            )
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
                imgUri = data.data as Uri
                val inputStream = requireContext().contentResolver?.openInputStream(imgUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val orientation = getOrientation(requireContext(),imgUri!!)
                val matrix = Matrix()
                when(orientation){
                    90 -> matrix.postRotate(90F)
                    180 -> matrix.postRotate(180F)
                    270 -> matrix.postRotate(270F)
                }
                val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0 ,bitmap.width, bitmap.height, matrix, true)
                feedWriteViewModel.setBitmap(rotateBitmap)
                binding.feedWriteImgRoot.visibility = View.VISIBLE
                binding.feedWriteImgDeleteBtn.visibility = View.VISIBLE
            }
            REQUEST_TAKE_PHOTO -> {
                binding.feedWriteImgRoot.visibility = View.VISIBLE
                binding.feedWriteImgDeleteBtn.visibility = View.VISIBLE
                val inputStream = requireContext().contentResolver?.openInputStream(imgUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val orientation = getOrientation(requireContext(),imgUri!!)
                val matrix = Matrix()
                when(orientation){
                    90 -> matrix.postRotate(90F)
                    180 -> matrix.postRotate(180F)
                    270 -> matrix.postRotate(270F)
                }
                val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0 ,bitmap.width, bitmap.height, matrix, true)
                feedWriteViewModel.setBitmap(rotateBitmap)
                feedWriteViewModel.setBitmap(rotateBitmap)
            }
            else -> {
                ErrorToast(requireContext()).showMsgDown(
                    resources.getString(com.charaminstra.pleon.common_ui.R.string.image_error),
                    binding.feedWriteImg.y
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
        imgUri = FileProvider.getUriForFile(
            requireContext(),
            "com.charaminstra.pleon.fileprovider",
            photoFile.create()
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
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

    override fun onPause() {
        super.onPause()
        indicator.stopAnimation()
    }

}

