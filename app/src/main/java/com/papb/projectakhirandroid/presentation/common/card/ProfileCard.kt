package com.papb.projectakhirandroid.presentation.common.card

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.papb.projectakhirandroid.R
import com.papb.projectakhirandroid.ui.theme.*

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    imageUri: Uri?
) {
    Row(
        modifier = modifier
            .padding(start = DIMENS_16dp, end = DIMENS_16dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(DIMENS_80dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageUri,
                    placeholder = painterResource(id = R.drawable.profileimage),
                    error = painterResource(id = R.drawable.profileimage)
                ),
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Reverted to Crop
            )
        }

        Spacer(modifier = Modifier.width(DIMENS_16dp))

        Column {
            Text(
                text = name,
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = TEXT_SIZE_18sp,
                color = Black
            )
            Text(
                text = email,
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = TEXT_SIZE_12sp,
                color = GraySecondTextColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    ProfileCard(name = "John Doe", email = "johndoe@example.com", imageUri = null)
}
