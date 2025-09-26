package br.com.ricarlo.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val tiny: Dp = SpacingDefaults.Tiny,
    val extraSmall: Dp = SpacingDefaults.ExtraSmall,
    val small: Dp = SpacingDefaults.Small,
    val medium: Dp = SpacingDefaults.Medium,
    val large: Dp = SpacingDefaults.Large,
    val extraLarge: Dp = SpacingDefaults.ExtraLarge,
)

internal data object SpacingDefaults {
    val Tiny = 2.dp

    val ExtraSmall = 4.dp

    val Small = 8.dp

    val Medium = 16.dp

    val Large = 24.dp

    val ExtraLarge = 32.dp
}

val LocalSpacing = compositionLocalOf { Spacing() }

typealias Theme = MaterialTheme

val Theme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
