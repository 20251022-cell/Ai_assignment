package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.CandidateStatus
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.AmberReviewBg
import com.example.ui.theme.AmberReviewText
import com.example.ui.theme.EmeraldMatchBg
import com.example.ui.theme.EmeraldMatchText
import com.example.ui.theme.IceBlueBorder
import com.example.ui.theme.LightBlueContainer
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateSurface
import com.example.ui.theme.SoftBlueTint
import com.example.ui.theme.SubtleOutline
import com.example.ui.theme.TealInterviewBg
import com.example.ui.theme.TealInterviewText
import com.example.ui.theme.TextNavyPrimary
import com.example.ui.theme.TextSlateSecondary

@Composable
fun SampleDataBanner(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        color = SoftBlueTint,
        border = BorderStroke(1.dp, IceBlueBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Sample data information",
                tint = AccentBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = NavyPrimary
            )
        }
    }
}

@Composable
fun StatusBadge(
    status: CandidateStatus,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, icon) = when (status) {
        CandidateStatus.SHORTLISTED -> Triple(EmeraldMatchBg, EmeraldMatchText, Icons.Rounded.Star)
        CandidateStatus.PENDING_REVIEW -> Triple(AmberReviewBg, AmberReviewText, Icons.Rounded.Schedule)
        CandidateStatus.INTERVIEW_SCHEDULED -> Triple(TealInterviewBg, TealInterviewText, Icons.Rounded.CheckCircle)
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = bgColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(13.dp)
            )
            Text(
                text = status.label,
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SkillTagChip(
    label: String,
    highlighted: Boolean = false,
    missing: Boolean = false,
    leadingIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    val bgColor = when {
        highlighted -> LightBlueContainer
        missing -> MaterialTheme.colorScheme.errorContainer
        else -> SlateSurface
    }
    val contentColor = when {
        highlighted -> NavyPrimary
        missing -> MaterialTheme.colorScheme.error
        else -> TextSlateSecondary
    }
    val borderColor = when {
        highlighted -> IceBlueBorder
        missing -> MaterialTheme.colorScheme.error.copy(alpha = 0.25f)
        else -> SubtleOutline
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = bgColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(14.dp)
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CandidateInitialAvatar(
    name: String,
    size: Dp = 44.dp,
    containerColor: Color = LightBlueContainer,
    contentColor: Color = NavyPrimary
) {
    val initials = name
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
        .ifEmpty { "HR" }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(containerColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = contentColor
        )
    }
}

@Composable
fun MatchProgressRing(
    percentage: Int,
    size: Dp = 112.dp,
    strokeWidth: Dp = 10.dp,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = (percentage.coerceIn(0, 100) / 100f),
        animationSpec = tween(durationMillis = 650),
        label = "match_ring_progress"
    )

    val ringColor = when {
        percentage >= 80 -> AccentBlue
        percentage >= 50 -> NavyPrimary
        else -> AmberReviewText
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokePx = strokeWidth.toPx()
            drawArc(
                color = SlateSurface,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
            drawArc(
                color = ringColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = TextNavyPrimary
            )
            Text(
                text = "Match",
                style = MaterialTheme.typography.labelSmall,
                color = TextSlateSecondary
            )
        }
    }
}
