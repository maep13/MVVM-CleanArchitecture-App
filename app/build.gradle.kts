plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    id("dagger.hilt.android.plugin")
    //id ("androidx.room") version "2.7.0"
}

android {
    namespace = "com.example.pokemonapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pokemonapp"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
configurations.all {
    resolutionStrategy.force("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0") // ✅ Fuerza una versión compatible
}

dependencies {
    // Core Dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))


    // Room (Excluyendo versión antigua de annotations)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.runtime.android) {
        exclude("com.intellij", "annotations")
    }
    implementation(libs.coil.compose) {
        exclude(group = "io.coil-kt", module = "coil-base")
    }
    dependencies {
        implementation(libs.androidx.lifecycle.runtime.ktx) {
            exclude(group = "androidx.annotation", module = "annotation")
        }
    }
    dependencies {
        implementation(libs.androidx.activity.compose) {
            exclude(group = "androidx.savedstate", module = "savedstate-compose-android")
        }
    }

    // Navigation
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)

    // Networking & Image Loading
    implementation(libs.converter.gson)
    implementation(libs.coil.compose)
    implementation(libs.androidx.material3.android)

    // Test Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Annotations (Solo versión 23.0.0)
    implementation(libs.annotations)
    testImplementation(kotlin("test"))
}
dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}
dependencies {
    implementation(libs.logging.interceptor)
}
dependencies {
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler.v252) // ✅ Reemplazo de kapt por ksp
}
dependencies {
    implementation(libs.androidx.room.ktx) // ✅ Extensiones para Kotlin
}
dependencies {
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.camera.core)
    implementation(libs.coil.compose.v222) // ✅ Asegura que estás usando una versión estable
}
dependencies {
    implementation(libs.coil.compose) // ✅ Mantén la versión principal de Coil
    implementation(libs.coil.gif) // ✅ Agrega la extensión para decodificar GIFs
}
dependencies {
    implementation (libs.androidx.lifecycle.runtime.compose) // ✅ Versión reciente de Lifecycle
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.androidx.navigation.compose.v253)
}

dependencies {

    implementation(libs.androidx.activity)
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose.v100)
}
dependencies {
    implementation (libs.androidx.navigation.compose)
}
dependencies {
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.mockk)
}
dependencies{
    androidTestImplementation (libs.androidx.hilt.navigation.compose)
    androidTestImplementation (libs.core.ktx)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.ui.test.junit4)
}
dependencies {
    implementation (libs.hilt.android.v248)
}
dependencies {
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler.v248) // ✅ Usa KSP en lugar de KAPT
    androidTestImplementation (libs.dagger.hilt.android.testing)

}
dependencies {
    implementation(libs.androidx.hilt.navigation.compose)
}
dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler.v250)
    androidTestImplementation(libs.dagger.hilt.android.testing) // ✅ Dependencia para pruebas
    kspAndroidTest(libs.hilt.compiler.v250) // ✅ Usa KSP en lugar de KAPT para pruebas instrumentadas
    //kspAndroidTest(libs.hilt.compiler.v248)
}





