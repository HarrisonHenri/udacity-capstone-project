package com.harrisonhenri.myapplication.companies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        binding.companyRecycler.adapter = CompanyListAdapter()

        binding.loginButton.setOnClickListener {
            val intent = viewModel.getLoginIntent()

            startActivityForResult(intent, SIGN_IN_REQUEST_CODE)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        findNavController().popBackStack()
    }
}