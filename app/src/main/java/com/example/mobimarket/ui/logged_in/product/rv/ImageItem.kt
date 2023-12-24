package com.example.mobimarket.ui.logged_in.product.rv

import android.net.Uri

sealed class ImageItem {

    data object AddImage : ImageItem()
    data class Image(val uri: Uri?) : ImageItem()
}