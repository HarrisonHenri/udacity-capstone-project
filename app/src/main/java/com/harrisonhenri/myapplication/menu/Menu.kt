package com.harrisonhenri.myapplication.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.harrisonhenri.myapplication.databinding.FragmentMenuBinding

class Menu : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMenuBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.categoriesRecycler.adapter = CategoryListAdapter()
        binding.categoriesRecycler.layoutManager = GridLayoutManager(activity, 2)

        binding.menu = MenuArgs.fromBundle(requireArguments()).Menu

        return binding.root
    }
}