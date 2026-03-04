package dev.feigo.triagemate.ui

import dev.feigo.triagemate.core.model.TriageReport

sealed interface TriageUiState {
    data object Idle : TriageUiState
    data object Loading : TriageUiState
    data class Success(val report: TriageReport) : TriageUiState
    data class Error(val message: String) : TriageUiState
}