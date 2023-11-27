package com.presidentserviceconsult.dimaz.black.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.presidentserviceconsult.dimaz.R
import com.presidentserviceconsult.dimaz.black.cloaca.init_sdk.InitSDK
import com.presidentserviceconsult.dimaz.databinding.FragmentLoadingBinding


class LoudingFragment : Fragment() {


    val binding by lazy { FragmentLoadingBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitSDK(
            this.requireContext().applicationContext,
            { findNavController().navigate(R.id.action_loading_to_white) },
            {
                val action = LoudingFragmentDirections.actionLoadingToBlack(it)
                findNavController().navigate(action)
            }
        ).start()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


}