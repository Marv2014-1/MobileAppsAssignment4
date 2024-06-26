package com.example.messageapp.code.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messageapp.databinding.FragmentUserScreenBinding

class UserListFragment : Fragment() {

    private var _binding: FragmentUserScreenBinding? = null
    // Check to make sure binding is not null
    private val binding get() = _binding ?: throw IllegalStateException("If Null, error with binding FragmentUserScreenBinding")

    // inflate the layout for this UserListFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // clearing binding to release resources
    }
}