package com.gramasuvidha.portal.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gramasuvidha.portal.data.local.AppDatabase
import com.gramasuvidha.portal.data.local.entities.User
import com.gramasuvidha.portal.data.repository.ProjectRepository
import com.gramasuvidha.portal.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val repository = ProjectRepository(database.projectDao(), database.feedbackDao(), database.userDao())

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val adminKey = binding.etAdminKey.text.toString()

            if (fullName.isBlank() || username.isBlank() || password.isBlank()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simple check for admin registration - in a real app, this would be more secure
            val isAdmin = adminKey == "1234" 

            lifecycleScope.launch {
                val existingUser = repository.getUserByUsername(username)
                if (existingUser != null) {
                    Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show()
                } else {
                    val newUser = User(username, password, fullName, isAdmin)
                    repository.insertUser(newUser)
                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
