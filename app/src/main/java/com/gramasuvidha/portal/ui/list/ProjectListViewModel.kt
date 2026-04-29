package com.gramasuvidha.portal.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gramasuvidha.portal.data.local.entities.ProjectEntity
import com.gramasuvidha.portal.data.repository.ProjectRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class ProjectListViewModel(private val repository: ProjectRepository) : ViewModel() {

    private val _filterStatus = MutableStateFlow<String?>(null)
    val filterStatus: StateFlow<String?> = _filterStatus.asStateFlow()

    val projects: StateFlow<List<ProjectEntity>> = _filterStatus
        .flatMapLatest { status ->
            if (status == null) {
                repository.allProjects
            } else {
                repository.getProjectsByStatus(status)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setFilter(status: String?) {
        _filterStatus.value = status
    }
}
