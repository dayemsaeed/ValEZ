package com.lumen.valez_android.ui.view

import android.Manifest
import android.graphics.Rect
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.lumen.valez_android.ui.theme.ValEZAndroidTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VinScanScreen() {
    ValEZAndroidTheme {
        // Camera permission state
        val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
        // VIN text and bounding box state
        var recognizedVin by remember { mutableStateOf("") }
        var boundingBox by remember { mutableStateOf<Rect?>(null) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when {
                cameraPermissionState.status.isGranted -> {
                    CameraPreview { text, box ->
                        recognizedVin = text
                        boundingBox = box
                    }

                    // Draw a rectangle and VIN text if a VIN is detected
                    boundingBox?.let { box ->
                        DrawBoundingBox(box)
                        Text(
                            text = recognizedVin,
                            modifier = Modifier
                                .offset(
                                    x = with(LocalDensity.current) { box.left.toDp() },
                                    y = with(LocalDensity.current) { (box.top - 50).toDp() } // Adjust as needed
                                ),
                            color = Color.White
                        )
                    }
                }
                cameraPermissionState.status.shouldShowRationale ||
                        !cameraPermissionState.status.isGranted -> {
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text("Request permission")
                    }
                }
                else -> {
                    Text("Permission denied. Please grant camera permission.")
                }
            }
        }
    }
}

@Composable
fun CameraPreview(
    onTextRecognized: (String, Rect) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }
    cameraController.bindToLifecycle(lifecycleOwner)

    // Set up the text recognition analysis
    val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val analyzer = provideTextRecognitionAnalyzer { text, rect ->
        val vin = extractVin(text)
        if (vin != null) {
            onTextRecognized(vin, rect) // Provide VIN and bounding box
        }
    }
    cameraController.setImageAnalysisAnalyzer(
        ContextCompat.getMainExecutor(context), analyzer
    )

    // Camera preview
    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                controller = cameraController
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DrawBoundingBox(boundingBox: Rect) {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(boundingBox.left.toFloat(), boundingBox.top.toFloat()),
                size = Size(
                    width = boundingBox.width().toFloat(),
                    height = boundingBox.height().toFloat()
                ),
                style = Stroke(width = 3.dp.toPx())
            )
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
fun provideTextRecognitionAnalyzer(onTextRecognized: (String, Rect) -> Unit): ImageAnalysis.Analyzer {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    return ImageAnalysis.Analyzer { imageProxy ->
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    for (block in visionText.textBlocks) {
                        for (line in block.lines) {
                            val vin = extractVin(line.text)
                            if (vin != null) {
                                onTextRecognized(vin, line.boundingBox ?: Rect()) // Use this VIN and its bounding box
                                break
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("TextRecognition", "Error processing image", e)
                }
                .addOnCompleteListener {
                    imageProxy.close() // Close to allow next image to be processed
                }
        }
    }
}

fun extractVin(text: String): String? {
    val vinRegex = "[A-HJ-NPR-Z0-9]{17}".toRegex()
    val matchResult = vinRegex.find(text)
    return matchResult?.value // This will be the VIN if found, or null if not found
}
