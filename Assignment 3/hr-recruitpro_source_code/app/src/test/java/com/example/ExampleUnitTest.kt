package com.example

import com.example.model.SampleDataCatalog
import com.example.model.SkillMatcher
import com.example.viewmodel.RecruitmentViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun submitEmptyCandidate_failsValidationWithErrors() {
        val viewModel = RecruitmentViewModel()
        val initialCount = viewModel.uiState.value.candidates.size

        val result = viewModel.submitCandidate()

        assertFalse(result)
        val form = viewModel.uiState.value.formState
        assertNotNull(form.nameError)
        assertNotNull(form.emailError)
        assertNotNull(form.qualificationError)
        assertNotNull(form.skillsError)
        assertNotNull(form.experienceError)
        assertEquals(initialCount, viewModel.uiState.value.candidates.size)
    }

    @Test
    fun submitInvalidEmailAndExperience_setsSpecificErrors() {
        val viewModel = RecruitmentViewModel()
        viewModel.onNameChanged("Kavya Rao")
        viewModel.onEmailChanged("not-an-email")
        viewModel.onQualificationChanged("B.Tech CS")
        viewModel.onSkillsChanged("Kotlin, Git")
        viewModel.onExperienceChanged("abc")

        val result = viewModel.submitCandidate()

        assertFalse(result)
        val form = viewModel.uiState.value.formState
        assertNull(form.nameError)
        assertNotNull(form.emailError)
        assertNull(form.qualificationError)
        assertNull(form.skillsError)
        assertNotNull(form.experienceError)
    }

    @Test
    fun submitValidCandidate_savesLocallyUpdatesStatsAndSelectsReport() {
        val viewModel = RecruitmentViewModel()
        val initialTotal = viewModel.uiState.value.dashboardStats.totalCandidates
        val initialPending = viewModel.uiState.value.dashboardStats.pendingReviews

        viewModel.onNameChanged("Kavya Rao")
        viewModel.onEmailChanged("kavya.rao@example.com")
        viewModel.onQualificationChanged("B.Tech Computer Science")
        viewModel.onJobRoleSelected("android_dev")
        viewModel.onQuickSkillAdded("Kotlin")
        viewModel.onQuickSkillAdded("Jetpack Compose")
        viewModel.onQuickSkillAdded("Coroutines")
        viewModel.onExperienceChanged("2.5")

        val result = viewModel.submitCandidate()

        assertTrue(result)
        val state = viewModel.uiState.value
        assertEquals(initialTotal + 1, state.dashboardStats.totalCandidates)
        assertEquals(initialPending + 1, state.dashboardStats.pendingReviews)
        assertEquals(1, state.screeningTab)
        assertEquals("Kavya Rao", state.candidates.first().name)
        assertEquals(state.candidates.first().id, state.selectedCandidateId)

        val report = state.selectedReport
        assertEquals(60, report.matchPercentage) // 3 of 5 required Android skills
        assertEquals(3, report.matchedSkills.size)
        assertEquals(2, report.missingSkills.size)
    }

    @Test
    fun skillMatcher_calculatesExactMatchPercentage() {
        val role = SampleDataCatalog.roleForId("hr_specialist")
        val candidate = SampleDataCatalog.initialCandidates.first { it.id == "cand_2" }

        val report = SkillMatcher.generateReport(candidate, role)

        assertEquals(80, report.matchPercentage) // 4 of 5 HR skills matched
        assertTrue(report.matchedSkills.contains("Talent Acquisition"))
        assertTrue(report.missingSkills.contains("HRIS"))
        assertTrue(report.screeningSummary.contains("demonstration only"))
    }
}
