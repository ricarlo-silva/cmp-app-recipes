package br.com.ricarlo.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Stroke(
    val tiny: Dp = StrokeDefaults.Tiny,
    val extraSmall: Dp = StrokeDefaults.ExtraSmall,
    val small: Dp = StrokeDefaults.Small,
    val medium: Dp = StrokeDefaults.Medium,
    val large: Dp = StrokeDefaults.Large,
    val extraLarge: Dp = StrokeDefaults.ExtraLarge,
)

internal data object StrokeDefaults {
    val Tiny = 0.5.dp

    val ExtraSmall = 1.dp

    val Small = 2.dp

    val Medium = 4.dp

    val Large = 6.dp

    val ExtraLarge = 8.dp
}

val LocalStroke = compositionLocalOf { Stroke() }

val Theme.stroke: Stroke
    @Composable
    @ReadOnlyComposable
    get() = LocalStroke.current
