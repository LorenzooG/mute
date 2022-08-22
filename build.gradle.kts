plugins {
    java
}

group = "me.devgabi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    implementation("org.projectlombok:lombok:1.18.12")
    implementation("com.github.zkingboos:UniversalWrapper:3.0.5-SNAPSHOT")
    implementation("com.github.SaiintBrisson:command-framework:master")
    implementation("mysql:mysql-connector-java:8.0.20")

    annotationProcessor("org.projectlombok:lombok:1.18.12")

    testImplementation("junit", "junit", "4.12")
}



configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}