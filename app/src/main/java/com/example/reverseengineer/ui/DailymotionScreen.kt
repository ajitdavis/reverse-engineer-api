package com.example.reverseengineer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reverseengineer.viewmodel.DailymotionUiState
import com.example.reverseengineer.viewmodel.DailymotionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailymotionScreen(
    modifier: Modifier = Modifier,
    viewModel: DailymotionViewModel = viewModel()
) {
    var videoId by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dailymotion Video Metadata",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        TextField(
            value = videoId,
            onValueChange = { videoId = it },
            label = { Text("Video ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        Button(
            onClick = { viewModel.fetchVideoMetadata(videoId) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            enabled = videoId.isNotBlank() && uiState !is DailymotionUiState.Loading
        ) {
            Text("Fetch Metadata")
        }
        
        when (val currentState = uiState) {
            is DailymotionUiState.Idle -> {
                Text(
                    text = "Enter a Dailymotion video ID and click 'Fetch Metadata'",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            is DailymotionUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Fetching metadata...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            is DailymotionUiState.Success -> {
                val metadata = currentState.metadata
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Video Metadata",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        MetadataItem("Title", metadata.title ?: "No title available")
                        MetadataItem("Description", metadata.description ?: "No description available")
                        MetadataItem("Duration", "${metadata.duration ?: 0} seconds")
                        MetadataItem("Channel", metadata.owner?.username ?: "Unknown channel")
                    }
                }
            }
            
            is DailymotionUiState.Error -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚠️",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = currentState.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MetadataItem(
    label: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value ?: "N/A",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
} 