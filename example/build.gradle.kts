plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.jetbrains.compose.compiler)
}

android {
	compileSdk = libs.versions.compileSdk.get().toInt()
	
	defaultConfig {
		applicationId = "com.dsc.formbuilder"
		minSdk = libs.versions.minSdk.get().toInt()
		targetSdk = libs.versions.targetSdk.get().toInt()
		versionCode = libs.versions.appVersionCode.get().toInt()
		versionName = libs.versions.appVersionName.get()
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	
	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility(libs.versions.jvmTarget.get())
		targetCompatibility(libs.versions.jvmTarget.get())
		isCoreLibraryDesugaringEnabled = true
	}
	
	kotlin {
		jvmToolchain(libs.versions.jvmTarget.get().toInt())
	}
	buildFeatures {
		compose = true
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
	namespace = "com.dsc.formbuilder"
}

dependencies {
	
	implementation(project(":form-builder"))
	// For using modern java 8 classes with older versions of android
	coreLibraryDesugaring(libs.core.java8)
	
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.activity.compose)
	implementation(libs.android.material)
	implementation(libs.androidx.lifecycle.runtime)
	
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.material)
	implementation(libs.androidx.compose.animation)
	implementation(libs.androidx.compose.ui.tooling.preview)
	
	debugImplementation(libs.androidx.compose.ui.tooling)
	
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.compose.material3.window.size)
}