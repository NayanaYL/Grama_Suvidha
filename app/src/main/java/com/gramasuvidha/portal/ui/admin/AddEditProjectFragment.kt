package com.gramasuvidha.portal.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gramasuvidha.portal.data.local.AppDatabase
import com.gramasuvidha.portal.data.local.entities.ProjectEntity
import com.gramasuvidha.portal.data.repository.ProjectRepository
import com.gramasuvidha.portal.databinding.FragmentAddEditProjectBinding
import kotlinx.coroutines.launch

class AddEditProjectFragment : Fragment() {

    private var _binding: FragmentAddEditProjectBinding? = null
    private val binding get() = _binding!!

    private var projectId: String? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectId = arguments?.getString("projectId")
        isEditMode = projectId != null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val repository = ProjectRepository(database.projectDao(), database.feedbackDao(), database.userDao())

        if (isEditMode) {
            lifecycleScope.launch {
                val project = repository.getProjectById(projectId!!)
                project?.let { populateFields(it) }
            }
        }

        binding.btnSave.setOnClickListener {
            saveProject(repository)
        }
    }

    private fun populateFields(project: ProjectEntity) {
        binding.etProjectId.setText(project.id)
        binding.etProjectId.isEnabled = false // Cannot change ID in edit mode
        binding.etNameEn.setText(project.nameEn)
        binding.etNameKn.setText(project.nameKn)
        binding.etStatusEn.setText(project.statusEn)
        binding.etBudget.setText(project.budget)
        binding.etProgress.setText(project.completionPercentage.toString())
        binding.etDescEn.setText(project.descriptionEn)
        binding.etImageUrl.setText(project.beforeImageUrl)
    }

    private fun saveProject(repository: ProjectRepository) {
        val id = binding.etProjectId.text.toString()
        val nameEn = binding.etNameEn.text.toString()
        val nameKn = binding.etNameKn.text.toString()
        val statusEn = binding.etStatusEn.text.toString()
        val budget = binding.etBudget.text.toString()
        val progress = binding.etProgress.text.toString().toIntOrNull() ?: 0
        val descEn = binding.etDescEn.text.toString()
        val imageUrl = binding.etImageUrl.text.toString()

        if (id.isBlank() || nameEn.isBlank()) {
            Toast.makeText(context, "Please enter ID and Name", Toast.LENGTH_SHORT).show()
            return
        }

        val project = ProjectEntity(
            id = id,
            nameEn = nameEn,
            nameKn = nameKn,
            locationEn = "Ward 4, Maduvaluru", // Default or add field
            locationKn = "ವಾರ್ಡ್ 4, ಮಡುವಲೂರು",
            budget = budget,
            expectedCompletionDate = "2025-12-31", // Default or add field
            statusEn = statusEn,
            statusKn = if (statusEn == "COMPLETED") "ಪೂರ್ಣಗೊಂಡಿದೆ" else "ಪ್ರಗತಿಯಲ್ಲಿದೆ",
            completionPercentage = progress,
            descriptionEn = descEn,
            descriptionKn = "ವಿವರಣೆ ಶೀಘ್ರದಲ್ಲೇ ಬರಲಿದೆ",
            beforeImageUrl = imageUrl,
            afterImageUrl = ""
        )

        lifecycleScope.launch {
            if (isEditMode) {
                repository.updateProject(project)
                Toast.makeText(context, "Project updated", Toast.LENGTH_SHORT).show()
            } else {
                repository.insertProject(project)
                Toast.makeText(context, "Project added", Toast.LENGTH_SHORT).show()
            }
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
