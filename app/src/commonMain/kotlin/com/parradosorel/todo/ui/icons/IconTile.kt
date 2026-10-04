package com.parradosorel.todo.ui.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parradosorel.todo.domain.model.ListIcon
import org.jetbrains.compose.resources.stringResource

/** A list's icon: its emoji on a rounded tinted square [size] wide. */
@Composable
fun IconTile(icon: ListIcon, modifier: Modifier = Modifier, size: Dp = 48.dp) {
    val label = stringResource(icon.label)
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size / 4))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .semantics { contentDescription = label },
        contentAlignment = Alignment.Center,
    ) {
        Text(icon.emoji, fontSize = (size.value * 0.5f).sp)
    }
}
