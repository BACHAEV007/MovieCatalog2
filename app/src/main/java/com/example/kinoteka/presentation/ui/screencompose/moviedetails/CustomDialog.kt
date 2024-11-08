package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kinoteka.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    onInputTextChange: (String) -> Unit,
    onDismiss:()->Unit,
    onConfirm:(reviewText: String, rating: Float, anonymous: Boolean)->Unit,
    inputText: String = "",
    inputMark: Float = 5f
) {
    var body by remember { mutableStateOf(inputText) }
    var sliderValue by remember { mutableStateOf(inputMark) }
    var isFocusedBody by remember { mutableStateOf(false) }
    val bodyHint = "Текст отзыва"
    var checked by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.app_background)
            ),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .border(
                    1.dp,
                    color = colorResource(R.color.app_background),
                    shape = RoundedCornerShape(28.dp)
                )
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Добавить отзыв",
                    fontSize = 20.sp,
                    color = colorResource(R.color.white),
                )
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = "Оценка",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Выбранная оценка: ${sliderValue.toInt()}",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Slider(
                    value = sliderValue,
                    onValueChange = { newValue ->
                        sliderValue = newValue
                    },
                    valueRange = 0f..10f,
                    steps = 9,
                    colors = SliderDefaults.colors(
                        thumbColor = colorResource(R.color.average_gradient_color),
                        activeTrackColor = colorResource(R.color.average_gradient_color),
                        inactiveTrackColor = colorResource(R.color.not_selected_button)
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(height = 120.dp, width = 0.dp)
                        .background(
                            color = colorResource(R.color.not_selected_button),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    BasicTextField(
                        value = if (body.isEmpty() && !isFocusedBody) bodyHint else body,
                        onValueChange = { newValue -> body = newValue },
                        textStyle = TextStyle(
                            color = if (body.isEmpty() && !isFocusedBody) Color.Gray else Color.White,
                            fontSize = 16.sp,
                        ),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                isFocusedBody = focusState.isFocused
                                if (body.isEmpty() && !isFocusedBody) body = ""
                            }
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ){
                    Text(
                        text = "Анонимный отзыв",
                        fontSize = 14.sp,
                        color = colorResource(R.color.gray)
                    )
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorResource(R.color.white),
                            checkedTrackColor = colorResource(R.color.average_gradient_color),
                            uncheckedThumbColor = colorResource(R.color.white),
                            uncheckedTrackColor = colorResource(R.color.gray_faded),
                        )
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(modifier = Modifier.weight(1.75f))
                    TextButton(
                        onClick = { onConfirm(body, sliderValue, checked) },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFDF2800), Color(0xFFFF6633)),
                                    start = Offset.Zero,
                                    end = Offset.Infinite
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .weight(1.25f)
                    ) {
                        Text(
                            text = "Отправить",
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}