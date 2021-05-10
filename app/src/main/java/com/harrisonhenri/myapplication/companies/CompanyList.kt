package com.harrisonhenri.myapplication.companies

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.harrisonhenri.myapplication.databinding.FragmentCompanyListBinding
import com.harrisonhenri.myapplication.utils.Constants.SIGN_IN_REQUEST_CODE
import com.harrisonhenri.myapplication.utils.isPermissionGranted

class CompanyList : Fragment() {
    private val viewModel: CompanyListViewModel by lazy {
        ViewModelProvider(this).get(CompanyListViewModel::class.java)
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userLatLong: LatLng? = null
    private lateinit var binding: FragmentCompanyListBinding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        getUserLocation()

        binding = FragmentCompanyListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.companyRecycler.adapter = CompanyListAdapter(userLatLong, CompanyClickListener { menuId ->
            viewModel.companyClicked(menuId)
        })

        viewModel.isAuthenticated.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.authenticationButton.setOnClickListener {
                    logoutCallback()
                }
            } else {
                binding.authenticationButton.setOnClickListener {
                    loginCallback()
                }
            }
        })

        viewModel.navigateToMenu.observe(viewLifecycleOwner, { menu ->
            menu?.let {
                val action = CompanyListDirections.actionCompanyToMenu(menu)
                NavHostFragment.findNavController(this).navigate(action)
                viewModel.doneNavigation()
            }
        })


        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if ((activity as AppCompatActivity?)!!.isPermissionGranted()){
            fusedLocationClient.lastLocation.addOnSuccessListener {
                it?.let {
                    userLatLong = LatLng(it.latitude, it.longitude)
                    binding.companyRecycler.adapter = CompanyListAdapter(userLatLong, CompanyClickListener { menuId ->
                        viewModel.companyClicked(menuId)
                    })
                }
            }
        }
    }

    private fun logoutCallback() {
        AuthUI.getInstance().signOut(requireContext())
    }

    private fun loginCallback() {
        val intent = viewModel.getLoginIntent()
        startActivityForResult(intent, SIGN_IN_REQUEST_CODE)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}