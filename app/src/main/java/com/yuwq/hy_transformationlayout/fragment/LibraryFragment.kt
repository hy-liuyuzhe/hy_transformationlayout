package com.yuwq.hy_transformationlayout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.yuwq.hy_transformationlayout.MockUtil
import com.yuwq.hy_transformationlayout.R
import com.yuwq.hy_transformationlayout.adapter.PosterAdapter
import com.yuwq.hy_transformationlayout.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.adapter = PosterAdapter()
                .also { it.setNewData(MockUtil.getMockPosters()) }
            fab.setOnClickListener {
                transformationLayout.startTransform()
            }
            menuCard.setOnClickListener {
                transformationLayout.finishTransform()
            }
        }
    }
}