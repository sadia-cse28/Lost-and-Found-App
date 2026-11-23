package com.example.lostfound // আপনার প্যাকেজ নাম ঠিক আছে কি না দেখে নিন

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("splash") }

    when (currentScreen) {
        "splash" -> SplashScreen { currentScreen = "login" }
        "login" -> LoginScreen { currentScreen = "list" }
        "list" -> RecentItemsScreen { currentScreen = "add_post" }
        "add_post" -> AddPostScreen { currentScreen = "list" }
    }
}

// ১. স্প্ল্যাশ স্ক্রিন
@Composable
fun SplashScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(100.dp), tint = Color(0xFF673AB7))
        Text("Lost & Found", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF673AB7))
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = onStartClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.width(180.dp).height(50.dp)
        ) {
            Text("শুরু করুন", fontSize = 18.sp, color = Color.White)
        }
    }
}

// ২. লগইন স্ক্রিন (টাইপিং ফিক্সড)
@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text("লগইন করুন", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("ইমেইল বা ফোন") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("পাসওয়ার্ড") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = onLoginClick, modifier = Modifier.fillMaxWidth().height(55.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)), shape = RoundedCornerShape(25.dp)) {
            Text("প্রবেশ করুন", fontSize = 18.sp)
        }
    }
}

// ৩. সাম্প্রতিক তালিকা (সার্চ বক্স ফিক্সড)
@Composable
fun RecentItemsScreen(onAddClick: () -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val itemsList = listOf(
        ItemData("আইডি কার্ড", "ইউনিভার্সিটি", "Lost", Color.Red),
        ItemData("মানিব্যাগ", "ঢাকা", "Lost", Color.Red),
        ItemData("চাবির তোড়া", "কুমিল্লা", "Found", Color(0xFF4CAF50))
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick, containerColor = Color(0xFF673AB7), shape = CircleShape) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("সাম্প্রতিক তালিকা", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFF673AB7))
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("খুঁজুন...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(itemsList) { item -> RecentItemCard(item) }
            }
        }
    }
}

// ৪. নতুন পোস্ট করুন (সব বক্স ফিক্সড)
@Composable
fun AddPostScreen(onBack: () -> Unit) {
    var thingName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var isLostSelected by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("নতুন পোস্ট করুন", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { isLostSelected = true }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (isLostSelected) Color(0xFFE1BEE7) else Color.White), border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(20.dp)) { Text("হারিয়েছি", color = Color.Black) }
            Button(onClick = { isLostSelected = false }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (!isLostSelected) Color(0xFFE1BEE7) else Color.White), border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(20.dp)) { Text("পেয়েছি", color = Color.Black) }
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = thingName, onValueChange = { thingName = it }, label = { Text("কি হারিয়েছেন/পেয়েছেন?") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("লোকেশন") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("যোগাযোগের নম্বর") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth().height(55.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)), shape = RoundedCornerShape(25.dp)) { Text("পোস্ট নিশ্চিত করুন", fontSize = 18.sp) }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("মিল পাওয়া গেছে!", fontWeight = FontWeight.Bold) },
            text = { Text("আপনার দেওয়া জিনিসের সাথে মিলে যায় এমন একটি পোস্ট অলরেডি তালিকায় আছে। আপনি কি তাও পোস্ট করতে চান?") },
            confirmButton = { TextButton(onClick = onBack) { Text("হ্যাঁ, পোস্ট করুন", color = Color(0xFF673AB7)) } },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("বাতিল", color = Color.Gray) } }
        )
    }
}

@Composable
fun RecentItemCard(item: ItemData) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F0F4))) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(item.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(item.location, fontSize = 14.sp, color = Color.Gray)
            }
            Text(item.status, color = item.statusColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

data class ItemData(val title: String, val location: String, val status: String, val statusColor: Color)