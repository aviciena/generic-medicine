package com.generic.medicine.com.generic.medicine.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.io.InputStream

class Utils {
    companion object {
        /**
         * Create Bitmap image from assets
         * @param context as Context
         * @param fileName as String
         */
        fun getBitmapFromAssets(context: Context, fileName: String): Bitmap {
            val assetManager = context.assets
            var inputStream: InputStream? = null
            try {
                inputStream = assetManager.open("images/$fileName")
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return BitmapFactory.decodeStream(inputStream)
        }
    }
}