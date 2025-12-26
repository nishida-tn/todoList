package com.thalesnishida.todo.presetention.ui.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thalesnishida.todo.R

@Composable
fun UserAvatar(
    photoUri: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photoUri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(id = R.drawable.avatar_placeholder),
        error = painterResource(id = R.drawable.avatar_placeholder),
        contentDescription = "User Avatar",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(
                1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
    )
}

@Preview
@Composable
fun UserAvatarPreview() {
    UserAvatar(
        photoUri = null
    )
}