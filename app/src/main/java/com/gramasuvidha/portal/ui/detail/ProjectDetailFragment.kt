package com.gramasuvidha.portal.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gramasuvidha.portal.R
import com.gramasuvidha.portal.data.local.AppDatabase
import com.gramasuvidha.portal.data.repository.ProjectRepository
import com.gramasuvidha.portal.databinding.FragmentProjectDetailBinding
import com.gramasuvidha.portal.ui.feedback.FeedbackDialog
import kotlinx.coroutines.launch
import java.util.Locale

class ProjectDetailFragment : Fragment() {

    private var _binding: FragmentProjectDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProjectDetailViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = ProjectRepository(database.projectDao(), database.feedbackDao())
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return ProjectDetailViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = arguments?.getString("projectId") ?: ""
        viewModel.loadProject(projectId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.project.collect { project ->
                    project?.let {
                        val currentLang = Locale.getDefault().language
                        binding.project = it
                        
                        // Localized text
                        binding.detailName.text = if (currentLang == "kn") it.nameKn else it.nameEn
                        binding.detailDescription.text = if (currentLang == "kn") it.descriptionKn else it.descriptionEn
                        
                        // Update UI labels
                        binding.btnFeedback.text = getString(R.string.give_feedback)
                        binding.titleWorkSplit.text = getString(R.string.work_amount_split)
                        binding.titleComponents.text = getString(R.string.components_used)
                        
                        // Mock data for split and components (as they aren't in Entity yet)
                        if (it.id == "1") { // Main Road Repair
                            binding.layoutSplit.visibility = View.VISIBLE
                            binding.workSplitContent.text = if (currentLang == "kn") 
                                "ಡಾಂಬರು: ₹6,00,000\nಕೂಲಿ: ₹3,00,000\nಇತರೆ: ₹1,00,000" 
                                else "Bitumen: ₹6,00,000\nLabor: ₹3,00,000\nOther: ₹1,00,000"
                                
                            binding.componentsContent.text = if (currentLang == "kn")
                                "1. ಡಾಂಬರು (VG-30)\n2. ಜಲ್ಲಿ ಕಲ್ಲುಗಳು\n3. ರೋಲರ್ ಯಂತ್ರ"
                                else "1. Bitumen (VG-30)\n2. Crushed Stones\n3. Road Roller"
                        }
                    }
                }
            }
        }

        binding.btnFeedback.setOnClickListener {
            val dialog = FeedbackDialog.newInstance(projectId)
            dialog.show(childFragmentManager, "FeedbackDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
