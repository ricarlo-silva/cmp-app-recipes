import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.internal.extensions.stdlib.capitalized

internal class MavenPublishConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("maven-publish")
            }

            version = rootProject.file("./config/VERSION_NAME").readText().trim()
            group = "br.com.ricarlo"

            extensions.getByType(PublishingExtension::class.java).apply {
                publications.withType(MavenPublication::class.java).all {
                    pom {
                        name.set("${project.name.capitalized()} - CMP library")
                        description.set("${project.name.capitalized()} - Compose Multiplatform library")
                        url.set("https://github.com/ricarlo-silva/cmp-app-recipes")

                        licenses {
                            license {
                                name.set("MIT")
                                url.set("https://opensource.org/licenses/MIT")
                            }
                        }

                        developers {
                            developer {
                                id.set("ricarlo-silva")
                                name.set("Ricarlo Silva")
                                email.set("ricarlosilv@gmail.com")
                            }
                        }
                    }
                }
                repositories {
                    maven {
                        name = "GitHubPackages"
                        url = uri("https://maven.pkg.github.com/ricarlo-silva/cmp-app-recipes")
                        credentials {
                            username = System.getenv("GITHUB_ACTOR")
                            password = System.getenv("GITHUB_TOKEN")
                        }
                    }
                }
            }
        }
    }
}
