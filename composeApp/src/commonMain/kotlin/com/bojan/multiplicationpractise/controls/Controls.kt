package com.bojan.multiplicationpractise.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(text: String, onClick: () -> Unit, background: Color = MaterialTheme.colors.primary) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(RoundedCornerShape(45.dp))
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            fontSize = TextUnit(32.0f, TextUnitType.Sp),
            lineHeight = TextUnit(32.0f, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 0.dp)
        )
    }
}