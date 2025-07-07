plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.recipes.convention.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.recipes.convention.publish)
    alias(libs.plugins.recipes.convention.multiplatform)
}

kotlin {

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        androidInstrumentedTest.dependencies {
            implementation(libs.androidx.junit)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(projects.core.common)
            implementation(projects.core.designsystem)
            implementation(projects.core.notification)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
