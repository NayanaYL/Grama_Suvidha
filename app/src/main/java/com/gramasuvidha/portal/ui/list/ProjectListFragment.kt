package com.gramasuvidha.portal.ui.list

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gramasuvidha.portal.MainActivity
import com.gramasuvidha.portal.R
import com.gramasuvidha.portal.data.local.AppDatabase
import com.gramasuvidha.portal.data.repository.ProjectRepository
import com.gramasuvidha.portal.databinding.FragmentProjectListBinding
import kotlinx.coroutines.launch

class ProjectListFragment : Fragment() {

    private var _binding: FragmentProjectListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProjectListViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = ProjectRepository(database.projectDao(), database.feedbackDao())
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
        _binding = FragmentProjectListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.settingsIcon.setOnClickListener {
            (activity as? MainActivity)?.showLanguageSelectionDialog()
        }

        binding.tabAll.setOnClickListener {
            viewModel.setFilter(null)
        }

        binding.tabOngoing.setOnClickListener {
            viewModel.setFilter("ONGOING")
        }

        binding.tabCompleted.setOnClickListener {
            viewModel.setFilter("COMPLETED")
        }

        // Card summary clicks
        binding.cardOngoing.setOnClickListener { viewModel.setFilter("ONGOING") }
        binding.cardCompleted.setOnClickListener { viewModel.setFilter("COMPLETED") }
        binding.cardPlanned.setOnClickListener { viewModel.setFilter("PLANNED") }
    }

    private fun setupRecyclerView() {
        val adapter = ProjectAdapter { project ->
            val bundle = Bundle().apply {
                putString("projectId", project.id)
            }
            findNavController().navigate(
                R.id.action_projectListFragment_to_projectDetailFragment,
                bundle
            )
        }
        binding.projectRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.projects.collect { projects ->
                    adapter.submitList(projects)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterStatus.collect { status ->
                    updateTabUI(status)
                }
            }
        }
    }

    private fun updateTabUI(selectedStatus: String?) {
        val tabs = listOf(binding.tabAll, binding.tabOngoing, binding.tabCompleted)
        tabs.forEach { it.deselect() }

        when (selectedStatus) {
            null -> binding.tabAll.select()
            "ONGOING" -> binding.tabOngoing.select()
            "COMPLETED" -> binding.tabCompleted.select()
        }
    }

    private fun TextView.select() {
        this.setBackgroundResource(R.drawable.bg_tab_selected)
        this.setTextColor(Color.BLACK)
        this.setTypeface(null, android.graphics.Typeface.BOLD)
    }

    private fun TextView.deselect() {
        this.background = null
        this.setTextColor("#757575".toColorInt())
        this.setTypeface(null, android.graphics.Typeface.NORMAL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
