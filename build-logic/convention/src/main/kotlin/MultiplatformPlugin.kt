import com.ricarlo.recipes.configureBuildConfig
import com.ricarlo.recipes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class MultiplatformPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
        }
        extensions.configure<KotlinMultiplatformExtension>(::configureBuildConfig)
        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget {
                compilations.all {
                    compileTaskProvider.configure {
                        compilerOptions {
                            jvmTarget.set(JvmTarget.JVM_21)
                        }
                    }
                }
                publishLibraryVariants("release")
            }
            iosX64()
            iosArm64()
            iosSimulatorArm64()
        }
    }
}
