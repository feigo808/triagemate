package dev.feigo.triagemate.core.analyze

object LogSanitizer {
    fun sanitizeLog(input: String, maxChars: Int = 120000): String {
        val trimmed = input.trim()

        // Truncate to maxChars
        val truncated = if (trimmed.length > maxChars) {
            trimmed.take(maxChars)
        } else {
            trimmed
        }

        return truncated
            .replace(
                Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"),
                "[EMAIL]"
            ) // Email addresses
    }

    fun sanitizeContext(input: String, maxChars: Int = 5000): String {
        val trimmed = input.trim()

        // Truncate to maxChars
        return if (trimmed.length > maxChars) {
            trimmed.take(maxChars)
        } else {
            trimmed
        }
    }
}