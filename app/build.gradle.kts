plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}
android {
    namespace = "com.jmdevlabs.auth0nativecomponents"
    compileSdk = 37
    defaultConfig {
        applicationId = "com.jmdevlabs.auth0nativecomponents"
        minSdk = 33
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["auth0Domain"] = "@string/com_auth0_domain"
        manifestPlaceholders["auth0Scheme"] = "app"
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
    buildFeatures {
        compose = true
    }
}
configurations.all {
    resolutionStrategy {
        force("io.netty:netty-codec-http2:4.1.136.Final")
        force("io.netty:netty-codec-http:4.1.136.Final")
        force("io.netty:netty-codec:4.1.136.Final")
        force("io.netty:netty-handler:4.1.136.Final")
        force("io.netty:netty-handler-proxy:4.1.136.Final")
        force("io.netty:netty-transport-native-unix-common:4.1.136.Final")
        force("io.netty:netty-common:4.1.136.Final")
        force("org.apache.commons:commons-lang3:3.18.0")
        force("org.apache.httpcomponents:httpclient:4.5.13")
        force("org.bouncycastle:bcprov-jdk18on:1.84")
        force("org.bouncycastle:bcpkix-jdk18on:1.84")
        force("org.jetbrains.kotlin:kotlin-stdlib:2.2.20")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.auth0.universal.components)
    implementation(libs.auth0.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}