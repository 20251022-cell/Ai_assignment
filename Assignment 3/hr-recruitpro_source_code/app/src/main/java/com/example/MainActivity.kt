package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.rounded.Assessment
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.CandidateScreeningScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.ReportsScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.HRRecruitProTheme
import com.example.ui.theme.LightBlueContainer
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SubtleOutline
import com.example.ui.theme.TextSlateSecondary
import com.example.viewmodel.RecruitmentViewModel

object Routes {
    const val WELCOME = "welcome"
    const val DASHBOARD = "dashboard"
    const val SCREENING = "screening"
    const val REPORTS = "reports"
}

private data class BottomNavDestination(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val mainDestinations = listOf(
    BottomNavDestination(
        route = Routes.DASHBOARD,
        label = "Dashboard",
        selectedIcon = Icons.Rounded.Dashboard,
        unselectedIcon = Icons.Outlined.Dashboard,
        testTag = "nav_dashboard"
    ),
    BottomNavDestination(
        route = Routes.SCREENING,
        label = "Screening",
        selectedIcon = Icons.Rounded.PersonAdd,
        unselectedIcon = Icons.Outlined.PersonAdd,
        testTag = "nav_screening"
    ),
    BottomNavDestination(
        route = Routes.REPORTS,
        label = "Reports",
        selectedIcon = Icons.Rounded.Assessment,
        unselectedIcon = Icons.Outlined.Assessment,
        testTag = "nav_reports"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HRRecruitProTheme {
                HRRecruitProApp()
            }
        }
    }
}

@Composable
fun HRRecruitProApp(
    viewModel: RecruitmentViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showMainChrome = currentRoute != null && currentRoute != Routes.WELCOME

    fun navigateToTab(route: String) {
        navController.navigate(route) {
            popUpTo(Routes.DASHBOARD) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateBackToDashboard() {
        val popped = navController.popBackStack(Routes.DASHBOARD, inclusive = false)
        if (!popped) {
            navigateToTab(Routes.DASHBOARD)
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useRail = maxWidth >= 600.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = PureWhite,
            contentWindowInsets = if (showMainChrome) WindowInsets.safeDrawing else WindowInsets(0),
            bottomBar = {
                if (showMainChrome && !useRail) {
                    HRBottomNavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = ::navigateToTab
                    )
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (showMainChrome && useRail) {
                    HRNavigationRail(
                        currentRoute = currentRoute,
                        onNavigate = ::navigateToTab
                    )
                    VerticalDivider(color = SubtleOutline)
                }

                HRNavHost(
                    navController = navController,
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateToTab = ::navigateToTab,
                    onNavigateBackToDashboard = ::navigateBackToDashboard,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun HRNavHost(
    navController: NavHostController,
    viewModel: RecruitmentViewModel,
    uiState: com.example.viewmodel.RecruitmentUiState,
    onNavigateToTab: (String) -> Unit,
    onNavigateBackToDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME,
        modifier = modifier
    ) {
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onGetStarted = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                uiState = uiState,
                onAddCandidateClick = {
                    viewModel.onScreeningTabChanged(0)
                    onNavigateToTab(Routes.SCREENING)
                },
                onViewReportsClick = {
                    onNavigateToTab(Routes.REPORTS)
                },
                onCandidateReportClick = { candidateId ->
                    viewModel.selectCandidateForReport(candidateId)
                    onNavigateToTab(Routes.REPORTS)
                },
                onSeeAllCandidatesClick = {
                    viewModel.onScreeningTabChanged(1)
                    onNavigateToTab(Routes.SCREENING)
                },
                getMatchPercentage = { candidate ->
                    viewModel.reportFor(candidate).matchPercentage
                }
            )
        }

        composable(Routes.SCREENING) {
            CandidateScreeningScreen(
                uiState = uiState,
                onNameChanged = viewModel::onNameChanged,
                onEmailChanged = viewModel::onEmailChanged,
                onQualificationChanged = viewModel::onQualificationChanged,
                onSkillsChanged = viewModel::onSkillsChanged,
                onQuickSkillAdded = viewModel::onQuickSkillAdded,
                onExperienceChanged = viewModel::onExperienceChanged,
                onJobRoleSelected = viewModel::onJobRoleSelected,
                onScreeningTabChanged = viewModel::onScreeningTabChanged,
                onSubmitCandidate = viewModel::submitCandidate,
                onOpenCandidateDetail = viewModel::openCandidateDetail,
                onDismissCandidateDetail = viewModel::dismissCandidateDetail,
                onViewCandidateReport = { candidateId ->
                    viewModel.selectCandidateForReport(candidateId)
                    onNavigateToTab(Routes.REPORTS)
                },
                onClearSubmissionFeedback = viewModel::clearSubmissionFeedback,
                onBackToDashboard = onNavigateBackToDashboard,
                getMatchPercentage = { candidate ->
                    viewModel.reportFor(candidate).matchPercentage
                }
            )
        }

        composable(Routes.REPORTS) {
            ReportsScreen(
                uiState = uiState,
                onSelectCandidate = viewModel::selectCandidateForReport,
                onAddAnotherCandidate = {
                    viewModel.onScreeningTabChanged(0)
                    onNavigateToTab(Routes.SCREENING)
                },
                onBackToDashboard = onNavigateBackToDashboard
            )
        }
    }
}

@Composable
private fun HRBottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier.background(PureWhite)
    ) {
        HorizontalDivider(color = SubtleOutline)
        NavigationBar(
            containerColor = PureWhite,
            tonalElevation = 0.dp
        ) {
            mainDestinations.forEach { dest ->
                val selected = currentRoute == dest.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigate(dest.route) },
                    icon = {
                        Icon(
                            imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                            contentDescription = dest.label
                        )
                    },
                    label = {
                        Text(
                            text = dest.label,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = NavyPrimary,
                        selectedTextColor = NavyPrimary,
                        indicatorColor = LightBlueContainer,
                        unselectedIconColor = TextSlateSecondary,
                        unselectedTextColor = TextSlateSecondary
                    ),
                    modifier = Modifier.testTag(dest.testTag)
                )
            }
        }
    }
}

@Composable
private fun HRNavigationRail(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationRail(
        containerColor = PureWhite,
        modifier = Modifier
            .fillMaxHeight()
            .width(84.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        mainDestinations.forEach { dest ->
            val selected = currentRoute == dest.route
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigate(dest.route) },
                icon = {
                    Icon(
                        imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                        contentDescription = dest.label
                    )
                },
                label = {
                    Text(
                        text = dest.label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = NavyPrimary,
                    selectedTextColor = NavyPrimary,
                    indicatorColor = LightBlueContainer,
                    unselectedIconColor = AccentBlue.copy(alpha = 0.65f),
                    unselectedTextColor = TextSlateSecondary
                ),
                modifier = Modifier.testTag(dest.testTag)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
