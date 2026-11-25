package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material.icons.rounded.Rule
import androidx.compose.material.icons.rounded.Summarize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.components.CandidateInitialAvatar
import com.example.ui.components.MatchProgressRing
import com.example.ui.components.SampleDataBanner
import com.example.ui.components.SkillTagChip
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.IceBlueBorder
import com.example.ui.theme.LightBlueContainer
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlueTint
import com.example.ui.theme.SoftOffWhite
import com.example.ui.theme.SubtleOutline
import com.example.ui.theme.TextNavyPrimary
import com.example.ui.theme.TextSlateSecondary
import com.example.viewmodel.RecruitmentUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReportsScreen(
    uiState: RecruitmentUiState,
    onSelectCandidate: (String) -> Unit,
    onAddAnotherCandidate: () -> Unit,
    onBackToDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val report = uiState.selectedReport
    val candidate = report.candidate
    val role = report.jobRole

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SoftOffWhite),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 640.dp)
        ) {
            // Top Bar
            Surface(
                color = PureWhite,
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBackToDashboard,
                            modifier = Modifier.testTag("reports_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back to Dashboard",
                                tint = NavyPrimary
                            )
                        }
                        Column {
                            Text(
                                text = stringResource(id = R.string.reports_title),
                                style = MaterialTheme.typography.titleLarge,
                                color = TextNavyPrimary
                            )
                            Text(
                                text = "Sample Rule-Based Matching Report",
                                style = MaterialTheme.typography.bodySmall,
                                color = AccentBlue
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SoftBlueTint,
                        border = BorderStroke(1.dp, IceBlueBorder),
                        modifier = Modifier
                            .clickable(onClick = onBackToDashboard)
                            .testTag("reports_top_dashboard_chip")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Dashboard,
                                contentDescription = null,
                                tint = NavyPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Dashboard",
                                style = MaterialTheme.typography.labelMedium,
                                color = NavyPrimary
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Rule-based Demo Disclaimer Banner
                item {
                    SampleDataBanner(
                        text = stringResource(id = R.string.rule_based_disclaimer)
                    )
                }

                // Candidate Selector Strip
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Select Candidate Profile",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextNavyPrimary
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.candidates.forEach { itemCandidate ->
                                val isSelected = itemCandidate.id == candidate.id
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = if (isSelected) NavyPrimary else PureWhite,
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isSelected) NavyPrimary else SubtleOutline
                                    ),
                                    modifier = Modifier
                                        .clickable { onSelectCandidate(itemCandidate.id) }
                                        .testTag("report_candidate_chip_${itemCandidate.id}")
                                ) {
                                    Text(
                                        text = itemCandidate.name,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = if (isSelected) PureWhite else TextNavyPrimary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Candidate Overview + Match Percentage Ring Card
                item {
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = PureWhite,
                        border = BorderStroke(1.dp, IceBlueBorder),
                        shadowElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    CandidateInitialAvatar(
                                        name = candidate.name,
                                        size = 48.dp
                                    )
                                    Column {
                                        Text(
                                            text = candidate.name,
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = TextNavyPrimary,
                                            modifier = Modifier.testTag("report_candidate_name")
                                        )
                                        Text(
                                            text = "Applied Role: ${role.title}",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = AccentBlue,
                                            modifier = Modifier.testTag("report_job_role")
                                        )
                                        Text(
                                            text = "${candidate.qualification} • ${candidate.yearsOfExperience} yrs exp",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextSlateSecondary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = SubtleOutline)
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(18.dp)
                            ) {
                                MatchProgressRing(
                                    percentage = report.matchPercentage,
                                    modifier = Modifier.testTag("report_match_percentage")
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = SoftBlueTint,
                                        border = BorderStroke(1.dp, IceBlueBorder)
                                    ) {
                                        Text(
                                            text = "Sample Rule-Based Match",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = NavyPrimary,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "${report.matchedSkills.size} of ${role.requiredSkills.size} role skills matched",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextNavyPrimary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    StatusBadge(status = candidate.status)
                                }
                            }
                        }
                    }
                }

                // Candidate Skills Card
                item {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = PureWhite,
                        border = BorderStroke(1.dp, SubtleOutline),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Candidate Skills",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextNavyPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Blue chips indicate skills matching sample ${role.title} requirements.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSlateSecondary
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                candidate.skills.forEach { candSkill ->
                                    val isMatched = role.requiredSkills.any { req ->
                                        candSkill.equals(req, ignoreCase = true) ||
                                            candSkill.lowercase().contains(req.lowercase()) ||
                                            req.lowercase().contains(candSkill.lowercase())
                                    }
                                    SkillTagChip(
                                        label = candSkill,
                                        highlighted = isMatched,
                                        leadingIcon = if (isMatched) Icons.Rounded.CheckCircle else null
                                    )
                                }
                            }
                        }
                    }
                }

                // Relevant Skills Matching Result Card
                item {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = PureWhite,
                        border = BorderStroke(1.dp, SubtleOutline),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Rule,
                                    contentDescription = null,
                                    tint = AccentBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Relevant Skills Matching Result",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextNavyPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Matched Role Skills (${report.matchedSkills.size})",
                                style = MaterialTheme.typography.labelLarge,
                                color = NavyPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (report.matchedSkills.isEmpty()) {
                                Text(
                                    text = "No direct keyword matches found against sample role skills.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSlateSecondary
                                )
                            } else {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    report.matchedSkills.forEach { skill ->
                                        SkillTagChip(
                                            label = skill,
                                            highlighted = true,
                                            leadingIcon = Icons.Rounded.CheckCircle
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Skills to Verify / Not Listed (${report.missingSkills.size})",
                                style = MaterialTheme.typography.labelLarge,
                                color = TextNavyPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (report.missingSkills.isEmpty()) {
                                Surface(
                                    shape = MaterialTheme.shapes.small,
                                    color = SoftBlueTint,
                                    border = BorderStroke(1.dp, IceBlueBorder),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "All sample role skills were matched in this profile.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = NavyPrimary,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            } else {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    report.missingSkills.forEach { skill ->
                                        SkillTagChip(
                                            label = skill,
                                            missing = true,
                                            leadingIcon = Icons.Rounded.RadioButtonUnchecked
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Screening Summary Card
                item {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = PureWhite,
                        border = BorderStroke(1.dp, SubtleOutline),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Summarize,
                                    contentDescription = null,
                                    tint = NavyPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Screening Summary (Sample Demo)",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextNavyPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = report.screeningSummary,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSlateSecondary,
                                modifier = Modifier.testTag("report_screening_summary")
                            )
                        }
                    }
                }

                // Recruitment Recommendations Card
                item {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = SoftBlueTint,
                        border = BorderStroke(1.dp, IceBlueBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Lightbulb,
                                    contentDescription = null,
                                    tint = AccentBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Recruitment Recommendations",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = NavyPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Advisory guidance for HR teams — demonstration only.",
                                style = MaterialTheme.typography.labelSmall,
                                color = AccentBlue
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                report.recommendations.forEach { rec ->
                                    Row(
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "•",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = AccentBlue
                                        )
                                        Text(
                                            text = rec,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextNavyPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Action Buttons to Screen Another Candidate or Return to Dashboard
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onAddAnotherCandidate,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("reports_add_candidate_button"),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.5.dp, AccentBlue),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = PureWhite,
                                contentColor = AccentBlue
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PersonAdd,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Screen Candidate")
                        }

                        Button(
                            onClick = onBackToDashboard,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("reports_back_to_dashboard_button"),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NavyPrimary,
                                contentColor = PureWhite
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Dashboard,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Dashboard")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
