import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  val kotlinVersion = "1.5.10"
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion
  id("org.springframework.boot") version "2.3.12.RELEASE"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  id("jacoco")
  `maven-publish`
  distribution
}

group = "amaze.us"
version = if (project.properties["version"] != "") project.properties["version"].toString() else "local"
description = "Space Stack Application: ${rootProject.name.split("-").map { it.capitalize() }.joinToString { " " }}"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  //springboot
  implementation("org.springframework.boot:spring-boot-starter"){
    exclude(module = "spring-aop")
  }
  implementation("org.springframework.boot:spring-boot-starter-web"){
    exclude(module = "spring-boot-starter-tomcat")
  }
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-undertow")
  implementation("org.springframework.boot:spring-boot-starter-actuator")

  //swagger
  implementation("io.springfox:springfox-swagger2:3.0.0")
  implementation("io.springfox:springfox-swagger-ui:3.0.0")

  //jwt
  implementation("io.jsonwebtoken:jjwt:0.9.1")
  implementation("javax.xml.bind:jaxb-api:2.3.1")

  //logs
  implementation("ch.qos.logback:logback-core:1.2.9")
  implementation("ch.qos.logback:logback-classic:1.2.9")
  implementation("ch.qos.logback:logback-access:1.2.9")
  implementation("net.logstash.logback:logstash-logback-encoder:6.2")

  // tests
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(module = "junit")
    exclude(module = "junit-vintage-engine")
    exclude(module = "mockito-core")
  }
  testImplementation("org.springframework.security:spring-security-test") {
    exclude(module = "junit")
    exclude(module = "junit-vintage-engine")
    exclude(module = "mockito-core")
  }
  testImplementation("org.testcontainers:mongodb:1.17.4")
  testImplementation("com.ninja-squad:springmockk:2.0.2")
  testImplementation("com.github.tomakehurst:wiremock:2.27.2")
  testImplementation("io.mockk:mockk:1.9.3")
  testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    showStandardStreams = true
    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    events("skipped", "failed")
  }
}

tasks.jacocoTestReport {
  reports {
    xml.required.set(true)
    html.required.set(true)
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks.withType<JacocoCoverageVerification> {
  afterEvaluate {
    classDirectories.setFrom(files(classDirectories.files.map {
      fileTree(it).apply {
        exclude("**/ColonyKeplerApplication**")
      }
    }))
  }
}

tasks.withType<JacocoReport> {
  afterEvaluate {
    classDirectories.setFrom(files(classDirectories.files.map {
      fileTree(it).apply {
        exclude("**/ColonyKeplerApplication**")
      }
    }))
  }
}

tasks.create<Exec>("dockerBuild") {
  commandLine = listOf("docker", "build", "-t", project.name, ".")
}

tasks.create<Exec>("dockerPush") {
  dependsOn("dockerBuild")
  commandLine = listOf("docker", "push", project.name)
}
