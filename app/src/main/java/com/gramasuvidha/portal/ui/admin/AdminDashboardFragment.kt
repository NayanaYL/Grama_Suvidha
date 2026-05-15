package com.gramasuvidha.portal.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gramasuvidha.portal.R
import com.gramasuvidha.portal.data.local.AppDatabase
import com.gramasuvidha.portal.data.repository.ProjectRepository
import com.gramasuvidha.portal.databinding.FragmentAdminDashboardBinding
import com.gramasuvidha.portal.ui.list.ProjectAdapter
import com.gramasuvidha.portal.ui.list.ProjectListViewModel
import kotlinx.coroutines.launch

class AdminDashboardFragment : Fragment() {

    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProjectListViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = ProjectRepository(database.projectDao(), database.feedbackDao(), database.userDao())
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ProjectListViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        
        binding.fabAddProject.setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboardFragment_to_addEditProjectFragment)
        }
    }

    private fun setupRecyclerView() {
        val adapter = ProjectAdapter { project ->
            val bundle = Bundle().apply {
                putString("projectId", project.id)
            }
            // Navigate to Edit mode
            findNavController().navigate(R.id.action_adminDashboardFragment_to_addEditProjectFragment, bundle)
        }
        binding.rvAdminProjects.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.projects.collect { projects ->
                    adapter.submitList(projects)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
