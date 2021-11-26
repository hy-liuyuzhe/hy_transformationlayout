package com.yuwq.hy_transformationlayout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yuwq.hy_transformationlayout.DetailActivity
import com.yuwq.hy_transformationlayout.MockUtil
import com.yuwq.hy_transformationlayout.adapter.PosterAdapter
import com.yuwq.hy_transformationlayout.adapter.PosterMenuAdapter
import com.yuwq.hy_transformationlayout.databinding.FragmentHomeBinding
import com.yuwq.transformationlayout.XClickUtils

class HomeFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            val args = Bundle()
            return HomeFragment()
        }
    }

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.adapter = PosterAdapter()
                .also { it.setNewData(MockUtil.getMockPosters()) }
            recyclerMenuView.adapter = PosterMenuAdapter().also { it.setNewData(MockUtil.getMockPosters()) }
        }

        binding.fabLaunchDetail.setOnClickListener {
            if (XClickUtils.isFastClick()) return@setOnClickListener
            DetailActivity.startActivity(
                it.context,
                binding.transformationLayoutWithDetail,
                MockUtil.getMockPoster()
            )
        }
        binding.fabMenu.setOnClickListener {
            if (XClickUtils.isFastClick(it.id)) return@setOnClickListener
            binding.transformationLayoutWithMenu.startTransform()
        }
        binding.layoutWrite.setOnClickListener {
            if (XClickUtils.isFastClick(it.id)) return@setOnClickListener
            binding.transformationLayoutWithMenu.finishTransform()
        }
    }
}