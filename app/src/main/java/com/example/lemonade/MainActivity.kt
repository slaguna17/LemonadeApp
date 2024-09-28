package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    val colorsBackground = listOf(Color(0xFFa1c4fd), Color(0xFFc2e9fb))
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                GradientBackgroundBrush(
                    isVerticalGradient = false,
                    colors = colorsBackground
                )
            ),
        color = Color.Transparent
    ) {
        Column (
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LemonTree(
                modifier = Modifier
            )
        }
    }
}

@Composable
fun LemonTree(modifier: Modifier = Modifier) {
    var state by remember {
        mutableStateOf(1)
    }
    val imageResource = when (state) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val textResource = when (state) {
        1 -> stringResource(id = R.string.lemon_tree_label)
        2 -> stringResource(id = R.string.Lemon)
        3 -> stringResource(id = R.string.Glass_of_lemonade)
        else -> stringResource(id = R.string.Empty_glass)
    }
    var needed by remember {
        mutableStateOf(1)
    }
    var counter = 0
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                when(state){
                    1 -> {
                        needed = (2..4).random()
                        state = 2
                    }
                    2 -> {
                        if(counter <= needed){
                            counter ++
                        } else {
                            state = 3
                            counter = 0
                        }
                    }
                    3 -> state = 4
                    else -> state = 1
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
//            shape = RoundedCornerShape(50), // = 50% percent
            // or shape = CircleShape
            modifier = modifier
                .background(Color(0xFFC3EDD3))
                .clip(RoundedCornerShape(15.dp))
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = imageResource.toString(),
                modifier = modifier.wrapContentSize()
            )
        }
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = textResource,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun GradientBackgroundBrush(isVerticalGradient: Boolean, colors: List<Color>): Brush {
    val endOffset = if(isVerticalGradient){
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }
    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset
    )
}

