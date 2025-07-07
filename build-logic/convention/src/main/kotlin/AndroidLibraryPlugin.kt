import com.android.build.gradle.LibraryExtension
import com.ricarlo.recipes.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        with(pluginManager) {
            apply(libs.findPlugin("androidLibrary").get().get().pluginId)
        }
        extensions.configure<LibraryExtension> {
            namespace = "br.com.ricarlo.$name"
            compileSdk = libs.findVersion("compileSdk").get().toString().toInt()
            defaultConfig {
                minSdk = libs.findVersion("minSdk").get().toString().toInt()
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
        }
    }
}
