package com.omotesando.missingpiece

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omotesando.missingpiece.ui.theme.Blue10
import com.omotesando.missingpiece.ui.theme.Orange10


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    borderColor: Color = Orange10,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    newWidth: Int,
    newHeight: Int,
    newFontSize: Int
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Blue10),
        border = BorderStroke(2.dp, color = borderColor),
        modifier = modifier
            .width(newWidth.dp)
            .height(newHeight.dp),
    ) {
            Text(
                text = text,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = newFontSize.sp,
                textAlign = TextAlign.Center
            )
    }
}
