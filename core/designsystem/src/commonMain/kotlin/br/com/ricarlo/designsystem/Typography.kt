package br.com.ricarlo.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import br.com.ricarlo.designsystem.generated.resources.OpenSans_Bold
import br.com.ricarlo.designsystem.generated.resources.OpenSans_Light
import br.com.ricarlo.designsystem.generated.resources.OpenSans_Medium
import br.com.ricarlo.designsystem.generated.resources.OpenSans_Regular
import br.com.ricarlo.designsystem.generated.resources.OpenSans_SemiBold
import br.com.ricarlo.designsystem.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
internal fun AppTypography() = Typography().run {
    val fontFamily = FontFamily(
        Font(Res.font.OpenSans_Bold, FontWeight.Bold),
        Font(Res.font.OpenSans_Light, FontWeight.Light),
        Font(Res.font.OpenSans_Medium, FontWeight.Medium),
        Font(Res.font.OpenSans_Regular, FontWeight.Normal),
        Font(Res.font.OpenSans_SemiBold, FontWeight.SemiBold)
    )

    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}
