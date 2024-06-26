plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.googlemappoltava"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.googlemappoltava"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.1")

    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    implementation (platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation ("org.mockito:mockito-core:5.5.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt ("com.google.dagger:hilt-android-compiler:2.46.1")


    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("com.google.maps.android:android-maps-utils:3.5.3")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.activity:activity-ktx:1.8.2")

    implementation ("com.github.bumptech.glide:glide:4.15.1")
}
kapt{
    correctErrorTypes = true
}

