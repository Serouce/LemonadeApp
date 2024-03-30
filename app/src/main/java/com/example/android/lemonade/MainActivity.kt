package com.example.android.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.android.lemonade.ui.theme.LemonadeTheme
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()

            }
        }
    }
}

@Composable
fun LemonadeApp() {
    var currentStage by remember { mutableStateOf(Stage.Tree) }
    var tapsCount by remember { mutableStateOf(0) }
    var requiredTaps by remember { mutableStateOf((2..4).random()) }

    when (currentStage) {
        Stage.Tree -> {
            InteractiveImage(
                image = R.drawable.lemon_tree,
                description = R.string.lemon_tree,
                text = R.string.select_lemon,
                onClick = {
                    currentStage = Stage.Lemon
                    tapsCount = 0
                }
            )
        }
        Stage.Lemon -> {
            InteractiveImage(
                image = R.drawable.lemon_squeeze,
                description = R.string.lemon,
                text = R.string.squeeze_lemon,
                onClick = {
                    tapsCount++
                    if (tapsCount >= requiredTaps) {
                        currentStage = Stage.Lemonade
                        requiredTaps = (2..4).random()
                    }
                }
            )
            }
        Stage.Lemonade -> {
            InteractiveImage(
                image = R.drawable.lemon_drink,
                description = R.string.glass_of_lemonade,
                text = R.string.drink_it,
                onClick = {
                    currentStage = Stage.EmptyGlass
                }
                )
            }
        Stage.EmptyGlass -> {
            InteractiveImage(
                image = R.drawable.lemon_restart,
                description = R.string.empty_glass,
                text = R.string.start_again,
                onClick = {
                    currentStage = Stage.Tree
                }
                )

            }
        }
    }


@Composable
fun InteractiveImage(image: Int, description: Int, text: Int, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = stringResource(id = description),
                    modifier = Modifier
                        .size(224.dp)

                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = stringResource(id = text), textAlign = TextAlign.Center)
            }

        }
    }
}



enum class Stage {
    Tree, Lemon, Lemonade, EmptyGlass
}