package com.ashrafovtaghi.tracker_presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.ashrafovtaghi.core_ui.LocalSpacing

@Composable
fun UnitDisplay(
    amount: Int,
    unit: String,
    modifier: Modifier = Modifier,
    amountSize: TextUnit = 20.sp,
    amountColor: Color = MaterialTheme.colors.onBackground,
    unitTextSize: TextUnit = 14.sp,
    unitTextColor: Color = MaterialTheme.colors.onBackground
) {
    val spacing = LocalSpacing.current
    Row(modifier){
        Text(
            text = amount.toString(),
            style = MaterialTheme.typography.h1,
            fontSize = amountSize,
            color = amountColor,
            modifier = Modifier.alignBy(LastBaseline)
        )
        Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
        Text(
            text = unit.toString(),
            style = MaterialTheme.typography.body1,
            fontSize = unitTextSize,
            color = unitTextColor,
            modifier = Modifier.alignBy(LastBaseline)
        )
    }
}

@Composable
@Preview
fun ShowUnit(){
    UnitDisplay(amount = 10, unit ="kg")
}