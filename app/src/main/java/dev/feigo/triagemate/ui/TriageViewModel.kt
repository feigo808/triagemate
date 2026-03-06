package dev.feigo.triagemate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.feigo.triagemate.core.analyze.LogSanitizer
import dev.feigo.triagemate.core.analyze.TriageAnalyzer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TriageViewModel(
    private val analyzer: TriageAnalyzer,
    private val workDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    // 1) Inputs (what user typed)
    private val _contextText = MutableStateFlow("")
    val contextText: StateFlow<String> = _contextText.asStateFlow()

    private val _logText = MutableStateFlow("")
    val logText: StateFlow<String> = _logText.asStateFlow()

    // 2) UI State
    private val _uiState = MutableStateFlow<TriageUiState>(TriageUiState.Idle)
    val uiState: StateFlow<TriageUiState> = _uiState.asStateFlow()

    fun onContextChanged(value: String) {
        _contextText.value = value
    }

    fun onLogChanged(value: String) {
        _logText.value = value
    }

    fun analyze() {
        val rawLog = _logText.value

        // Basic validation (fast)
        if (rawLog.isBlank()) {
            _uiState.value = TriageUiState.Error("Paste a crash log or stacktrace first.")
            return
        }

        viewModelScope.launch {
            _uiState.value = TriageUiState.Loading

            val result = runCatching {
                val safeContext = LogSanitizer.sanitizeContext(_contextText.value)
                val safeLog = LogSanitizer.sanitizeLog(rawLog)

                // Do heavier work off the main thread
                withContext(workDispatcher) {
                    analyzer.analyze(
                        context = safeContext,
                        rawLog = safeLog
                    )
                }
            }

            result
                .onSuccess { report ->
                    _uiState.value = TriageUiState.Success(report)
                }
                .onFailure { e ->
                    _uiState.value = TriageUiState.Error(
                        e.message ?: "Something went wrong while analyzing."
                    )
                }
        }
    }

    fun reset() {
        _uiState.value = TriageUiState.Idle
    }

    fun clearInputs() {
        _contextText.value = ""
        _logText.value = ""
        _uiState.value = TriageUiState.Idle
    }
}