package com.parradosorel.todo.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

// Material's arrow_back and add, drawn here so the app does not need the icons library for two icons.

val BackArrow: ImageVector = materialIcon("BackArrow") {
    moveTo(20f, 11f)
    horizontalLineTo(7.83f)
    lineToRelative(5.59f, -5.59f)
    lineTo(12f, 4f)
    lineToRelative(-8f, 8f)
    lineToRelative(8f, 8f)
    lineToRelative(1.41f, -1.41f)
    lineTo(7.83f, 13f)
    horizontalLineTo(20f)
    close()
}

val Plus: ImageVector = materialIcon("Plus") {
    moveTo(19f, 13f)
    horizontalLineToRelative(-6f)
    verticalLineToRelative(6f)
    horizontalLineToRelative(-2f)
    verticalLineToRelative(-6f)
    horizontalLineTo(5f)
    verticalLineToRelative(-2f)
    horizontalLineToRelative(6f)
    verticalLineTo(5f)
    horizontalLineToRelative(2f)
    verticalLineToRelative(6f)
    horizontalLineToRelative(6f)
    close()
}

private fun materialIcon(
    name: String,
    pathBuilder: androidx.compose.ui.graphics.vector.PathBuilder.() -> Unit,
): ImageVector = ImageVector.Builder(
    name = name,
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).apply { path(fill = SolidColor(Color.Black), pathBuilder = pathBuilder) }.build()
