package com.example.valentinesgarage.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.valentinesgarage.ui.utils.SoundManager
import com.example.valentinesgarage.viewmodel.CheckInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInScreen(
    onGoToChecklist: (Int) -> Unit,
    onLogout: () -> Unit,
    checkInViewModel: CheckInViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("garage_prefs", Context.MODE_PRIVATE)
    val userId = prefs.getInt("user_id", -1)

    var plateNumber by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var kilometers by remember { mutableStateOf("") }
    var conditionNotes by remember { mutableStateOf("") }
    var conditionRating by remember { mutableStateOf(3) }
    var showSuccess by remember { mutableStateOf(false) }
    var lastVehicleId by remember { mutableStateOf(-1) }

    val checkInSuccess by checkInViewModel.checkInSuccess.observeAsState()
    val errorMessage by checkInViewModel.errorMessage.observeAsState()

    LaunchedEffect(checkInSuccess) {
        checkInSuccess?.let {
            lastVehicleId = it
            showSuccess = true
            SoundManager.playAction(context)
            plateNumber = ""
            ownerName = ""
            kilometers = ""
            conditionNotes = ""
            conditionRating = 3
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vehicle Check-In", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        prefs.edit().clear().apply()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Vehicle Details",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F)
                    )

                    OutlinedTextField(
                        value = plateNumber,
                        onValueChange = { plateNumber = it },
                        label = { Text("Plate Number") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = ownerName,
                        onValueChange = { ownerName = it },
                        label = { Text("Owner Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = kilometers,
                        onValueChange = { kilometers = it },
                        label = { Text("Kilometers Driven") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Condition Assessment",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F)
                    )

                    Text(
                        "Condition Rating: $conditionRating/5",
                        fontWeight = FontWeight.Medium
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        (1..5).forEach { star ->
                            FilterChip(
                                selected = conditionRating == star,
                                onClick = { conditionRating = star },
                                label = { Text("$star★") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFD32F2F),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    OutlinedTextField(
                        value = conditionNotes,
                        onValueChange = { conditionNotes = it },
                        label = { Text("Condition Notes (damage, wear, etc.)") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            Button(
                onClick = {
                    showSuccess = false
                    checkInViewModel.checkInVehicle(
                        plateNumber, ownerName, kilometers,
                        conditionNotes, conditionRating, userId
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("Check In Vehicle", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            errorMessage?.let {
                Text(it, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            if (showSuccess) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "✓ Vehicle checked in successfully!",
                            color = Color(0xFFD32F2F),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { onGoToChecklist(lastVehicleId) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF444444)
                            )
                        ) {
                            Text("Go to Checklist →")
                        }
                    }
                }
            }
        }
    }
}


//package com.example.valentinesgarage.ui.screens
//
//import android.content.Context
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ExitToApp
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.valentinesgarage.viewmodel.CheckInViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CheckInScreen(
//    onGoToChecklist: (Int) -> Unit,
//    onLogout: () -> Unit,
//    checkInViewModel: CheckInViewModel = viewModel()
//) {
//    val context = LocalContext.current
//    val prefs = context.getSharedPreferences("garage_prefs", Context.MODE_PRIVATE)
//    val userId = prefs.getInt("user_id", -1)
//
//    var plateNumber by remember { mutableStateOf("") }
//    var ownerName by remember { mutableStateOf("") }
//    var kilometers by remember { mutableStateOf("") }
//    var conditionNotes by remember { mutableStateOf("") }
//    var conditionRating by remember { mutableStateOf(3) }
//    var showSuccess by remember { mutableStateOf(false) }
//    var lastVehicleId by remember { mutableStateOf(-1) }
//
//    val checkInSuccess by checkInViewModel.checkInSuccess.observeAsState()
//    val errorMessage by checkInViewModel.errorMessage.observeAsState()
//
//    LaunchedEffect(checkInSuccess) {
//        checkInSuccess?.let {
//            lastVehicleId = it
//            showSuccess = true
//            plateNumber = ""
//            ownerName = ""
//            kilometers = ""
//            conditionNotes = ""
//            conditionRating = 3
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Vehicle Check-In") },
//                actions = {
//                    IconButton(onClick = {
//                        prefs.edit().clear().apply()
//                        onLogout()
//                    }) {
//                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
//                    }
//                }
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(24.dp)
//                .verticalScroll(rememberScrollState()),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            OutlinedTextField(
//                value = plateNumber,
//                onValueChange = { plateNumber = it },
//                label = { Text("Plate Number") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//
//            OutlinedTextField(
//                value = ownerName,
//                onValueChange = { ownerName = it },
//                label = { Text("Owner Name") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//
//            OutlinedTextField(
//                value = kilometers,
//                onValueChange = { kilometers = it },
//                label = { Text("Kilometers Driven") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                singleLine = true
//            )
//
//            Text("Vehicle Condition Rating: $conditionRating/5", fontWeight = FontWeight.Medium)
//
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                (1..5).forEach { star ->
//                    FilterChip(
//                        selected = conditionRating == star,
//                        onClick = { conditionRating = star },
//                        label = { Text("$star★") }
//                    )
//                }
//            }
//
//            OutlinedTextField(
//                value = conditionNotes,
//                onValueChange = { conditionNotes = it },
//                label = { Text("Condition Notes") },
//                modifier = Modifier.fillMaxWidth(),
//                minLines = 3
//            )
//
//            Button(
//                onClick = {
//                    showSuccess = false
//                    checkInViewModel.checkInVehicle(
//                        plateNumber, ownerName, kilometers,
//                        conditionNotes, conditionRating, userId
//                    )
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Check In Vehicle")
//            }
//
//            errorMessage?.let {
//                Text(it, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
//            }
//
//            if (showSuccess) {
//                Text(
//                    "Vehicle checked in successfully!",
//                    color = Color(0xFF1D9E75),
//                    fontWeight = FontWeight.Medium
//                )
//                Button(
//                    onClick = { onGoToChecklist(lastVehicleId) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
//                ) {
//                    Text("Go to Checklist")
//                }
//            }
//        }
//    }
//}
