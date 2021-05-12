package com.harrisonhenri.myapplication.favoritelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.harrisonhenri.myapplication.databinding.FragmentFavoriteListBinding

class FavoriteList : Fragment() {
    private val viewModel: FavoriteListViewModel by lazy {
        ViewModelProvider(this).get(FavoriteListViewModel::class.java)
    }
    private lateinit var binding: FragmentFavoriteListBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.favoriteRecycler.adapter = FavoriteListAdapter()

        return binding.root
    }
}