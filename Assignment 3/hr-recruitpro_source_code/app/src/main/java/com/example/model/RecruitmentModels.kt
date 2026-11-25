package com.example.model

import kotlin.math.roundToInt

enum class CandidateStatus(val label: String) {
    SHORTLISTED("Shortlisted"),
    PENDING_REVIEW("Pending Review"),
    INTERVIEW_SCHEDULED("Interview Scheduled")
}

data class JobRole(
    val id: String,
    val title: String,
    val department: String,
    val requiredSkills: List<String>,
    val minRecommendedYears: Double
)

data class Candidate(
    val id: String,
    val name: String,
    val email: String,
    val qualification: String,
    val skills: List<String>,
    val yearsOfExperience: Double,
    val jobRoleId: String,
    val status: CandidateStatus,
    val isUserAdded: Boolean = false
)

data class SkillMatchReport(
    val candidate: Candidate,
    val jobRole: JobRole,
    val matchedSkills: List<String>,
    val missingSkills: List<String>,
    val additionalSkills: List<String>,
    val matchPercentage: Int,
    val screeningSummary: String,
    val recommendations: List<String>
)

data class DashboardStats(
    val totalCandidates: Int,
    val shortlistedCandidates: Int,
    val pendingReviews: Int,
    val interviewsScheduled: Int
)

object SampleDataCatalog {

    val jobRoles: List<JobRole> = listOf(
        JobRole(
            id = "android_dev",
            title = "Android Developer",
            department = "Mobile Engineering",
            requiredSkills = listOf("Kotlin", "Jetpack Compose", "Coroutines", "REST APIs", "Git"),
            minRecommendedYears = 2.0
        ),
        JobRole(
            id = "hr_specialist",
            title = "HR Specialist",
            department = "People Operations",
            requiredSkills = listOf("Talent Acquisition", "Screening", "HRIS", "Onboarding", "Communication"),
            minRecommendedYears = 2.0
        ),
        JobRole(
            id = "data_analyst",
            title = "Data Analyst",
            department = "Business Intelligence",
            requiredSkills = listOf("SQL", "Python", "Data Visualization", "Excel", "Statistics"),
            minRecommendedYears = 1.5
        ),
        JobRole(
            id = "ui_ux_designer",
            title = "UI/UX Designer",
            department = "Product Design",
            requiredSkills = listOf("Figma", "Wireframing", "Design Systems", "User Research", "Prototyping"),
            minRecommendedYears = 2.0
        ),
        JobRole(
            id = "project_manager",
            title = "Project Manager",
            department = "Delivery & PMO",
            requiredSkills = listOf("Agile", "Scrum", "Stakeholder Management", "Risk Planning", "Jira"),
            minRecommendedYears = 3.0
        ),
        JobRole(
            id = "qa_engineer",
            title = "QA Engineer",
            department = "Quality Assurance",
            requiredSkills = listOf("Test Automation", "Kotlin", "JUnit", "Regression Testing", "Bug Tracking"),
            minRecommendedYears = 1.5
        )
    )

    val initialCandidates: List<Candidate> = listOf(
        Candidate(
            id = "cand_1",
            name = "Aarav Mehta",
            email = "aarav.mehta@samplemail.com",
            qualification = "B.Tech in Computer Science",
            skills = listOf("Kotlin", "Jetpack Compose", "Coroutines", "Git", "Firebase"),
            yearsOfExperience = 3.5,
            jobRoleId = "android_dev",
            status = CandidateStatus.SHORTLISTED
        ),
        Candidate(
            id = "cand_2",
            name = "Priya Nair",
            email = "priya.nair@samplemail.com",
            qualification = "MBA in Human Resources",
            skills = listOf("Talent Acquisition", "Screening", "Onboarding", "Communication", "LinkedIn Recruiter"),
            yearsOfExperience = 4.0,
            jobRoleId = "hr_specialist",
            status = CandidateStatus.INTERVIEW_SCHEDULED
        ),
        Candidate(
            id = "cand_3",
            name = "Rohan Kulkarni",
            email = "rohan.k@samplemail.com",
            qualification = "B.Sc. in Statistics",
            skills = listOf("SQL", "Excel", "Data Visualization", "Tableau"),
            yearsOfExperience = 2.0,
            jobRoleId = "data_analyst",
            status = CandidateStatus.PENDING_REVIEW
        ),
        Candidate(
            id = "cand_4",
            name = "Sneha Verma",
            email = "sneha.verma@samplemail.com",
            qualification = "B.Des in Interaction Design",
            skills = listOf("Figma", "Wireframing", "Design Systems", "User Research", "Prototyping"),
            yearsOfExperience = 3.0,
            jobRoleId = "ui_ux_designer",
            status = CandidateStatus.SHORTLISTED
        ),
        Candidate(
            id = "cand_5",
            name = "Vikram Joshi",
            email = "vikram.joshi@samplemail.com",
            qualification = "B.E. in Information Technology",
            skills = listOf("Agile", "Scrum", "Jira", "Documentation"),
            yearsOfExperience = 4.5,
            jobRoleId = "project_manager",
            status = CandidateStatus.INTERVIEW_SCHEDULED
        ),
        Candidate(
            id = "cand_6",
            name = "Ananya Das",
            email = "ananya.das@samplemail.com",
            qualification = "MCA (Master of Computer Applications)",
            skills = listOf("Test Automation", "JUnit", "Bug Tracking", "Postman"),
            yearsOfExperience = 1.5,
            jobRoleId = "qa_engineer",
            status = CandidateStatus.PENDING_REVIEW
        )
    )

    fun roleForId(roleId: String): JobRole =
        jobRoles.find { it.id == roleId } ?: jobRoles.first()
}

object SkillMatcher {

    fun generateReport(candidate: Candidate, role: JobRole): SkillMatchReport {
        val normalizedCandidateSkills = candidate.skills
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinctBy { it.lowercase() }

        val matchedSkills = mutableListOf<String>()
        val missingSkills = mutableListOf<String>()

        role.requiredSkills.forEach { reqSkill ->
            val reqLower = reqSkill.lowercase()
            val hasMatch = normalizedCandidateSkills.any { candSkill ->
                val candLower = candSkill.lowercase()
                candLower == reqLower ||
                    candLower.contains(reqLower) ||
                    reqLower.contains(candLower)
            }
            if (hasMatch) {
                matchedSkills.add(reqSkill)
            } else {
                missingSkills.add(reqSkill)
            }
        }

        val additionalSkills = normalizedCandidateSkills.filter { candSkill ->
            val candLower = candSkill.lowercase()
            role.requiredSkills.none { reqSkill ->
                val reqLower = reqSkill.lowercase()
                candLower == reqLower ||
                    candLower.contains(reqLower) ||
                    reqLower.contains(candLower)
            }
        }

        val percentage = if (role.requiredSkills.isEmpty()) {
            0
        } else {
            ((matchedSkills.size.toDouble() / role.requiredSkills.size.toDouble()) * 100.0)
                .roundToInt()
                .coerceIn(0, 100)
        }

        val expLabel = if (candidate.yearsOfExperience == 1.0) "1.0 year" else "${candidate.yearsOfExperience} years"

        val alignmentPhrase = when {
            percentage >= 80 -> "strong sample skill overlap"
            percentage >= 50 -> "moderate sample skill overlap"
            else -> "developing sample skill overlap"
        }

        val experienceNote = if (candidate.yearsOfExperience >= role.minRecommendedYears) {
            "meets the sample experience benchmark of ${role.minRecommendedYears}+ years"
        } else {
            "is below the sample experience benchmark of ${role.minRecommendedYears}+ years"
        }

        val summary = "Sample rule-based comparison for ${candidate.name} (${candidate.qualification}, $expLabel) " +
            "against ${role.title} shows $alignmentPhrase (${matchedSkills.size} of ${role.requiredSkills.size} " +
            "required skills matched, $percentage%). Reported experience $experienceNote. " +
            "This summary is generated for demonstration only and is not a live AI decision."

        val recommendations = buildList {
            when {
                percentage >= 80 -> add(
                    "Strong skill alignment ($percentage%): Consider advancing profile for standard HR team review."
                )
                percentage >= 50 -> add(
                    "Partial skill alignment ($percentage%): Recommend an initial discovery call to clarify practical depth."
                )
                else -> add(
                    "Targeted review advised ($percentage%): Verify transferable experience and core competencies during screening."
                )
            }

            if (missingSkills.isNotEmpty()) {
                add(
                    "Suggested focus areas to verify: ${missingSkills.joinToString(", ")}."
                )
            } else {
                add(
                    "All sample role criteria were matched; focus interview prompts on project outcomes and collaboration."
                )
            }

            if (additionalSkills.isNotEmpty()) {
                add(
                    "Note complementary skills listed by candidate: ${additionalSkills.joinToString(", ")}."
                )
            }

            add("Advisory notice: Final candidate evaluation remains solely with the HR hiring team.")
        }

        return SkillMatchReport(
            candidate = candidate,
            jobRole = role,
            matchedSkills = matchedSkills,
            missingSkills = missingSkills,
            additionalSkills = additionalSkills,
            matchPercentage = percentage,
            screeningSummary = summary,
            recommendations = recommendations
        )
    }
}
