package com.generic.medicine.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.generic.medicine.R
import com.generic.medicine.com.generic.medicine.utils.Utils
import com.generic.medicine.model.SlideImage
import java.io.IOException
import java.io.InputStream

/**
 * Create SlideImage Adapter
 * By HARDIK PARSANIA
 * https://demonuts.com/kotlin-image-slider/
 */
class SlidingImageAdapter(private val context: Context, private val imageArray: ArrayList<SlideImage>): PagerAdapter() {

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout = LayoutInflater.from(context).inflate(R.layout.sliding_images_layout, container, false)
        val imageView:ImageView = imageLayout.findViewById(R.id.slideImage)
        imageArray[position].image.let {
            imageView.setImageBitmap(Utils.getBitmapFromAssets(context, imageArray[position].image))
        }

        container.addView(imageLayout, 0)

        return imageLayout
    }

    override fun getCount(): Int {
        return imageArray.size
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }
}