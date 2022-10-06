package com.charaminstra.pleon.plant_register.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.PLeonMsgDialog
import com.charaminstra.pleon.common_ui.PopUpImageMenu
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.PlantSearchAdapter
import com.charaminstra.pleon.plant_register.PlantSearchViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantSpeciesBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileInputStream

@AndroidEntryPoint
class PlantSpeciesFragment : Fragment() {
    private val TAG = javaClass.name

    private lateinit var binding: FragmentPlantSpeciesBinding
    private lateinit var adapter: PlantSearchAdapter
    private val searchViewModel: PlantSearchViewModel by viewModels()
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    lateinit var photoFile: PLeonImageFile
    lateinit var navController: NavController

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(PLANT_SPECIES_VIEW, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlantSpeciesBinding.inflate(layoutInflater)
        navController = this.findNavController()

        if(!viewModel.plantDetectionResultLabel.value.isNullOrBlank()){
            binding.plantSpeciesEt.setText(viewModel.plantDetectionResultLabel.value)
        }

        binding.plantSpeciesBackBtn.setOnClickListener {
            viewModel.clearPlantDetectionUrl()
            viewModel.nextStep = false
            navController.popBackStack()
        }

        binding.imageSearchButton.setOnClickListener {
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

            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(PLANT_SPECIES_IMG_SEARCH_BTN_CLICK, bundle)
        }

        initList()
        observeViewModel()
        searchViewModel.getPlantSpecies()
        binding.plantSpeciesRecyclerview.adapter = adapter
        binding.plantSpeciesRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        binding.plantSpeciesSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //확인 누르면
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            //글자 칠때 마다 변함
            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isEmpty()){
                    binding.plantSpeciesEt.visibility = View.VISIBLE
                    binding.plantSpeciesRecyclerview.visibility = View.GONE
                }else{
                    binding.plantSpeciesEt.visibility = View.GONE
                    binding.plantSpeciesRecyclerview.visibility = View.VISIBLE
                }
                searchViewModel.searchFilter(newText)
                return true
            }
        })

        binding.plantSpeciesNextBtn.setOnClickListener {
            if(binding.plantSpeciesEt.text.isNullOrBlank()){
                ErrorToast(requireContext()).showMsg(resources.getString(R.string.plant_species_fragment_error),binding.plantSpeciesEt.y)
            }else{
                viewModel.setSpecies(binding.plantSpeciesEt.text.toString())
                navController.navigate(R.id.plant_species_fragment_to_plant_name_fragment)
            }
        }

        binding.plantSpeciesSkipBtn.setOnClickListener {
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
            REQUEST_GALLERY -> {
                data ?: return
                val uri = data.data as Uri
                activity?.contentResolver?.openInputStream(uri).let {
                    val bitmap = BitmapFactory.decodeStream(it)
                    viewModel.speciesGalleryToUrl(bitmap)
                }
            }
            REQUEST_TAKE_PHOTO -> {
                viewModel.speciesCameraToUrl(FileInputStream(photoFile.currentPhotoPath))
            }
            else -> {
                ErrorToast(requireContext()).showMsg(
                    resources.getString(com.charaminstra.pleon.common_ui.R.string.image_error),
                    binding.plantSpeciesEt.y
                )
            }
        }
    }

    private fun initList() {
        adapter = PlantSearchAdapter()
        adapter.onItemClicked = { name ->
            binding.plantSpeciesEt.visibility = View.VISIBLE
            binding.plantSpeciesEt.setText(name)
            binding.plantSpeciesRecyclerview.visibility = View.GONE

            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(PLANT_SPECIES_SEARCH_ITEM_CLICK, bundle)
        }
    }

    private fun observeViewModel() {
        searchViewModel.plantSpeciesList.observe(viewLifecycleOwner, Observer {
            adapter.refreshItems(it)
        })
        searchViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            adapter.refreshItems(it)
        })
        viewModel.plantDetectionUrlResponse.observe(viewLifecycleOwner, Observer{
            if(it == null){

            }else{
                navController.navigate(R.id.plant_species_fragment_to_plant_detection_waiting_fragment)
            }

        })
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