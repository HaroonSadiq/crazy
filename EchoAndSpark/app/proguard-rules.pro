# Proguard rules for Echo & Spark

# Keep the app's main classes
-keepclasseswithmembernames class com.echospark.** { *; }

# Don't obfuscate package names
-keeppackagenames com.echospark.**

# Kotlin specific rules
-keepclassmembers class kotlin.** {
    *** **(...);
}

# Keep annotations
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Firebase
-keepclassmembers class com.google.firebase.firestore.** {
    *** **(...);
}

# Jetpack Compose
-keep class androidx.compose.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.foundation.** { *; }
-keep class androidx.compose.material3.** { *; }

# Hilt Dependency Injection
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextHolder

# Room Database
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }
-keepclasseswithmembernames class * {
    @androidx.room.* <fields>;
}
-keepclasseswithmembernames class * {
    @androidx.room.* <methods>;
}

# GSON
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Keep model classes
-keep class com.echospark.domain.model.** { *; }
-keep class com.echospark.data.** { *; }

# Coroutines
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Remove assertions in production
-assumenosideeffects class java.lang.Throwable {
    public void printStackTrace(...);
}

# Optimization
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

# Keep line numbers for crash reporting
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
