package br.com.ricarlo.analysis.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression

class NoDisposableEffectWithoutKey(config: Config) : Rule(config) {

    override val issue = Issue(
        id = "NoDisposableEffectWithoutKey",
        severity = Severity.Defect,
        description = "DisposableEffect deve sempre ter pelo menos uma key para evitar side-effects indesejados.",
        Debt.FIVE_MINS
    )

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)

        if (expression.calleeExpression?.text == "DisposableEffect") {
//            val arguments = expression.valueArguments
//            if (arguments.isEmpty()) {
                report(
                    CodeSmell(
                        issue,
                        Entity.from(expression),
                        "Use DisposableEffect(Unit) ou com keys explícitas: DisposableEffect(state, ...) { ... }"
                    )
                )
//            }
        }
    }
}
