package com.papb.projectakhirandroid.presentation.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.papb.projectakhirandroid.R
import com.papb.projectakhirandroid.domain.model.ProductItem
import com.papb.projectakhirandroid.presentation.common.SpacerDividerContent
import com.papb.projectakhirandroid.presentation.component.RatingBar
import com.papb.projectakhirandroid.ui.theme.*
import com.papb.projectakhirandroid.utils.showToastShort

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val mContext = LocalContext.current
    val selectedProduct by detailViewModel.selectedProduct.collectAsState()
    var quantity by remember { mutableStateOf(1) }

    Scaffold { padding ->
        Column {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
                    .padding(padding)
            ) {
                selectedProduct?.let { productItem ->
                    DetailContentImageHeader(productItem = productItem)

                    Spacer(modifier = Modifier.height(DIMENS_24dp))

                    DetailContentDescription(productItem = productItem) { newQuantity ->
                        quantity = newQuantity
                    }
                }
            }

            Column {
                selectedProduct?.let {
                    DetailButtonAddCart(
                        productItem = it,
                        quantity = quantity,
                        onClickToCart = { productItem, qty ->
                            mContext.showToastShort("Berhasil Masuk Keranjang: ${productItem.title} ($qty item)")
                            // Update quantity sebelum masuk cart
                            detailViewModel.addCart(productItem.copy(isCart = true, quantity = qty))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailContentImageHeader(
    productItem: ProductItem
) {
    Card(
        shape = RoundedCornerShape(bottomEnd = DIMENS_24dp, bottomStart = DIMENS_24dp),
        backgroundColor = GrayBackground,
        modifier = Modifier
            .blur(DIMENS_1dp)
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = productItem.image),
            contentDescription = stringResource(id = R.string.image_product),
            modifier = Modifier.height(DIMENS_353dp)
        )
    }
}

@Composable
fun DetailContentDescription(
    modifier: Modifier = Modifier,
    productItem: ProductItem,
    onQuantityChange: (Int) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    Column(
        modifier = modifier.padding(start = DIMENS_16dp, end = DIMENS_16dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = productItem.title,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = TEXT_SIZE_24sp
                )

                Spacer(modifier = Modifier.height(DIMENS_6dp))

                Text(
                    text = productItem.unit,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = GraySecondTextColor,
                    fontSize = TEXT_SIZE_12sp,
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite_border),
                contentDescription = stringResource(R.string.image_favorite),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(DIMENS_8dp))

        // Price and Quantity Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Quantity Controller
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (quantity > 1) {
                            quantity--
                            onQuantityChange(quantity)
                        }
                    }
                ) {
                    Text(
                        text = "-",
                        fontSize = TEXT_SIZE_24sp,
                        color = if (quantity > 1) Green else GraySecondTextColor
                    )
                }

                Card(
                    shape = RoundedCornerShape(DIMENS_12dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GrayBorderStroke),
                    modifier = Modifier.padding(horizontal = DIMENS_8dp)
                ) {
                    Text(
                        text = quantity.toString(),
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = TEXT_SIZE_18sp,
                        color = Black, // Changed to Black
                        modifier = Modifier.padding(horizontal = DIMENS_16dp, vertical = DIMENS_8dp)
                    )
                }

                IconButton(
                    onClick = {
                        quantity++
                        onQuantityChange(quantity)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = Green
                    )
                }
            }

            Text(
                text = "Rp ${(productItem.price * quantity).toInt()}",
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black,
                fontSize = TEXT_SIZE_24sp
            )
        }

        SpacerDividerContent()

        Text(
            text = stringResource(R.string.product_detail),
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Black,
            fontSize = TEXT_SIZE_16sp,
        )

        Spacer(modifier = Modifier.height(DIMENS_8dp))

        Text(
            text = productItem.description,
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.Medium,
            color = GraySecondTextColor,
            fontSize = TEXT_SIZE_12sp,
        )

        Spacer(modifier = Modifier.height(DIMENS_16dp))
        SpacerDividerContent()

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.nutritions),
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                fontSize = TEXT_SIZE_16sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            Card(
                shape = RoundedCornerShape(DIMENS_6dp),
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = productItem.nutritions,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = GraySecondTextColor,
                    fontSize = TEXT_SIZE_10sp,
                    modifier = Modifier
                        .background(color = GrayBackgroundSecond)
                        .padding(DIMENS_4dp)
                )
            }

            Spacer(modifier = Modifier.width(DIMENS_8dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.arrow_right)
            )
        }

        SpacerDividerContent()

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.review),
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                fontSize = TEXT_SIZE_16sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            RatingBar(rating = productItem.review)

            Spacer(modifier = Modifier.width(DIMENS_8dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.arrow_right)
            )
        }

    }
}

@Composable
fun DetailButtonAddCart(
    modifier: Modifier = Modifier,
    productItem: ProductItem,
    quantity: Int,
    onClickToCart: (ProductItem, Int) -> Unit
) {
    Button(
        shape = RoundedCornerShape(DIMENS_24dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Green),
        modifier = modifier
            .fillMaxWidth()
            .padding(DIMENS_16dp),
        onClick = { onClickToCart.invoke(productItem, quantity) }
    ) {
        Text(
            text = stringResource(R.string.add_to_cart),
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = TEXT_SIZE_18sp,
            color = Color.White,
            modifier = Modifier.padding(top = DIMENS_8dp, bottom = DIMENS_8dp)
        )
    }
}


@Preview
@Composable
fun DetailScreenImageHeaderPreview() {
    DetailContentImageHeader(
        ProductItem(
            id = 1,
            title = "Organic Bananas",
            description = "Apples are nutritious. Apples may be good for weight loss. apples may be good for your heart. As part of a healtful and varied diet.",
            image = R.drawable.product2,
            unit = "7pcs, Priceg",
            price = 4.99,
            nutritions = "100gr",
            review = 4.0
        )
    )
}

@Preview
@Composable
fun DetailContentDescriptionPreview() {
    DetailContentDescription(
        productItem = ProductItem(
            id = 1,
            title = "Organic Bananas",
            description = "Apples are nutritious. Apples may be good for weight loss. apples may be good for your heart. As part of a healtful and varied diet.",
            image = R.drawable.product2,
            unit = "7pcs, Priceg",
            price = 4.99,
            nutritions = "100gr",
            review = 4.0
        ),
        onQuantityChange = {}
    )
}