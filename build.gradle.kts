import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "0.4.10"
    java
    kotlin("jvm") version "1.3.60"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("com.gluonhq.client-gradle-plugin") version "0.0.11"

}

group = "io.github.elena-lyulina.codetracker"
version = "1.0-SNAPSHOT"

repositories {
    maven (url = "https://www.jetbrains.com/intellij-repository/releases")
    maven (url = "https://jetbrains.bintray.com/intellij-third-party-dependencies")
    maven (url = "https://nexus.gluonhq.com/nexus/content/repositories/releases/")
    mavenCentral()
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.opencsv","opencsv", "5.0")
    implementation("joda-time", "joda-time", "2.9.2")
    implementation("org.apache.commons", "commons-csv", "1.7")
    // https://mvnrepository.com/artifact/com.gluonhq/charm-glisten
    implementation("com.gluonhq", "charm-glisten", "6.0.1")
    implementation("com.google.code.gson", "gson", "2.8.5")
    implementation("com.squareup.okhttp3", "okhttp", "4.2.2")

    testCompile("junit", "junit", "4.12")

}

 /*
    Uncomment for testing with Rider IDE
 */
//tasks.getByName<org.jetbrains.intellij.tasks.IntelliJInstrumentCodeTask>("instrumentCode") {
//    setCompilerVersion("192.6817.32")
//}
//intellij {
//    type = "RD"
//    version = "2019.2-SNAPSHOT"
//    downloadSources = false
//    intellij.updateSinceUntilBuild = false
//}


 /*
    Uncomment for testing with Intellij IDEA
 */
//intellij {
//    version = "2019.2.2"
//}


 /*
    Uncomment for testing with PyCharm IDE
 */
//intellij {
//    version = "2019.2.3"
//    type = "PY"
//}


intellij {
    val ideVersion = System.getenv().getOrDefault(
        "CODE_TRACKER_IDEA_VERSION",
        "192.5728.98"
    )
    println("Using ide version: ${ideVersion}")
    version = ideVersion
    pluginName = "code-tracker-plugin"
    downloadSources = true
    sameSinceUntilBuild = false
    updateSinceUntilBuild = false
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml", "javafx.swing")
    configuration = "compileOnly"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      Add change notes here.<br>
      <em>most HTML tags may be used</em>""")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

gluonClient {
    reflectionList = arrayListOf("javafx.fxml.FXMLLoader", "com.gluon.hello.views.HelloPresenter",
        "javafx.scene.control.Button", "javafx.scene.control.Label")
}
tasks.withType<ShadowJar>() {
    archiveBaseName.set("code-tracker-PTHS")
}


