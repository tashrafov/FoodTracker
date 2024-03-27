plugins {
    `android-library`
    `kotlin-android`
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.ashrafovtaghi.core_ui"
}