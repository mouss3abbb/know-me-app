package com.example.knowme

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.knowme.ui.theme.HeaderText
import com.example.knowme.ui.theme.KnowMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnowMeTheme {
                MainScreen(this)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KnowMeTheme {
        MainScreen(MainActivity())
    }
}

@Composable
fun MainScreen(context: Context) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.mainscreen),
                contentScale = ContentScale.FillBounds
            )

    ) {
        PersonalPicture()
        Column(
            Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ){
            Section(context,header = "Projects")
            Section(context,header = "Skills")
            Experience()
            Certificates(context)
            ContactMe(context)
        }
    }
}

@Composable
fun PersonalPicture() {
    Box(modifier = Modifier.height(300.dp)){
        var isVisible by remember {
            mutableStateOf(false)
        }
        Image(painter = painterResource(id = R.drawable.ic_pfp), contentDescription = "PersonalPicture", contentScale = ContentScale.Crop)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 200f
                    )
                )
                .clickable { isVisible = !isVisible }
        ){

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                , contentAlignment = Alignment.BottomCenter
        ){

            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInHorizontally(tween(1000)) + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Text(
                        text = "Android Developer",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInHorizontally(tween(1500)) + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Text(
                        text = "Competitive Programmer",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun Section(
    context: Context,
    header: String
) {
    val projectsIcons = listOf(R.drawable.chat,R.drawable.list,R.drawable.tic_tac_toe,R.drawable.command,R.drawable.icpcusc)
    val skillsIcons = listOf(R.drawable.c__1,R.drawable.android_1,R.drawable.golang_1,R.drawable.linux_1)
    val projectsNames = listOf("Hawadeet", "Todo App", "Tic Tac Toe", "Panda Shell", "ICPC Materials")
    val skillsNames = listOf("C++","Android Development","Go lang","Linux")
    val projectsLinks = listOf(
        "https://github.com/mouss3abbb/hawadeet-app",
        "https://github.com/mouss3abbb/CodeClause_Todo_App",
        "https://github.com/mouss3abbb/CodeClause_TicTacToe",
        "https://github.com/mouss3abbb/psh",
        "https://github.com/mouss3abbb/icpc-usc-app"
    )
    val skills = (header == "Skills")
    Column(Modifier.fillMaxSize()) {
        Text(text = header, style = TextStyle(
            fontSize = 28.sp,
            color = HeaderText,
            fontWeight = FontWeight.Medium
        ))
        LazyRow{
            items (if (skills)skillsIcons.size else projectsIcons.size){i->
                val transition = rememberInfiniteTransition()
                val padding by transition.animateValue(
                    initialValue = 0.dp,
                    targetValue = 2.dp,
                    animationSpec = infiniteRepeatable(
                        animation = tween(750),
                        repeatMode = RepeatMode.Reverse
                    ),
                    typeConverter = Dp.VectorConverter
                )
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(vertical = 26.dp, horizontal = 14.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(12.dp)
                        .clickable {
                            if (!skills) {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(projectsLinks[i])
                                startActivity(context, intent, null)
                            }
                        },
                        horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = if (skills) skillsIcons[i] else projectsIcons[i]),
                        contentDescription = "",
                        Modifier
                            .padding(4.dp)
                            .size(32.dp)
                            .padding(top = padding)
                        ,
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = if (skills) skillsNames[i] else projectsNames[i],
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = HeaderText,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Experience() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Experience", style = TextStyle(
            fontSize = 28.sp,
            color = HeaderText,
            fontWeight = FontWeight.Medium))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .paint(
                    painterResource(id = R.drawable.exp_bg),
                    contentScale = ContentScale.FillBounds
                )
                .clip(RoundedCornerShape(12.dp))
        ) {
            ExperienceItem(name = "The Sparks Foundation", date = "NOV - DEC 2022", painter = painterResource(R.drawable.tsf))
            ExperienceItem(name = "ICPC USC Community", date = "2020 - 2022", painter = painterResource(R.drawable.icpcusc))
        }
    }
}

@Composable
fun Certificates(context: Context) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Certificates", style = TextStyle(
            fontSize = 28.sp,
            color = HeaderText,
            fontWeight = FontWeight.Medium
        ))
        LazyRow{
            items(count = 1){
                Image(
                    painter = painterResource(id = R.drawable.problemsolving), contentDescription = "", contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(250.dp)
                        .clickable {
                            startActivity(
                                context,
                                Intent(Intent.ACTION_VIEW).setData(
                                    Uri.parse("https://www.hackerrank.com/certificates/a1cdfc534e1c")
                                ), null
                            )
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.sql), contentDescription = "", contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(250.dp)
                        .clickable {
                            startActivity(
                                context,
                                Intent(Intent.ACTION_VIEW).setData(
                                    Uri.parse("https://www.datacamp.com/statement-of-accomplishment/course/82828587ce5879e93cfd0583df9571a14e6ae244")
                                ), null
                            )
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.python), contentDescription = "", contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(250.dp)
                        .clickable {
                            startActivity(
                                context,
                                Intent(Intent.ACTION_VIEW).setData(
                                    Uri.parse("https://www.hackerrank.com/certificates/71a7729e46f5")
                                ), null
                            )
                        }
                )
            }
        }
    }
}

@Composable
fun ExperienceItem(
    name: String,
    date: String,
    painter: Painter
) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(Color(0xFFDFE0E0))
            .padding(25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
        ) {
        Image(painter = painter, contentDescription = "",
            Modifier
                .size(30.dp)
                .clip(CircleShape), contentScale = ContentScale.Crop)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = name, style = TextStyle(
                fontSize = 14.sp,
                color = HeaderText
            ))
            Text(text = date, style = TextStyle(
                fontSize = 14.sp,
                color = HeaderText
            ))
        }
    }
}

@Composable
fun ContactMe(context: Context) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Contact", style = TextStyle(
            fontSize = 28.sp,
            color = HeaderText,
            fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(painter = painterResource(id = R.drawable.linkedin), contentDescription = "",
                Modifier
                    .padding(end = 12.dp)
                    .size(40.dp)
                    .clickable {
                        startActivity(
                            context,
                            Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse("https://www.linkedin.com/in/abdullah-kamshishi-967635211/")),
                            null
                        )
                    }
            )
            Image(painter = painterResource(id = R.drawable.github), contentDescription = "",
                Modifier
                    .padding(end = 12.dp)
                    .size(40.dp)
                    .clickable {
                        startActivity(
                            context,
                            Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse("https://github.com/mouss3abbb")), null
                        )
                    }
            )
            Image(painter = painterResource(id = R.drawable.codeforces), contentDescription = "",
                Modifier
                    .padding(end = 12.dp)
                    .size(40.dp)
                    .clickable {
                        startActivity(
                            context,
                            Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse("https://codeforces.com/profile/PandaX")), null
                        )
                    }
            )
            Image(painter = painterResource(id = R.drawable.icpc), contentDescription = "",
                Modifier
                    .size(40.dp)
                    .clickable {
                        startActivity(
                            context,
                            Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse("https://icpc.global/ICPCID/LF23VEBBMAA8")), null
                        )
                    }
            )
        }
    }
}