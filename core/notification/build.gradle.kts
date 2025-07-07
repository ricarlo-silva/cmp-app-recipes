
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.recipes.convention.publish)
    alias(libs.plugins.recipes.convention.multiplatform)
}

kotlin {

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.messaging.ktx)
            implementation(libs.firebase.analytics.ktx)
        }
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.network)
            api(libs.permissions.compose)
            api(libs.permissions.notifications)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "br.com.ricarlo.notification"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
