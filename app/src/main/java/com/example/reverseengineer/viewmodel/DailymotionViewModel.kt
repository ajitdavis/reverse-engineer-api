package com.example.reverseengineer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reverseengineer.data.DailymotionVideoMetadata
import com.example.reverseengineer.network.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DailymotionUiState {
    object Idle : DailymotionUiState()
    object Loading : DailymotionUiState()
    data class Success(val metadata: DailymotionVideoMetadata) : DailymotionUiState()
    data class Error(val message: String) : DailymotionUiState()
}

class DailymotionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<DailymotionUiState>(DailymotionUiState.Idle)
    val uiState: StateFlow<DailymotionUiState> = _uiState.asStateFlow()
    
    fun fetchVideoMetadata(videoId: String) {
        if (videoId.isBlank()) {
            _uiState.value = DailymotionUiState.Error("Please enter a video ID")
            return
        }
        
        _uiState.value = DailymotionUiState.Loading
        
        viewModelScope.launch {
            try {
                val metadata = NetworkModule.dailymotionApiService.getVideoMetadata(videoId)
                if (metadata.title == null && metadata.description == null) {
                    _uiState.value = DailymotionUiState.Error("Invalid video data received")
                } else {
                    _uiState.value = DailymotionUiState.Success(metadata)
                }
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("404") == true -> "Video not found. Please check the video ID."
                    e.message?.contains("network") == true -> "Network error. Please check your connection."
                    else -> "Error: ${e.message ?: "Unknown error occurred"}"
                }
                _uiState.value = DailymotionUiState.Error(errorMessage)
            }
        }
    }
} 