package dev.feigo.triagemate.core.model

import kotlinx.serialization.Serializable

/**
 * TriageReport v1
 * If schema changes, increment version and update serialization logic accordingly.
 */
@Serializable
data class TriageReport(
    val title: String,
    val summary: String,
    val severity: Severity,
    val confidence: Confidence,
    val suspectedCauses: List<String>,
    val whereToLook: List<String>,
    val reproSteps: List<String>,
    val questionsNeeded: List<String>,
    val nextSteps: List<String>
)

@Serializable
enum class Severity {
    LOW, MEDIUM, HIGH
}

@Serializable
enum class Confidence {
    LOW, MEDIUM, HIGH
}