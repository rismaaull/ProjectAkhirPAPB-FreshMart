package com.papb.projectakhirandroid.presentation.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.papb.projectakhirandroid.presentation.common.content.ListContentCart
import com.papb.projectakhirandroid.ui.theme.Black
import com.papb.projectakhirandroid.ui.theme.DIMENS_16dp
import com.papb.projectakhirandroid.ui.theme.GilroyFontFamily
import com.papb.projectakhirandroid.ui.theme.TEXT_SIZE_18sp
import com.papb.projectakhirandroid.R


@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val productCartList by cartViewModel.productCartList.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = DIMENS_16dp),
            text = stringResource(R.string.my_cart),
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = TEXT_SIZE_18sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(DIMENS_16dp))

        ListContentCart(
            cartProducts = productCartList,
            onClickDeleteCart = { productItem ->
                cartViewModel.deleteCart(productItem.copy(isCart = false))
            }
        )
    }
}
