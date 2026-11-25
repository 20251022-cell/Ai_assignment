package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.Candidate
import com.example.model.CandidateStatus
import com.example.model.DashboardStats
import com.example.model.JobRole
import com.example.model.SampleDataCatalog
import com.example.model.SkillMatchReport
import com.example.model.SkillMatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

data class CandidateFormState(
    val name: String = "",
    val email: String = "",
    val qualification: String = "",
    val skills: String = "",
    val yearsOfExperience: String = "",
    val jobRoleId: String = SampleDataCatalog.jobRoles.first().id,
    val nameError: String? = null,
    val emailError: String? = null,
    val qualificationError: String? = null,
    val skillsError: String? = null,
    val experienceError: String? = null
)

data class RecruitmentUiState(
    val candidates: List<Candidate> = SampleDataCatalog.initialCandidates,
    val jobRoles: List<JobRole> = SampleDataCatalog.jobRoles,
    val selectedCandidateId: String = SampleDataCatalog.initialCandidates.first().id,
    val formState: CandidateFormState = CandidateFormState(),
    val screeningTab: Int = 0, // 0 = Add Candidate Form, 1 = Candidate List
    val detailCandidate: Candidate? = null,
    val submissionFeedback: String? = null
) {
    val dashboardStats: DashboardStats
        get() = DashboardStats(
            totalCandidates = candidates.size,
            shortlistedCandidates = candidates.count { it.status == CandidateStatus.SHORTLISTED },
            pendingReviews = candidates.count { it.status == CandidateStatus.PENDING_REVIEW },
            interviewsScheduled = candidates.count { it.status == CandidateStatus.INTERVIEW_SCHEDULED }
        )

    val selectedCandidate: Candidate
        get() = candidates.find { it.id == selectedCandidateId } ?: candidates.first()

    val selectedReport: SkillMatchReport
        get() {
            val candidate = selectedCandidate
            val role = SampleDataCatalog.roleForId(candidate.jobRoleId)
            return SkillMatcher.generateReport(candidate, role)
        }
}

class RecruitmentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecruitmentUiState())
    val uiState: StateFlow<RecruitmentUiState> = _uiState.asStateFlow()

    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun onNameChanged(value: String) {
        _uiState.update {
            it.copy(
                formState = it.formState.copy(name = value, nameError = null)
            )
        }
    }

    fun onEmailChanged(value: String) {
        _uiState.update {
            it.copy(
                formState = it.formState.copy(email = value, emailError = null)
            )
        }
    }

    fun onQualificationChanged(value: String) {
        _uiState.update {
            it.copy(
                formState = it.formState.copy(qualification = value, qualificationError = null)
            )
        }
    }

    fun onSkillsChanged(value: String) {
        _uiState.update {
            it.copy(
                formState = it.formState.copy(skills = value, skillsError = null)
            )
        }
    }

    fun onQuickSkillAdded(skill: String) {
        _uiState.update { state ->
            val currentSkills = state.formState.skills
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
            if (currentSkills.any { it.equals(skill, ignoreCase = true) }) {
                state
            } else {
                val updated = (currentSkills + skill).joinToString(", ")
                state.copy(
                    formState = state.formState.copy(skills = updated, skillsError = null)
                )
            }
        }
    }

    fun onExperienceChanged(value: String) {
        _uiState.update {
            it.copy(
                formState = it.formState.copy(yearsOfExperience = value, experienceError = null)
            )
        }
    }

    fun onJobRoleSelected(roleId: String) {
        _uiState.update {
            it.copy(
                formState = it.formState.copy(jobRoleId = roleId)
            )
        }
    }

    fun onScreeningTabChanged(tabIndex: Int) {
        _uiState.update { it.copy(screeningTab = tabIndex) }
    }

    fun selectCandidateForReport(candidateId: String) {
        _uiState.update { it.copy(selectedCandidateId = candidateId) }
    }

    fun openCandidateDetail(candidate: Candidate) {
        _uiState.update { it.copy(detailCandidate = candidate) }
    }

    fun dismissCandidateDetail() {
        _uiState.update { it.copy(detailCandidate = null) }
    }

    fun clearSubmissionFeedback() {
        _uiState.update { it.copy(submissionFeedback = null) }
    }

    fun submitCandidate(): Boolean {
        val form = _uiState.value.formState
        val trimmedName = form.name.trim()
        val trimmedEmail = form.email.trim()
        val trimmedQualification = form.qualification.trim()
        val parsedSkills = form.skills
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinctBy { it.lowercase() }
        val parsedExp = form.yearsOfExperience.trim().toDoubleOrNull()

        val nameError = when {
            trimmedName.isEmpty() -> "Candidate name is required"
            trimmedName.length < 2 -> "Enter at least 2 characters"
            else -> null
        }

        val emailError = when {
            trimmedEmail.isEmpty() -> "Email address is required"
            !emailRegex.matches(trimmedEmail) -> "Enter a valid email address"
            else -> null
        }

        val qualificationError = when {
            trimmedQualification.isEmpty() -> "Qualification is required"
            trimmedQualification.length < 2 -> "Enter a valid qualification"
            else -> null
        }

        val skillsError = when {
            parsedSkills.isEmpty() -> "Enter at least one skill (comma-separated)"
            else -> null
        }

        val experienceError = when {
            form.yearsOfExperience.trim().isEmpty() -> "Years of experience is required"
            parsedExp == null -> "Enter a valid number (e.g. 2.5)"
            parsedExp < 0.0 || parsedExp > 50.0 -> "Enter experience between 0 and 50 years"
            else -> null
        }

        if (nameError != null || emailError != null || qualificationError != null ||
            skillsError != null || experienceError != null
        ) {
            _uiState.update {
                it.copy(
                    formState = form.copy(
                        nameError = nameError,
                        emailError = emailError,
                        qualificationError = qualificationError,
                        skillsError = skillsError,
                        experienceError = experienceError
                    )
                )
            }
            return false
        }

        val newCandidate = Candidate(
            id = "cand_${UUID.randomUUID().toString().take(8)}",
            name = trimmedName,
            email = trimmedEmail,
            qualification = trimmedQualification,
            skills = parsedSkills,
            yearsOfExperience = parsedExp ?: 0.0,
            jobRoleId = form.jobRoleId,
            status = CandidateStatus.PENDING_REVIEW,
            isUserAdded = true
        )

        _uiState.update { state ->
            state.copy(
                candidates = listOf(newCandidate) + state.candidates,
                selectedCandidateId = newCandidate.id,
                formState = CandidateFormState(jobRoleId = form.jobRoleId),
                screeningTab = 1,
                submissionFeedback = "${newCandidate.name} saved locally and added to candidate list."
            )
        }
        return true
    }

    fun reportFor(candidate: Candidate): SkillMatchReport {
        val role = SampleDataCatalog.roleForId(candidate.jobRoleId)
        return SkillMatcher.generateReport(candidate, role)
    }
}
