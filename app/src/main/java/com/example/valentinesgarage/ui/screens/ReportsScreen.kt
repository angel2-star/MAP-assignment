package com.example.valentinesgarage.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.valentinesgarage.data.entity.TaskEntity
import com.example.valentinesgarage.data.entity.VehicleEntity
import com.example.valentinesgarage.viewmodel.ReportsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    onLogout: () -> Unit,
    reportsViewModel: ReportsViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("garage_prefs", Context.MODE_PRIVATE)
    val scope = rememberCoroutineScope()

    val vehicles by reportsViewModel.allVehicles.observeAsState(emptyList())
    val completedTasks by reportsViewModel.completedTasks.observeAsState(emptyList())
    var tasksWithNames by remember {
        mutableStateOf<List<Pair<TaskEntity, String>>>(emptyList())
    }

    LaunchedEffect(completedTasks) {
        scope.launch {
            tasksWithNames = completedTasks.map { task ->
                val name = reportsViewModel.getUserName(task.assignedToUserId)
                Pair(task, name)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Reports", fontWeight = FontWeight.Bold)
                        Text(
                            "${vehicles.size} vehicles · ${completedTasks.size} tasks done",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEBEE),
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "🚛  Vehicle Check-ins",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(containerColor = Color(0xFFFFEBEE)) {
                        Text("${vehicles.size}", color = Color(0xFFD32F2F))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (vehicles.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                    ) {
                        Text(
                            "No vehicles checked in yet.",
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(vehicles) { vehicle ->
                    VehicleReportCard(vehicle)
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 2.dp, color = Color(0xFFD32F2F))
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "✓  Completed Tasks",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1D9E75)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(containerColor = Color(0xFFFFEBEE)) {
                        Text("${tasksWithNames.size}", color = Color(0xFFD32F2F))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (tasksWithNames.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                    ) {
                        Text(
                            "No completed tasks yet.",
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(tasksWithNames) { (task, mechanicName) ->
                    TaskReportCard(task, mechanicName)
                }
            }
        }
    }
}

@Composable
fun VehicleReportCard(vehicle: VehicleEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "🚛 ${vehicle.plateNumber}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Text(
                    "${"★".repeat(vehicle.conditionRating)}${"☆".repeat(5 - vehicle.conditionRating)}",
                    color = Color(0xFFFFB300),
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text("Owner: ${vehicle.ownerName}", color = Color.DarkGray, fontSize = 14.sp)
            Text(
                "Kilometers: ${vehicle.kilometersDriven} km",
                color = Color.DarkGray,
                fontSize = 14.sp
            )
            if (vehicle.conditionNotes.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Notes: ${vehicle.conditionNotes}",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun TaskReportCard(task: TaskEntity, mechanicName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("✓", color = Color(0xFF1D9E75), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                if (task.notes.isNotBlank()) {
                    Text(
                        "Notes: ${task.notes}",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )
                }
                Text(
                    "By: $mechanicName",
                    color = Color(0xFF1D9E75),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


//package com.example.valentinesgarage.ui.screens
//
//import android.content.Context
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ExitToApp
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.valentinesgarage.data.entity.TaskEntity
//import com.example.valentinesgarage.data.entity.VehicleEntity
//import com.example.valentinesgarage.viewmodel.ReportsViewModel
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReportsScreen(
//    onLogout: () -> Unit,
//    reportsViewModel: ReportsViewModel = viewModel()
//) {
//    val context = LocalContext.current
//    val prefs = context.getSharedPreferences("garage_prefs", Context.MODE_PRIVATE)
//    val scope = rememberCoroutineScope()
//
//    val vehicles by reportsViewModel.allVehicles.observeAsState(emptyList())
//    val completedTasks by reportsViewModel.completedTasks.observeAsState(emptyList())
//    var tasksWithNames by remember { mutableStateOf<List<Pair<TaskEntity, String>>>(emptyList()) }
//
//    LaunchedEffect(completedTasks) {
//        scope.launch {
//            tasksWithNames = completedTasks.map { task ->
//                val name = reportsViewModel.getUserName(task.assignedToUserId)
//                Pair(task, name)
//            }
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Reports") },
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
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            item {
//                Text(
//                    "Vehicle Check-ins",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF1D9E75)
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//
//            if (vehicles.isEmpty()) {
//                item { Text("No vehicles checked in yet.", color = Color.Gray) }
//            } else {
//                items(vehicles) { vehicle ->
//                    VehicleReportCard(vehicle)
//                }
//            }
//
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//                Divider()
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    "Completed Tasks by Mechanics",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF1D9E75)
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//
//            if (tasksWithNames.isEmpty()) {
//                item { Text("No completed tasks yet.", color = Color.Gray) }
//            } else {
//                items(tasksWithNames) { (task, mechanicName) ->
//                    TaskReportCard(task, mechanicName)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun VehicleReportCard(vehicle: VehicleEntity) {
//    Card(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Text("🚛 ${vehicle.plateNumber}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//            Text("Owner: ${vehicle.ownerName}", color = Color.DarkGray)
//            Text("Kilometers: ${vehicle.kilometersDriven} km", color = Color.DarkGray)
//            Text(
//                "Condition: ${"★".repeat(vehicle.conditionRating)}${"☆".repeat(5 - vehicle.conditionRating)}",
//                color = Color.DarkGray
//            )
//            if (vehicle.conditionNotes.isNotBlank()) {
//                Text("Notes: ${vehicle.conditionNotes}", color = Color.Gray, fontSize = 13.sp)
//            }
//        }
//    }
//}
//
//@Composable
//fun TaskReportCard(task: TaskEntity, mechanicName: String) {
//    Card(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Text("✓ ${task.title}", fontWeight = FontWeight.Bold)
//            Text(
//                if (task.notes.isNotBlank()) "Notes: ${task.notes}" else "No notes",
//                color = Color.DarkGray,
//                fontSize = 13.sp
//            )
//            Text("Done by: $mechanicName", color = Color(0xFF1D9E75), fontSize = 12.sp)
//        }
//    }
//}
