package com.motodashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF0B0B0B) // Fond global sombre
            ) {
                // Disposition en 3 colonnes (Paysage)
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 1. PANNEAU DE GAUCHE : La vraie carte Google Maps & Navigation
                    NavigationPanel(
                        modifier = Modifier.weight(1.2f)
                    )

                    // 2. PANNEAU CENTRAL : Heure, Média, Batterie (Prochaine étape)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(16.dp))
                    ) {
                        // Emplacement temporaire du centre
                    }

                    // 3. PANNEAU DE DROITE : Notifications
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(16.dp))
                    ) {
                        // Emplacement temporaire de droite
                    }
                }
            }
        }
    }
}
