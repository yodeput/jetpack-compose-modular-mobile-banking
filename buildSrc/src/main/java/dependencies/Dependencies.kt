package dependencies

/**
 * Project dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object Dependencies {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${BuildDependenciesVersions.KOTLIN}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${BuildDependenciesVersions.APPCOMPAT}"
    const val MATERIAL = "com.google.android.material:material:${BuildDependenciesVersions.MATERIAL}"
    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${BuildDependenciesVersions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${BuildDependenciesVersions.NAVIGATION}"
    const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-runtime-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    const val CORE_KTX = "androidx.core:core-ktx:${BuildDependenciesVersions.CORE_KTX}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${BuildDependenciesVersions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${BuildDependenciesVersions.ROOM}"
    const val HILT = "com.google.dagger:hilt-android:${BuildDependenciesVersions.HILT}"
    const val TIMBER = "com.jakewharton.timber:timber:${BuildDependenciesVersions.TIMBER}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${BuildDependenciesVersions.RETROFIT}"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:${BuildDependenciesVersions.RETROFIT}"
    const val RETROFIT_MOSHI = "com.squareup.retrofit2:converter-moshi:${BuildDependenciesVersions.RETROFIT}"
    const val LOGGING = "com.squareup.okhttp3:logging-interceptor:${BuildDependenciesVersions.LOGGING}"
    const val MOSHI = "com.squareup.moshi:moshi:${BuildDependenciesVersions.MOSHI}"
    const val MOSHI_KTX = "com.squareup.moshi:moshi-kotlin:${BuildDependenciesVersions.MOSHI}"
    const val COIL = "io.coil-kt:coil-compose:${BuildDependenciesVersions.COIL}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildDependenciesVersions.COROUTINES}"
    const val COMPOSE_LIVEDATA = "androidx.compose.runtime:runtime-livedata:${BuildDependenciesVersions.COMPOSE}"
    const val COMPOSE_UI = "androidx.compose.ui:ui:${BuildDependenciesVersions.COMPOSE}"
    const val COMPOSE_CONSTRAIN_LAYOUT = "androidx.constraintlayout:constraintlayout-compose:${BuildDependenciesVersions.CONSTRAIN_LAYOUT}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${BuildDependenciesVersions.COMPOSE}"
    const val COMPOSE_MATERIAL_3 = "androidx.compose.material3:material3:${BuildDependenciesVersions.MATERIAL3}"
    const val COMPOSE_MATERIAL_ICON = "androidx.compose.material:material-icons-extended:${BuildDependenciesVersions.COMPOSE}"
    const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${BuildDependenciesVersions.COMPOSE}"
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${BuildDependenciesVersions.ACTIVITY_COMPOSE}"
    const val SKYDOVES = "com.github.skydoves:sandwich:${BuildDependenciesVersions.SKYDOVES}"

    const val SWIPE_REFRESH = "com.google.accompanist:accompanist-swiperefresh:${BuildDependenciesVersions.ACCOMPANIST}"
    const val PLACEHOLDER = "com.google.accompanist:accompanist-placeholder:${BuildDependenciesVersions.ACCOMPANIST}"

    const val CHUCKER = "com.github.chuckerteam.chucker:library-no-op:${BuildDependenciesVersions.CHUCKER}"


}