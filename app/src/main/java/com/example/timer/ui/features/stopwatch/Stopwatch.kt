package com.example.timer.ui.features.stopwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.model.data.StopwatchLap
import com.example.timer.ui.theme.BackGround
import com.example.timer.ui.theme.BlueDark
import com.example.timer.ui.theme.Pink
import com.example.timer.ui.theme.Shapes
import dev.burnoo.cokoin.navigation.getNavViewModel

@Composable
fun StopwatchScreen() {
    val viewModel = getNavViewModel<StopwatchViewModel>()
    val fraction = if (viewModel.lapList.value.isEmpty()) 140.dp else 95.dp
    val paddingBottom =if (viewModel.lapList.value.isEmpty()) 0.45f else 0.35f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = fraction), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            StopwatchText(text = viewModel.stopwatch.value)

            if (viewModel.lapList.value.isNotEmpty()) {
                LapText(text = viewModel.lapTimeString.value)
                TextBox(viewModel.lapList.value)
            }

        }

        BottomButtons(paddingBottom, leftText = viewModel.leftText.value, rightText = viewModel.rightText.value,
            onLeftClick = {
                if (it == "Start") {
                    viewModel.onStartClick()
                } else if (it == "Stop") {
                    viewModel.onStopClick()
                } else if (it == "Resume") {
                    viewModel.onResumeClick()
                }
            }, onRightClick = {
                if (it == "Lap" && viewModel.leftText.value == "Stop") {
                    viewModel.onLapClick()
                } else if (it == "Reset") {
                    viewModel.onResetClick()
                }
            })

    }
}

@Composable
fun StopwatchText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        color = BlueDark,
        textAlign = TextAlign.Center,
        fontSize = 40.sp
    )
}

@Composable
fun LapText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Normal,
        color = BlueDark,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 8.dp),
        fontSize = 25.sp
    )
}

@Composable
fun TextBox(data: List<StopwatchLap>) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 22.dp, end = 22.dp, top = 22.dp, bottom = 2.dp)
    ) {
        Text(text ="Lap", fontSize = 18.sp, color = Color.Gray)
        Text(text = "Lap times" , fontSize = 18.sp, color = Color.Gray)
        Text(text ="Overall time" , fontSize = 18.sp, color = Color.Gray)
    }

    Divider(
        color = Color.LightGray, thickness = 1.dp,
        modifier = Modifier.padding(top = 2.dp, start = 20.dp, end = 20.dp)
    )

    LazyColumn(reverseLayout = true,state = LazyListState(firstVisibleItemIndex = data.size, firstVisibleItemScrollOffset = data.size),
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 22.dp, vertical = 8.dp)
    ) {
        items(data.size) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text(text =if ( data[it].lap.toInt() < 10)  " ${data[it].lap} " else  data[it].lap, fontSize = 18.sp)
                Text(text = data[it].lapTimes , fontSize = 18.sp)
                Text(text = data[it].overallTime , fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun BottomButtons(
    paddingBottom:Float,
    leftText: String,
    rightText: String,
    onRightClick: (String) -> Unit,
    onLeftClick: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(paddingBottom)
    ) {
        Button(
            onClick = { onLeftClick.invoke(leftText) },
            shape = Shapes.medium,
            colors = ButtonDefaults.buttonColors(
                Pink
            ),
            modifier = Modifier
                .size(140.dp, 60.dp)
                .padding(4.dp)
        ) {
            Text(text = leftText, fontSize = 20.sp, color = Color.White)
        }

        Button(
            onClick = { onRightClick.invoke(rightText) },
            shape = Shapes.medium,
            colors = ButtonDefaults.buttonColors(
                Pink
            ),
            modifier = Modifier
                .size(140.dp, 60.dp)
                .padding(4.dp)
        ) {
            Text(text = rightText, fontSize = 20.sp, color = Color.White)
        }
    }
}
