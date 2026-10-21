package com.motodashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun NavigationPanel(modifier: Modifier = Modifier) {
    // Position initiale par défaut (Dijon)
    val defaultLocation = LatLng(47.3220, 5.0415)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 15f)
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(16.dp))
            .padding(4.dp)
    ) {
        // 1. La Carte Google Maps en arrière-plan
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black, shape = RoundedCornerShape(12.dp)),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                compassEnabled = true,
                myLocationButtonEnabled = true
            ),
            properties = MapProperties(
                isMyLocationEnabled = false // Passer à true une fois la permission GPS accordée
            )
        )

        // 2. Panneau d'instruction de direction (En haut à gauche)
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF006C3B)), // Vert navigation
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .fillMaxWidth(0.9f)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Navigation,
                    contentDescription = "Direction",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "1,2 km",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Dijon / Centre-ville",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 3. Barre d'informations de trajet (En bas)
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121212).copy(alpha = 0.9f)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Annuler la navigation */ }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = Color.White
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "23 min",
                        color = Color(0xFF00E676),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "18 km · 16:42",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                IconButton(onClick = { /* Changer d'itinéraire */ }) {
                    Icon(
                        imageVector = Icons.Default.AltRoute,
                        contentDescription = "Itinéraire alternatif",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
