package com.gramasuvidha.portal.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.gramasuvidha.portal.data.local.AppDatabase
import com.gramasuvidha.portal.data.local.entities.Feedback
import com.gramasuvidha.portal.data.repository.ProjectRepository
import com.gramasuvidha.portal.databinding.DialogFeedbackBinding
import kotlinx.coroutines.launch

class FeedbackDialog : DialogFragment() {

    private var _binding: DialogFeedbackBinding? = null
    private val binding get() = _binding!!

    private var projectId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectId = arguments?.getString(ARG_PROJECT_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val repository = ProjectRepository(database.projectDao(), database.feedbackDao())

        binding.btnSubmit.setOnClickListener {
            val description = binding.etComment.text.toString()
            val rating = binding.ratingBar.rating.toInt()

            if (description.isNotBlank()) {
                val feedback = Feedback(
                    projectId = projectId,
                    rating = rating,
                    issueDescription = description
                )
                lifecycleScope.launch {
                    repository.insertFeedback(feedback)
                    dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PROJECT_ID = "projectId"

        fun newInstance(projectId: String): FeedbackDialog {
            val fragment = FeedbackDialog()
            val args = Bundle()
            args.putString(ARG_PROJECT_ID, projectId)
            fragment.arguments = args
            return fragment
        }
    }
}
