plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  google()
}

gradlePlugin {
  plugins {
    register("common-android-plugin") {
      id = "common-android-plugin"
      implementationClass = "CommonAndroidPlugin"
    }
  }
}

dependencies {
  implementation("com.android.tools.build:gradle:7.0.3")
  implementation(kotlin("gradle-plugin", "1.6.10"))
}
