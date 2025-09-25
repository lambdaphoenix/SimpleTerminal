plugins {
    id("java")
}

group = "io.github.lambdaphoenix"
version = "0.1.0"
description = "A Java library for styled console output and interactive prompts"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}