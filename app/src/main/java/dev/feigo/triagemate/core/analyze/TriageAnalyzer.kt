package dev.feigo.triagemate.core.analyze

import dev.feigo.triagemate.core.model.TriageReport

interface TriageAnalyzer {
    suspend fun analyze(context: String, rawLog: String): TriageReport
}