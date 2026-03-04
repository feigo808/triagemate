package dev.feigo.triagemate.core.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class TriageReportTest {

    // Use a stable JSON configuration so tests don't break unexpectedly.
    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        prettyPrint = false
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
        encodeDefaults = true
    }

    @Test
    fun triageReport_serializes_and_deserializes() {
        val report = TriageReport(
            title = "Crash: NullPointerException",
            summary = "User tapped Pay and app crashed.",
            severity = Severity.HIGH,
            confidence = Confidence.MEDIUM,
            suspectedCauses = listOf(
                "paymentMethod is null due to missing selection",
                "race condition between state update and button click"
            ),
            whereToLook = listOf(
                "PaymentViewModel.onPayClicked",
                "Checkout UI enable/disable logic"
            ),
            reproSteps = listOf(
                "Open checkout screen",
                "Tap Pay quickly before selecting a payment method"
            ),
            questionsNeeded = listOf(
                "App version/build number?",
                "Device model + OS version?"
            ),
            nextSteps = listOf(
                "Add null guards + log state snapshot",
                "Disable Pay button until required state is present",
                "Add unit test for state transitions"
            )
        )

        val encoded = json.encodeToString(report)
        val decoded = json.decodeFromString<TriageReport>(encoded)

        assertEquals(report, decoded)
    }
}