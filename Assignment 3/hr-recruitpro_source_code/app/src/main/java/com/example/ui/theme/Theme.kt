package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val HRRecruitProColorScheme = lightColorScheme(
    primary = NavyPrimary,
    onPrimary = PureWhite,
    primaryContainer = LightBlueContainer,
    onPrimaryContainer = NavyDark,
    secondary = AccentBlue,
    onSecondary = PureWhite,
    secondaryContainer = SoftBlueTint,
    onSecondaryContainer = NavyMedium,
    tertiary = SkyBlue,
    onTertiary = PureWhite,
    background = PureWhite,
    onBackground = TextNavyPrimary,
    surface = PureWhite,
    onSurface = TextNavyPrimary,
    surfaceVariant = SoftOffWhite,
    onSurfaceVariant = TextSlateSecondary,
    surfaceContainerLowest = PureWhite,
    surfaceContainerLow = SoftOffWhite,
    surfaceContainer = SlateSurface,
    outline = SubtleOutline,
    outlineVariant = IceBlueBorder,
    error = CoralGapText,
    onError = PureWhite,
    errorContainer = CoralGapBg,
    onErrorContainer = CoralGapText
)

val HRRecruitProShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun HRRecruitProTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HRRecruitProColorScheme,
        typography = HRRecruitProTypography,
        shapes = HRRecruitProShapes,
        content = content
    )
}
