import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.ricarlo.recipes.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType

class AnalysisConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("detekt").get().get().pluginId)
            apply(libs.findPlugin("spotless").get().get().pluginId)
        }
        // Lint
        extensions.findByType(ApplicationExtension::class.java)?.apply {
            lint {
                baseline = file("lint-baseline.xml")
            }
        }

        extensions.findByType(LibraryExtension::class.java)?.apply {
            lint {
                baseline = file("lint-baseline.xml")
            }
        }

        // Detekt
//        dependencies {
//            add("detektPlugins", "io.nlopez.compose.rules:detekt:0.4.27")
//            add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
////                add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-rules-ktlint-wrapper:1.23.8")
//            add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-rules-libraries:1.23.8")
//            add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-rules-ruleauthors:1.23.8")
//        }
        configureDetekt()
        configureSpotless()
    }
}

internal fun Project.configureDetekt() = configure<DetektExtension> {

    toolVersion = libs.findVersion("detekt").get().requiredVersion
    config.setFrom(file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    baseline = file("detekt-baseline.xml")
    parallel = true

    tasks.withType<Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
    tasks.withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
    tasks.named<Detekt>("detekt") {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
}

internal fun Project.configureSpotless() = configure<SpotlessExtension> {
    // --- Kotlin source formatting ---
    kotlin {
        target("**/*.kt")
        targetExclude("${layout.buildDirectory}/**/*.kt") // Exclude files in the build directory
        ktlint(libs.findVersion("ktlint").get().requiredVersion)
            .setEditorConfigPath(rootProject.file(".editorconfig").path)
            .customRuleSets(
                listOf(
                    "io.nlopez.compose.rules:ktlint:0.4.27"
                )
            )
        toggleOffOn() // Allow toggling Spotless off and on within code files using comments
//            ktfmt(libs.findVersion("ktfmt").get().requiredVersion)
//                .googleStyle()
//                .kotlinlangStyle()
    }

    // --- Kotlin Gradle scripts (build.gradle.kts) ---
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint(libs.findVersion("ktlint").get().requiredVersion)
    }

    // --- XML (Android, resources, manifests, etc.) ---
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**")
    }
}
