package com.charaminstra.pleon.feed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.common_ui.DateUtils
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.PLeonDatePicker
import com.charaminstra.pleon.common_ui.PopUpImageMenu
import com.charaminstra.pleon.feed.databinding.FragmentFeedWriteBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileInputStream
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
    private lateinit var permissionMsg: ErrorToast
    lateinit var photoFile: PLeonImageFile


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFeedWriteBinding.inflate(layoutInflater)
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
        //test
        //sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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
            dlg.start("날짜 선택")
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
            binding.feedWriteImgRoot.visibility = View.GONE
            binding.feedWriteImgDeleteBtn.visibility = View.GONE
            binding.feedWriteImgAddBtn.visibility= View.VISIBLE
        }
        binding.completeBtn.setOnClickListener {
            feedWriteViewModel.postFeed(
                DateUtils(requireContext()).viewToSendServer(binding.feedWriteDate.text.toString()),
                binding.feedWriteContent.text.toString()
            )
        }
        return binding.root
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
            hideKeyboard(binding.feedWriteContent, requireContext())
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
            showKeyboard(binding.feedWriteContent)
        }
    }

    private fun initList() {
        plant_adapter = FeedPlantAdapter()
        plant_adapter.onItemClicked = { Id ->
            //plantId = Id
            feedWriteViewModel.plantId = Id
            feedWriteViewModel.getPlantName()
        }
        //test
        binding.feedWriteActionTagTv.text= resources.getString(com.charaminstra.pleon.feed_common.R.string.action_tag) + "오늘의모습"
        feedWriteViewModel.plantAction = ActionType.오늘의모습
        action_adapter = ActionAdapter()
        action_adapter.onItemClicked = { actionType ->
            feedWriteViewModel.plantAction = actionType
            binding.feedWriteActionTagTv.text= resources.getString(com.charaminstra.pleon.feed_common.R.string.action_tag) + actionType.name
        }

        action_adapter.refreshItems(
            listOf(
                ActionObject(ActionType.오늘의모습, R.drawable.ic_today),
                ActionObject(ActionType.물, R.drawable.ic_water),
                ActionObject(ActionType.통풍, R.drawable.ic_air),
                ActionObject(ActionType.분무, R.drawable.ic_spray),
                ActionObject(ActionType.분갈이, R.drawable.ic_repot),
                ActionObject(ActionType.가지치기, R.drawable.ic_prune),
                ActionObject(ActionType.잎, R.drawable.ic_leaf),
                ActionObject(ActionType.꽃, R.drawable.ic_flower),
                ActionObject(ActionType.영양제, R.drawable.ic_nutrition),
                ActionObject(ActionType.열매, R.drawable.ic_fruit),
                ActionObject(ActionType.기타, R.drawable.ic_etc)
            )
        )
    }

    private fun observeViewModel() {
        feedWriteViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            plant_adapter.refreshItems(it)
        })
        feedWriteViewModel.plantName.observe(viewLifecycleOwner, Observer {
            binding.feedWritePlantTagTv.text = resources.getString(com.charaminstra.pleon.feed_common.R.string.plant_tag) + feedWriteViewModel.plantName.value
        })
        feedWriteViewModel.postSuccess.observe(viewLifecycleOwner, Observer{
            if(it){
                navController.popBackStack()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        feedWriteViewModel.getPlantList()
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
                    binding.feedWriteImgRoot.visibility = View.VISIBLE
                    binding.feedWriteImgDeleteBtn.visibility = View.VISIBLE
                    binding.feedWriteImg.setImageBitmap(bitmap)
                    feedWriteViewModel.galleryToUrl(bitmap)
                }
            }
            REQUEST_TAKE_PHOTO -> {
                binding.feedWriteImgRoot.visibility = View.VISIBLE
                binding.feedWriteImgDeleteBtn.visibility = View.VISIBLE
                Glide.with(this).load(photoFile.currentPhotoPath).into(binding.feedWriteImg)
                feedWriteViewModel.cameraToUrl(FileInputStream(photoFile.currentPhotoPath))
            }
            else -> {
                ErrorToast(requireContext()).showMsg(
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
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "com.charaminstra.pleon.fileprovider",
            photoFile.create()
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_TAKE_PHOTO)
    }

    private fun showKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        view.requestFocus()
    }

}

