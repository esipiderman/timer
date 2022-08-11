package com.example.timer.ui.features.timer

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.timer.ui.theme.BackGround
import com.example.timer.ui.theme.BlueDark
import com.example.timer.ui.theme.Pink
import com.example.timer.ui.theme.Shapes
import com.example.timer.util.deleteMillis
import dev.burnoo.cokoin.navigation.getNavViewModel

@Composable
fun TimerScreen() {
    val viewModel = getNavViewModel<TimerViewModel>()
    val paddingTop = if (viewModel.isStarted.value) 50.dp else 140.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.isStarted.value) {
                CircleSlider(progress = viewModel.progress.value, timer = deleteMillis(viewModel.timeInString.value))
            } else {
                TimePicker() {
                    viewModel.timeInString.value = it
                }
            }

        }

        Buttons(viewModel.isStarted.value, leftText = viewModel.leftText.value,
            rightText = viewModel.rightText.value, onLeftClick = {
                if (it == "Start" && viewModel.timeInString.value != "00:00:00") {
                    viewModel.onStartClick()
                } else if (it == "Pause") {
                    viewModel.onPauseClick()
                } else if (it == "Resume") {
                    viewModel.onResumeClick()
                }
            }, onRightClick = {
                viewModel.onCancelClick()
            })
    }
}

@Composable
fun Buttons(
    isStarted: Boolean,
    leftText: String,
    rightText: String,
    onRightClick: (String) -> Unit,
    onLeftClick: (String) -> Unit
) {
    if (isStarted) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
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
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.265f),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    Pink
                ),
                onClick = { onLeftClick.invoke(leftText) },
                modifier = Modifier
                    .size(140.dp, 60.dp)
                    .padding(4.dp),
                shape = Shapes.medium
            ) {
                Text(text = leftText, fontSize = 20.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun TimePicker(getTime: (String) -> Unit) {
    var hour = "00"
    var minute = "00"
    var second = "00"

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Hour", modifier = Modifier.padding(start = 8.dp))
        Text(text = "Minute", modifier = Modifier.padding(start = 16.dp))
        Text(text = "Second")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(0.15f),
            factory = { context ->
                NumberPicker(context).apply {
                    setOnValueChangedListener { numberPicker, i, i2 ->
                        hour = if (numberPicker.value.toString().toInt() < 10) {
                            "0" + numberPicker.value.toString()
                        }else if (numberPicker.value.toString().toInt() == 0){
                            "00"
                        } else{
                            numberPicker.value.toString()
                        }
                        getTime.invoke("$hour:$minute:$second")

                    }
                    minValue = 0
                    maxValue = 99
                }
            }
        )

        Text(text = ":", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        AndroidView(
            modifier = Modifier.fillMaxWidth(0.175f),
            factory = { context ->
                NumberPicker(context).apply {
                    setOnValueChangedListener { numberPicker, i, i2 ->
                        minute = if (numberPicker.value.toString().toInt() < 10) {
                            "0" + numberPicker.value.toString()
                        } else if (numberPicker.value.toString().toInt() == 0){
                            "00"
                        }else {
                            numberPicker.value.toString()
                        }
                        getTime.invoke("$hour:$minute:$second")
                    }
                    minValue = 0
                    maxValue = 59
                }
            }
        )

        Text(text = ":", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        AndroidView(
            modifier = Modifier.fillMaxWidth(0.2f),
            factory = { context ->
                NumberPicker(context).apply {
                    setOnValueChangedListener { numberPicker, i, i2 ->
                        second = if (numberPicker.value.toString().toInt() < 10) {
                            "0" + numberPicker.value.toString()
                        } else if (numberPicker.value.toString().toInt() == 0){
                            "00"
                        }else {
                            numberPicker.value.toString()
                        }
                        getTime.invoke("$hour:$minute:$second")
                    }
                    minValue = 0
                    maxValue = 59
                }
            }
        )
    }

}

@Composable
fun CircleSlider(progress: Float, timer: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(42.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(text = timer,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.61f),
            color = BlueDark,
            fontSize = 40.sp
        )


        CircularProgressIndicator(
            progress = progress,
            color = BlueDark,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            strokeWidth = 8.dp
        )

    }


}
