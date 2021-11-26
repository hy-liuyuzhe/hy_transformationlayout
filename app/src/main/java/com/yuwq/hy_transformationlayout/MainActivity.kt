package com.yuwq.hy_transformationlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.yuwq.hy_transformationlayout.databinding.ActivityMainBinding
import com.yuwq.hy_transformationlayout.fragment.HomeFragment
import com.yuwq.hy_transformationlayout.fragment.LibraryFragment
import com.yuwq.transformationlayout.onTransformationStartContainer

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragments = arrayListOf(HomeFragment.newInstance(), LibraryFragment.newInstance())
        with(binding.viewPager) {
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int = fragments.size
                override fun createFragment(position: Int): Fragment = fragments[position]
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {

                }
            })
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> binding.viewPager.currentItem = 0
                R.id.action_library -> binding.viewPager.currentItem = 1
            }
            true
        }
    }

}