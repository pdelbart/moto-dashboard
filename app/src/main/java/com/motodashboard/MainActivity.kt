package com.motodashboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private var currentSpeed by mutableStateOf(0f)
    private var batteryLevel by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        checkPermissions()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                Row(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    // Gauche : Navigation
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(0xFF1E1E1E))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = {
                            val intent = packageManager.getLaunchIntentForPackage("com.google.android.apps.maps")
                            intent?.let { startActivity(it) }
                        }) {
                            Text("Google Maps", fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Milieu : Vitesse + Batterie + Heure
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(0xFF1E1E1E))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var timeText by remember { mutableStateOf("") }
                        LaunchedEffect(Unit) {
                            while(true) {
                                timeText = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                                batteryLevel = getBatteryPercentage(this@MainActivity)
                                kotlinx.coroutines.delay(1000)
                            }
                        }
                        Text(text = timeText, fontSize = 48.sp, color = Color.White)

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "${currentSpeed.toInt()}", fontSize = 64.sp, color = Color(0xFF00E5FF))
                            Text(text = "km/h GPS", fontSize = 14.sp, color = Color.LightGray)
                        }

                        Text(text = "Batterie: $batteryLevel%", fontSize = 16.sp, color = Color(0xFF00FF66))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Droite : Notifications
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(0xFF1E1E1E))
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Notifications", color = Color.White, fontSize = 16.sp)
                            Button(
                                onClick = {
                                    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                                    startActivity(intent)
                                },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("Activer", fontSize = 10.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn {
                            items(NotificationListener.activeNotificationsList) { notif ->
                                Text(
                                    text = notif,
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1f, this)
        }
    }

    private fun getBatteryPercentage(context: Context): Int {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    override fun onLocationChanged(location: Location) {
        if (location.hasSpeed()) {
            currentSpeed = location.speed * 3.6f
        }
    }
}
