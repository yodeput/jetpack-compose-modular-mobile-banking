import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies
import extensions.addTestsDependencies

plugins {
    id("commons.android-library")
    id(BuildPlugins.NAVIGATION)
    id(BuildPlugins.HILT)
}

android {

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildDependenciesVersions.COMPOSE
    }

    flavorDimensions += "environment"
    productFlavors {
        create("production") {
        }
        create("staging") {
        }
        create("dev") {
        }
    }
}

dependencies {
    api(project(BuildModules.CORE))

    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.LIFECYCLE_EXTENSIONS)
    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.COIL)
    implementation(Dependencies.COMPOSE_LIVEDATA)
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_MATERIAL_ICON)
    implementation(Dependencies.COMPOSE_MATERIAL_3)
    implementation(Dependencies.COMPOSE_PREVIEW)
    implementation(Dependencies.COMPOSE_CONSTRAIN_LAYOUT)
    implementation(Dependencies.ACTIVITY_COMPOSE)
    implementation(Dependencies.SWIPE_REFRESH)
    implementation(Dependencies.PLACEHOLDER)
    kapt(AnnotationProcessorsDependencies.HILT)

    addTestsDependencies()
}
