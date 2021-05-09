package com.harrisonhenri.myapplication.companies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.harrisonhenri.myapplication.databinding.FragmentCompanyListBinding
import com.harrisonhenri.myapplication.utils.Constants.SIGN_IN_REQUEST_CODE

class CompanyList : Fragment() {
    private val viewModel: CompanyListViewModel by lazy {
        ViewModelProvider(this).get(CompanyListViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCompanyListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.companyRecycler.adapter = CompanyListAdapter(CompanyClickListener { menuId ->
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

    private fun logoutCallback() {
        AuthUI.getInstance().signOut(requireContext())
    }

    private fun loginCallback() {
        val intent = viewModel.getLoginIntent()
        startActivityForResult(intent, SIGN_IN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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