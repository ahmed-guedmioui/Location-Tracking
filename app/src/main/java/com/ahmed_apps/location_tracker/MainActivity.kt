package com.ahmed_apps.location_tracker

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.ahmed_apps.location_tracker.ui.theme.LocationTrackerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val locationManager by lazy {
        LocationManager(applicationContext)
    }

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this, permissions, 100
        )

        setContent {
            LocationTrackerTheme {
                Screen()
            }
        }
    }

    @Composable
    fun Screen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            var locationText by remember {
                mutableStateOf("")
            }

            Text(text = locationText)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    locationManager.getLocation { latitude, longitude ->
                        locationText = "Location: ..$latitude / ..$longitude"
                    }
                }
            ) {
                Text(text = "Get Location")
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    Intent(
                        applicationContext, LocationTrackerService::class.java
                    ).also {
                        it.action = LocationTrackerService.Action.START.name
                        startService(it)
                    }
                }
            ) {
                Text(text = "Start Tracking")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Intent(
                        applicationContext, LocationTrackerService::class.java
                    ).also {
                        it.action = LocationTrackerService.Action.STOP.name
                        startService(it)
                    }
                }
            ) {
                Text(text = "Stop Tracking")
            }
        }
    }
}


















