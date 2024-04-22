package com.example.messageapp.code.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messageapp.databinding.FragmentLoginScreenBinding
import com.example.messageapp.code.logic.Mediator

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding?: throw IllegalStateException("If Null, error with binding FragmentLoginScreenBinding")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Listen for login button clicks
        binding.login.setOnClickListener {
            val username: String = binding.username.text.toString()
            val password: String = binding.password.text.toString()
            // Assuming Mediator can still be called in this context
            Mediator.login(requireActivity(), username, password)
        }

        // Listen for new user button clicks
        binding.newUser.setOnClickListener {
            Mediator.newUser(requireActivity())
        }

        // Retrieve and display status updates
        arguments?.getString("status")?.let {
            binding.mainUpdates.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}