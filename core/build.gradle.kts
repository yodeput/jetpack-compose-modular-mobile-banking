import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies

plugins {
    id("commons.android-library")
}

android {
    defaultConfig {
        buildConfigField("String", "API_BASE_URL", "\"https://green-thumb-64168.uc.r.appspot.com/\"")
        buildConfigField("String", "DATABASE_NAME", "\"yodeput-bank\"")
        buildConfigField("int", "DATABASE_VERSION", "1")
        buildConfigField("boolean", "DATABASE_EXPORT_SCHEMA", "false")
    }

    flavorDimensions += "environment"
    productFlavors {
        create("production") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_URL", "\"https://green-thumb-64168.uc.r.appspot.com/\"")
        }
        create("staging") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_URL", "\"https://green-thumb-64168.uc.r.appspot.com/\"")
        }
        create("dev") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_URL", "\"https://green-thumb-64168.uc.r.appspot.com/\"")
        }
    }
}

dependencies {
    api(project(BuildModules.COMMON))

    api(Dependencies.RETROFIT)
    api(Dependencies.MOSHI)
    implementation(Dependencies.ROOM_KTX)
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.RETROFIT_GSON)
    implementation(Dependencies.RETROFIT_MOSHI)
    implementation(Dependencies.LOGGING)
    implementation(Dependencies.MOSHI_KTX)
    implementation(Dependencies.COROUTINES)
    implementation(Dependencies.SKYDOVES)
    implementation(Dependencies.CHUCKER)

    kapt(AnnotationProcessorsDependencies.HILT)
    kapt(AnnotationProcessorsDependencies.ROOM)
}