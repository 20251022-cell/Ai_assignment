package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Assessment
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.WorkHistory
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.Candidate
import com.example.model.SampleDataCatalog
import com.example.ui.components.CandidateInitialAvatar
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CandidateScreeningScreen(
    uiState: RecruitmentUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onQualificationChanged: (String) -> Unit,
    onSkillsChanged: (String) -> Unit,
    onQuickSkillAdded: (String) -> Unit,
    onExperienceChanged: (String) -> Unit,
    onJobRoleSelected: (String) -> Unit,
    onScreeningTabChanged: (Int) -> Unit,
    onSubmitCandidate: () -> Boolean,
    onOpenCandidateDetail: (Candidate) -> Unit,
    onDismissCandidateDetail: () -> Unit,
    onViewCandidateReport: (String) -> Unit,
    onClearSubmissionFeedback: () -> Unit,
    onBackToDashboard: () -> Unit,
    getMatchPercentage: (Candidate) -> Int,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.submissionFeedback) {
        val message = uiState.submissionFeedback
        if (message != null) {
            snackbarHostState.showSnackbar(message)
            onClearSubmissionFeedback()
        }
    }

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
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = onBackToDashboard,
                                modifier = Modifier.testTag("screening_back_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = "Return to Dashboard",
                                    tint = NavyPrimary
                                )
                            }
                            Column {
                                Text(
                                    text = stringResource(id = R.string.screening_title),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = TextNavyPrimary
                                )
                                Text(
                                    text = "Enter profiles & review local candidate list",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSlateSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SegmentedButton(
                            selected = uiState.screeningTab == 0,
                            onClick = { onScreeningTabChanged(0) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = NavyPrimary,
                                activeContentColor = PureWhite,
                                inactiveContainerColor = SoftOffWhite,
                                inactiveContentColor = TextNavyPrimary
                            ),
                            modifier = Modifier.testTag("screening_tab_form")
                        ) {
                            Text("Add Candidate")
                        }
                        SegmentedButton(
                            selected = uiState.screeningTab == 1,
                            onClick = { onScreeningTabChanged(1) },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = NavyPrimary,
                                activeContentColor = PureWhite,
                                inactiveContainerColor = SoftOffWhite,
                                inactiveContentColor = TextNavyPrimary
                            ),
                            modifier = Modifier.testTag("screening_tab_list")
                        ) {
                            Text("Candidate List (${uiState.candidates.size})")
                        }
                    }
                }
            }

            if (uiState.screeningTab == 0) {
                CandidateEntryFormContent(
                    uiState = uiState,
                    onNameChanged = onNameChanged,
                    onEmailChanged = onEmailChanged,
                    onQualificationChanged = onQualificationChanged,
                    onSkillsChanged = onSkillsChanged,
                    onQuickSkillAdded = onQuickSkillAdded,
                    onExperienceChanged = onExperienceChanged,
                    onJobRoleSelected = onJobRoleSelected,
                    onSubmitCandidate = onSubmitCandidate,
                    onBackToDashboard = onBackToDashboard
                )
            } else {
                CandidateListContent(
                    candidates = uiState.candidates,
                    onCandidateClick = onOpenCandidateDetail,
                    onViewReportClick = onViewCandidateReport,
                    getMatchPercentage = getMatchPercentage
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

    val detailCandidate = uiState.detailCandidate
    if (detailCandidate != null) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val role = SampleDataCatalog.roleForId(detailCandidate.jobRoleId)
        val matchPct = getMatchPercentage(detailCandidate)

        ModalBottomSheet(
            onDismissRequest = onDismissCandidateDetail,
            sheetState = sheetState,
            containerColor = PureWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 28.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        CandidateInitialAvatar(name = detailCandidate.name, size = 50.dp)
                        Column {
                            Text(
                                text = detailCandidate.name,
                                style = MaterialTheme.typography.headlineSmall,
                                color = TextNavyPrimary
                            )
                            Text(
                                text = role.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = AccentBlue
                            )
                        }
                    }
                    IconButton(onClick = onDismissCandidateDetail) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close candidate details",
                            tint = TextSlateSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatusBadge(status = detailCandidate.status)
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = SoftBlueTint,
                        border = BorderStroke(1.dp, IceBlueBorder)
                    ) {
                        Text(
                            text = "$matchPct% Sample Skill Match",
                            style = MaterialTheme.typography.labelSmall,
                            color = NavyPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = SubtleOutline)
                Spacer(modifier = Modifier.height(16.dp))

                DetailInfoRow(label = "Email Address", value = detailCandidate.email)
                Spacer(modifier = Modifier.height(10.dp))
                DetailInfoRow(label = "Qualification", value = detailCandidate.qualification)
                Spacer(modifier = Modifier.height(10.dp))
                DetailInfoRow(
                    label = "Experience",
                    value = "${detailCandidate.yearsOfExperience} years"
                )
                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Submitted Skills",
                    style = MaterialTheme.typography.titleSmall,
                    color = TextNavyPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    detailCandidate.skills.forEach { skill ->
                        SkillTagChip(label = skill, highlighted = true)
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = {
                        onDismissCandidateDetail()
                        onViewCandidateReport(detailCandidate.id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("detail_view_report_button"),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyPrimary,
                        contentColor = PureWhite
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Assessment,
                        contentDescription = null,
                        modifier = Modifier.size(19.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Screening Report")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun CandidateEntryFormContent(
    uiState: RecruitmentUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onQualificationChanged: (String) -> Unit,
    onSkillsChanged: (String) -> Unit,
    onQuickSkillAdded: (String) -> Unit,
    onExperienceChanged: (String) -> Unit,
    onJobRoleSelected: (String) -> Unit,
    onSubmitCandidate: () -> Boolean,
    onBackToDashboard: () -> Unit
) {
    val form = uiState.formState
    val selectedRole = SampleDataCatalog.roleForId(form.jobRoleId)
    var roleMenuExpanded by remember { mutableStateOf(false) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = AccentBlue,
        unfocusedBorderColor = SubtleOutline,
        focusedContainerColor = PureWhite,
        unfocusedContainerColor = PureWhite,
        focusedLabelColor = NavyPrimary,
        cursorColor = AccentBlue
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = PureWhite,
            border = BorderStroke(1.dp, SubtleOutline),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "New Candidate Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextNavyPrimary
                )
                Text(
                    text = "All fields are required. Profiles are stored locally for demonstration.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSlateSecondary
                )

                // Candidate Name
                OutlinedTextField(
                    value = form.name,
                    onValueChange = onNameChanged,
                    label = { Text("Candidate Name *") },
                    placeholder = { Text("e.g. Neha Sharma") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            tint = NavyPrimary
                        )
                    },
                    isError = form.nameError != null,
                    supportingText = form.nameError?.let { err -> { Text(err) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    colors = fieldColors,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_candidate_name")
                )

                // Email Address
                OutlinedTextField(
                    value = form.email,
                    onValueChange = onEmailChanged,
                    label = { Text("Email Address *") },
                    placeholder = { Text("e.g. neha.sharma@example.com") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            contentDescription = null,
                            tint = NavyPrimary
                        )
                    },
                    isError = form.emailError != null,
                    supportingText = form.emailError?.let { err -> { Text(err) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    colors = fieldColors,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_email")
                )

                // Qualification
                OutlinedTextField(
                    value = form.qualification,
                    onValueChange = onQualificationChanged,
                    label = { Text("Qualification *") },
                    placeholder = { Text("e.g. B.Tech Computer Science / MBA HR") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.School,
                            contentDescription = null,
                            tint = NavyPrimary
                        )
                    },
                    isError = form.qualificationError != null,
                    supportingText = form.qualificationError?.let { err -> { Text(err) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    colors = fieldColors,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_qualification")
                )

                // Years of Experience
                OutlinedTextField(
                    value = form.yearsOfExperience,
                    onValueChange = onExperienceChanged,
                    label = { Text("Years of Experience *") },
                    placeholder = { Text("e.g. 2.5") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.WorkHistory,
                            contentDescription = null,
                            tint = NavyPrimary
                        )
                    },
                    isError = form.experienceError != null,
                    supportingText = form.experienceError?.let { err -> { Text(err) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    colors = fieldColors,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_experience")
                )

                // Job Role Applied For Dropdown
                ExposedDropdownMenuBox(
                    expanded = roleMenuExpanded,
                    onExpandedChange = { roleMenuExpanded = !roleMenuExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedRole.title,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Job Role Applied For *") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Badge,
                                contentDescription = null,
                                tint = NavyPrimary
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = roleMenuExpanded)
                        },
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .testTag("input_job_role")
                    )

                    ExposedDropdownMenu(
                        expanded = roleMenuExpanded,
                        onDismissRequest = { roleMenuExpanded = false },
                        containerColor = PureWhite
                    ) {
                        uiState.jobRoles.forEach { role ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = role.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = TextNavyPrimary
                                        )
                                        Text(
                                            text = role.department,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextSlateSecondary
                                        )
                                    }
                                },
                                onClick = {
                                    onJobRoleSelected(role.id)
                                    roleMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // Sample Role Skills Reference
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = SoftBlueTint,
                    border = BorderStroke(1.dp, IceBlueBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Sample Skills for ${selectedRole.title} (tap to add):",
                            style = MaterialTheme.typography.labelMedium,
                            color = NavyPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            selectedRole.requiredSkills.forEach { skill ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = PureWhite,
                                    border = BorderStroke(1.dp, IceBlueBorder),
                                    modifier = Modifier.clickable { onQuickSkillAdded(skill) }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Add,
                                            contentDescription = "Add $skill",
                                            tint = AccentBlue,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = skill,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = NavyPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Skills Text Field
                OutlinedTextField(
                    value = form.skills,
                    onValueChange = onSkillsChanged,
                    label = { Text("Candidate Skills *") },
                    placeholder = { Text("Comma-separated, e.g. Kotlin, Compose, Git") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Code,
                            contentDescription = null,
                            tint = NavyPrimary
                        )
                    },
                    isError = form.skillsError != null,
                    supportingText = {
                        Text(
                            form.skillsError
                                ?: "Separate multiple skills with commas for rule-based matching."
                        )
                    },
                    minLines = 2,
                    maxLines = 3,
                    colors = fieldColors,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_skills")
                )

                // Submit Candidate Button
                Button(
                    onClick = { onSubmitCandidate() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("submit_candidate_button"),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyPrimary,
                        contentColor = PureWhite
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.submit_candidate),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        OutlinedButton(
            onClick = onBackToDashboard,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, SubtleOutline),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = PureWhite,
                contentColor = NavyPrimary
            )
        ) {
            Text(stringResource(id = R.string.back_to_dashboard))
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun CandidateListContent(
    candidates: List<Candidate>,
    onCandidateClick: (Candidate) -> Unit,
    onViewReportClick: (String) -> Unit,
    getMatchPercentage: (Candidate) -> Int
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SampleDataBanner(
                text = "Tap a candidate card to review full profile details or open their sample screening report."
            )
        }

        items(items = candidates, key = { it.id }) { candidate ->
            val role = SampleDataCatalog.roleForId(candidate.jobRoleId)
            val matchPct = getMatchPercentage(candidate)

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = PureWhite,
                border = BorderStroke(
                    width = if (candidate.isUserAdded) 1.5.dp else 1.dp,
                    color = if (candidate.isUserAdded) AccentBlue else SubtleOutline
                ),
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCandidateClick(candidate) }
                    .testTag("candidate_card_${candidate.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                                            text = "New",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = NavyPrimary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "${role.title} • ${candidate.qualification}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSlateSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SoftBlueTint,
                            border = BorderStroke(1.dp, IceBlueBorder)
                        ) {
                            Text(
                                text = "$matchPct%",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = AccentBlue,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatusBadge(status = candidate.status)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SoftOffWhite,
                                border = BorderStroke(1.dp, SubtleOutline),
                                modifier = Modifier.clickable { onCandidateClick(candidate) }
                            ) {
                                Text(
                                    text = "Details",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = TextNavyPrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = NavyPrimary,
                                modifier = Modifier.clickable { onViewReportClick(candidate.id) }
                            ) {
                                Text(
                                    text = "Report",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = PureWhite,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSlateSecondary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = TextNavyPrimary
        )
    }
}
