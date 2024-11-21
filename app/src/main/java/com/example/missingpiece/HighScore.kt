package com.example.missingpiece

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.missingpiece.ui.theme.Orange10
import com.example.missingpiece.ui.theme.Orange40


@Composable
fun HighScore(hsViewModel: HighScoreViewModel) {

    val highScoreList by hsViewModel.highScoreList.collectAsState()

    LaunchedEffect(true) {
        hsViewModel.getHighScore()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .padding(15.dp)
                .border(2.dp, color = Orange40, shape = RoundedCornerShape(35.dp))
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f),

            ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .border(5.dp, color = Orange10, shape = RoundedCornerShape(30.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Text(
                    text = "HIGH SCORE!!", fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(15.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Text(
                        text = "Name", fontFamily = FontFamily.SansSerif,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(15.dp)
                    )
                    Spacer(Modifier.weight(0.5f))
                    Text(
                        text = "Score", fontFamily = FontFamily.SansSerif,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(15.dp)
                    )
                }
                if (highScoreList != null) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                    ) {
                        items(10) { index ->
                            Row(
                                modifier = Modifier
                                    .padding(15.dp),
                            ) {
                                Text(
                                    text = "${index + 1}. ",
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                )
                                val item = highScoreList?.getOrNull(index)
                                if (item != null) {
                                    Text(
                                        text = "${item.name}",
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                    )
                                    Spacer(Modifier.weight(0.5f))
                                    Text(
                                        text = "${item.score}",
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

