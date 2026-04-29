package com.gramasuvidha.portal

import android.app.Application
import com.gramasuvidha.portal.data.local.AppDatabase
import com.gramasuvidha.portal.data.local.entities.ProjectEntity
import com.gramasuvidha.portal.data.repository.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class GramaApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        
        val database = AppDatabase.getDatabase(this)
        val repository = ProjectRepository(database.projectDao(), database.feedbackDao())
        
        applicationScope.launch {
            val dummyProjects = listOf(
                ProjectEntity(
                    "1", 
                    "Main Road Repair - Ward 4", 
                    "ಮುಖ್ಯ ರಸ್ತೆ ದುರಸ್ತಿ - ವಾರ್ಡ್ 4", 
                    "Ward 4, Maduvaluru Village",
                    "ವಾರ್ಡ್ 4, ಮಡುವಲೂರು ಗ್ರಾಮ",
                    "₹10,00,000", 
                    "ONGOING", 
                    "ಪ್ರಗತಿಯಲ್ಲಿದೆ", 
                    85, 
                    "Repair and maintenance of the main connecting road.", 
                    "ಮುಖ್ಯ ಸಂಪರ್ಕ ರಸ್ತೆಯ ದುರಸ್ತಿ ಮತ್ತು ನಿರ್ವಹಣೆ.", 
                    "https://images.unsplash.com/photo-1541675154750-0444c7d51e8e?q=80&w=400", 
                    ""
                ),
                ProjectEntity(
                    "2", 
                    "New Borewell Installation", 
                    "ಹೊಸ ಬೋರ್‌ವೆಲ್ ಅಳವಡಿಕೆ", 
                    "North Suburb",
                    "ಉತ್ತರ ಉಪನಗರ",
                    "₹3,50,000", 
                    "COMPLETED", 
                    "ಪೂರ್ಣಗೊಂಡಿದೆ", 
                    100, 
                    "Drilling and installation of solar borewell.", 
                    "ಸೌರ ಬೋರ್‌ವೆಲ್ ಕೊರೆಯುವಿಕೆ ಮತ್ತು ಅಳವಡಿಕೆ.", 
                    "https://images.unsplash.com/photo-1581094288338-2314dddb7ec4?q=80&w=400", 
                    ""
                ),
                ProjectEntity(
                    "3", 
                    "Community Hall Renovation", 
                    "ಸಮುದಾಯ ಭವನ ನವೀಕರಣ", 
                    "Central Square",
                    "ಕೇಂದ್ರ ಚೌಕ",
                    "₹8,00,000", 
                    "PLANNED", 
                    "ಯೋಜಿಸಲಾಗಿದೆ", 
                    0, 
                    "Renovation of existing community hall structure.", 
                    "ಅಸ್ತಿತ್ವದಲ್ಲಿರುವ ಸಮುದಾಯ ಭವನದ ನವೀಕರಣ.", 
                    "https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?q=80&w=400", 
                    ""
                )
            )
            repository.insertProjects(dummyProjects)
        }
    }
}
