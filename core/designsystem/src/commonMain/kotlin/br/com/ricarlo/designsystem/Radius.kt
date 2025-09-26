package br.com.ricarlo.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Radius(
    val tiny: Dp = RadiusDefaults.Tiny,
    val extraSmall: Dp = RadiusDefaults.ExtraSmall,
    val small: Dp = RadiusDefaults.Small,
    val medium: Dp = RadiusDefaults.Medium,
    val large: Dp = RadiusDefaults.Large,
    val extraLarge: Dp = RadiusDefaults.ExtraLarge
)

internal data object RadiusDefaults {
    val Tiny = 2.dp

    val ExtraSmall = 4.dp

    val Small = 8.dp

    val Medium = 16.dp

    val Large = 24.dp

    val ExtraLarge = 32.dp
}

val LocalRadius = compositionLocalOf { Radius() }

val Theme.radius: Radius
    @Composable
    @ReadOnlyComposable
    get() = LocalRadius.current
