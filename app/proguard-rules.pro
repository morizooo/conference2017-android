# ----------------------------------------
# moshi
# ----------------------------------------
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

# ----------------------------------------
# Retrofit and OkHttp
# ----------------------------------------
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-dontwarn com.squareup.okhttp.**

# ----------------------------------------
# Picasso
# ----------------------------------------
-dontwarn com.squareup.picasso.**

# ----------------------------------------
# Support Library
# ----------------------------------------
-dontwarn android.support.**
-keep class android.support.** { *; }

# ----------------------------------------
# App
# ----------------------------------------
-keep class io.builderscon.conference2017.** { *; }
-keepnames class ** { *; }

-dontwarn javax.annotation.**

# ----------------------------------------
# Kotlin
# ----------------------------------------
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# ----------------------------------------
# Kotlin reflect
# ----------------------------------------
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** {
  public protected private *;
}

-keepclassmembers public class io.builderscon.client.model.** {
    public synthetic <methods>;
}