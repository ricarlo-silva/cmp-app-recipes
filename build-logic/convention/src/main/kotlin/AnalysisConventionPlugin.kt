
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
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
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.findPlugin("detekt").get().get().pluginId)
            configureDetekt()

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

            dependencies {

            }
        }
    }
}

internal fun Project.configureDetekt() = configure<DetektExtension> {

    toolVersion = libs.findVersion("detekt").get().requiredVersion
    config.setFrom(file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true

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
            txt.required.set(true)
            sarif.required.set(true)
            md.required.set(true)
        }
    }
}
