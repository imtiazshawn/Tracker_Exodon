package com.example.trackerexodon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trackerexodon.R
import com.example.trackerexodon.components.Header

@Composable
fun AboutScreen(navController: NavHostController) {
    val localUriHandler = LocalUriHandler.current

    Scaffold(
        topBar = { Header(true, navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF21353C))
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                item {
                    Text(
                        text = "About This App",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )
                    Text(
                        text = "Tracker Exodon is more than just an expense tracking tool—it's your ultimate companion for managing your finances and staying on top of your budget. With its intuitive design and robust features, Tracker Exodon empowers you to track your expenses, set financial goals, and gain insights into your spending habits.\n\n" +
                                "Whether you're managing personal finances or overseeing household budgets, Tracker Exodon offers a seamless experience to record expenses, categorize transactions, and visualize your financial data. Its user-friendly interface ensures that you can navigate effortlessly through your financial information, making it easy to stay informed and in control of your money.\n\n" +
                                "As a Mobile Application Developer, I've crafted Tracker Exodon with a passion for creating solutions that enhance your financial management. Working at Ultimate IT Solution, I strive to deliver innovative mobile applications that meet your needs and exceed expectations. Tracker Exodon reflects this commitment, offering not only functionality but also a delightful user experience.\n\n" +
                                "Explore Tracker Exodon today and discover how it can transform the way you manage your finances, enabling you to achieve your financial goals with ease.",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )
                    Text(
                        text = "About The Developer",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Meet ")
                            withStyle(style = SpanStyle(color = Color(0xFF3FDB9D))) {
                                append("Imtiaz Shawn")
                            }
                            append(", the mind behind Tracker Exodon: a passionate Mobile Application Developer specializing in Kotlin. Currently contributing to innovative solutions at Ultimate IT Solution, Imtiaz focuses on crafting efficient and user-centric mobile applications. Stay connected through Imtiaz's social media channels to explore the latest projects and contributions.")
                        },
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Developer's Social",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )
                    Row {
                        Row(
                            modifier = Modifier.clickable {
                                localUriHandler.openUri("https://github.com/imtiazshawn")
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_github),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Github", color = Color.White, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Row(
                            modifier = Modifier.clickable {
                                localUriHandler.openUri("https://www.linkedin.com/in/imtiazshawn/")
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_linkedin),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Linkedin", color = Color.White, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Row(
                            modifier = Modifier.clickable {
                                localUriHandler.openUri("https://www.facebook.com/Imtiazshawn0/")
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_facebook),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Facebook", color = Color.White, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(rememberNavController())
}
