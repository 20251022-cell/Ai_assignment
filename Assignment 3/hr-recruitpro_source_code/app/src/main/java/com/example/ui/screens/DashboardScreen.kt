package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Assessment
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.Candidate
import com.example.model.SampleDataCatalog
import com.example.ui.components.CandidateInitialAvatar
import com.example.ui.components.SampleDataBanner
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.AmberReviewBg
import com.example.ui.theme.AmberReviewText
import com.example.ui.theme.EmeraldMatchBg
import com.example.ui.theme.EmeraldMatchText
import com.example.ui.theme.IceBlueBorder
import com.example.ui.theme.LightBlueContainer
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlueTint
import com.example.ui.theme.SoftOffWhite
import com.example.ui.theme.SubtleOutline
import com.example.ui.theme.TealInterviewBg
import com.example.ui.theme.TealInterviewText
import com.example.ui.theme.TextNavyPrimary
import com.example.ui.theme.TextSlateSecondary
import com.example.viewmodel.RecruitmentUiState

@Composable
fun DashboardScreen(
    uiState: RecruitmentUiState,
    onAddCandidateClick: () -> Unit,
    onViewReportsClick: () -> Unit,
    onCandidateReportClick: (String) -> Unit,
    onSeeAllCandidatesClick: () -> Unit,
    getMatchPercentage: (Candidate) -> Int,
    modifier: Modifier = Modifier
) {
    val stats = uiState.dashboardStats

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SoftOffWhite),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 640.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Header card
                Surface(
                    shape = MaterialTheme.shapes.large,
                    color = NavyPrimary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = LightBlueContainer
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Recruitment Overview",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = PureWhite
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = PureWhite.copy(alpha = 0.16f)
                            ) {
                                Text(
                                    text = "Sample Data",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PureWhite,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Monitor pipeline metrics, add applicants, and explore rule-based skill reports.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = LightBlueContainer.copy(alpha = 0.92f)
                        )
                    }
                }
            }

            item {
                SampleDataBanner(
                    text = stringResource(id = R.string.sample_data_notice)
                )
            }

            // 2x2 KPI Statistics Grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DashboardMetricCard(
                            title = "Total Candidates",
                            value = stats.totalCandidates.toString(),
                            subtitle = "Sample pipeline",
                            icon = Icons.Rounded.Groups,
                            iconBg = LightBlueContainer,
                            iconTint = NavyPrimary,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("metric_total_candidates")
                        )
                        DashboardMetricCard(
                            title = "Shortlisted Candidates",
                            value = stats.shortlistedCandidates.toString(),
                            subtitle = "Sample strong fit",
                            icon = Icons.Rounded.Verified,
                            iconBg = EmeraldMatchBg,
                            iconTint = EmeraldMatchText,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("metric_shortlisted_candidates")
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DashboardMetricCard(
                            title = "Pending Reviews",
                            value = stats.pendingReviews.toString(),
                            subtitle = "Awaiting HR check",
                            icon = Icons.Rounded.HourglassTop,
                            iconBg = AmberReviewBg,
                            iconTint = AmberReviewText,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("metric_pending_reviews")
                        )
                        DashboardMetricCard(
                            title = "Interviews Scheduled",
                            value = stats.interviewsScheduled.toString(),
                            subtitle = "Sample calendar",
                            icon = Icons.Rounded.CalendarMonth,
                            iconBg = TealInterviewBg,
                            iconTint = TealInterviewText,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("metric_interviews_scheduled")
                        )
                    }
                }
            }

            // Primary Navigation Action Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onAddCandidateClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("dashboard_add_candidate_button"),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentBlue,
                            contentColor = PureWhite
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PersonAdd,
                            contentDescription = null,
                            modifier = Modifier.size(19.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.add_candidate),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    OutlinedButton(
                        onClick = onViewReportsClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("dashboard_view_reports_button"),
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.5.dp, NavyPrimary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = PureWhite,
                            contentColor = NavyPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Assessment,
                            contentDescription = null,
                            modifier = Modifier.size(19.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.view_reports),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            // Recent Candidates Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Recent Candidates",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextNavyPrimary
                        )
                        Text(
                            text = "Tap any profile to inspect sample skill report",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSlateSecondary
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SoftBlueTint,
                        border = BorderStroke(1.dp, IceBlueBorder),
                        modifier = Modifier
                            .clickable(onClick = onSeeAllCandidatesClick)
                            .testTag("dashboard_see_all_candidates")
                    ) {
                        Text(
                            text = "See All (${uiState.candidates.size})",
                            style = MaterialTheme.typography.labelMedium,
                            color = AccentBlue,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            items(
                items = uiState.candidates.take(4),
                key = { it.id }
            ) { candidate ->
                val role = SampleDataCatalog.roleForId(candidate.jobRoleId)
                val matchPct = getMatchPercentage(candidate)
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = PureWhite,
                    border = BorderStroke(1.dp, SubtleOutline),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCandidateReportClick(candidate.id) }
                        .testTag("dashboard_candidate_${candidate.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CandidateInitialAvatar(name = candidate.name)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = candidate.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextNavyPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (candidate.isUserAdded) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = LightBlueContainer
                                    ) {
                                        Text(
                                            text = "Added",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = NavyPrimary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "${role.title} • ${candidate.yearsOfExperience} yrs exp",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSlateSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                StatusBadge(status = candidate.status)
                                Text(
                                    text = "$matchPct% sample match",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = AccentBlue
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "Open report",
                            tint = TextSlateSecondary
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun DashboardMetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = PureWhite,
        border = BorderStroke(1.dp, SubtleOutline),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = iconBg,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = iconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = NavyPrimary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = TextNavyPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSlateSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
