package com.gramasuvidha.portal.data.repository

import com.gramasuvidha.portal.data.local.dao.FeedbackDao
import com.gramasuvidha.portal.data.local.dao.ProjectDao
import com.gramasuvidha.portal.data.local.dao.UserDao
import com.gramasuvidha.portal.data.local.entities.Feedback
import com.gramasuvidha.portal.data.local.entities.ProjectEntity
import com.gramasuvidha.portal.data.local.entities.User
import kotlinx.coroutines.flow.Flow

class ProjectRepository(
    private val projectDao: ProjectDao,
    private val feedbackDao: FeedbackDao,
    private val userDao: UserDao
) {
    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    fun getProjectsByStatus(status: String): Flow<List<ProjectEntity>> {
        return projectDao.getProjectsByStatus(status)
    }

    suspend fun getProjectById(id: String): ProjectEntity? {
        return projectDao.getProjectById(id)
    }

    suspend fun insertProjects(projects: List<ProjectEntity>) {
        projectDao.insertProjects(projects)
    }

    suspend fun insertProject(project: ProjectEntity) {
        projectDao.insertProject(project)
    }

    suspend fun updateProject(project: ProjectEntity) {
        projectDao.updateProject(project)
    }

    suspend fun deleteProject(project: ProjectEntity) {
        projectDao.deleteProject(project)
    }

    suspend fun deleteAllProjects() {
        projectDao.deleteAllProjects()
    }

    fun getFeedbackForProject(projectId: String): Flow<List<Feedback>> {
        return feedbackDao.getFeedbackForProject(projectId)
    }

    suspend fun insertFeedback(feedback: Feedback) {
        feedbackDao.insertFeedback(feedback)
    }

    // User operations
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
}
