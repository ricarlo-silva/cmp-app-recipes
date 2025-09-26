package br.com.ricarlo.analysis.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class MyRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "MyRuleSet"

    override fun instance(config: Config): RuleSet = RuleSet(ruleSetId, listOf(MyRule(config)))
}
