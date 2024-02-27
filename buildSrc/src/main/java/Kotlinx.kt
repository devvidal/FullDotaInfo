object Kotlinx {

    private const val coroutinesCoreVersion = "1.6.4"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion"

    // Need for tests. Plugin doesn't work.
    private const val serializationVersion = "1.5.0"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"
}