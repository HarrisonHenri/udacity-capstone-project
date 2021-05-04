package com.harrisonhenri.myapplication.companies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.harrisonhenri.myapplication.databinding.FragmentCompanyListBinding

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
        return binding.root
    }
}