package com.generic.medicine.activity

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.generic.medicine.R
import com.generic.medicine.adapter.SlidingImageAdapter
import com.generic.medicine.model.Medicine
import com.generic.medicine.model.SlideImage
import com.viewpagerindicator.CirclePageIndicator

class MedicineDetailsActivity: AppCompatActivity() {
    companion object {
        const val DETAILS_KEY = "Details_KEY"
    }

    private lateinit var mPager: ViewPager
    private lateinit var indicator: CirclePageIndicator
    private lateinit var productName: TextView
    private lateinit var description: TextView
    private lateinit var descriptionText: TextView
    private lateinit var productType: TextView
    private lateinit var productCategory: TextView
    private lateinit var productAdvantage: TextView
    private lateinit var productConsumedBy: TextView
    private lateinit var productIngredients: TextView
    private lateinit var productShape: TextView
    private lateinit var moreDetails: TextView
    private lateinit var ingredientsView: TableRow
    private lateinit var advantagesView: TableRow
    private lateinit var typeView: TableRow
    private lateinit var categoryView: TableRow
    private lateinit var noImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_page)

        mPager = findViewById(R.id.viewPager)
        indicator = findViewById(R.id.pageIndicator)

        productName = findViewById(R.id.productName)
        description = findViewById(R.id.productDescription)
        descriptionText = findViewById(R.id.descriptionText)
        productType = findViewById(R.id.productType)
        productCategory = findViewById(R.id.productCategory)
        productAdvantage = findViewById(R.id.productAdvantage)
        productConsumedBy = findViewById(R.id.productConsumedBy)
        productIngredients = findViewById(R.id.productIngredients)
        productShape = findViewById(R.id.productShape)
        moreDetails = findViewById(R.id.productMoreDetails)
        ingredientsView = findViewById(R.id.ingredientsView)
        advantagesView = findViewById(R.id.advantagesView)
        typeView = findViewById(R.id.typeView)
        categoryView = findViewById(R.id.categoryView)
        noImage = findViewById(R.id.noImage)

        descriptionText.paintFlags = descriptionText.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val medicine = intent.getSerializableExtra(DETAILS_KEY) as Medicine

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (supportActionBar as ActionBar).title = medicine.name
        }

        if (!medicine.type.equals("-", ignoreCase = true)) {
            typeView.visibility = View.VISIBLE
        }

        if (!medicine.ingredients.equals("-", ignoreCase = true)) {
            ingredientsView.visibility = View.VISIBLE
        }

        if (!medicine.category.equals("-", ignoreCase = true)) {
            categoryView.visibility = View.VISIBLE
        }

        medicine.images?.let {
            if (it.size === 1) {
                indicator.visibility = View.GONE
            }
        }

        initSlideImages(medicine)
        setDataView(medicine)
    }

    private fun setDataView(medicine: Medicine) {
        productName.text = medicine.name
        description.text = medicine.description

        productType.text = medicine.type
        productCategory.text = medicine.category

        val builder = StringBuilder()

        medicine.advantage?.let {
            if (it.isNotEmpty()) {
                for (advantage in it.iterator()) {
                    val currentAdvantage: String = if (builder.isNotEmpty()) "\n\n".plus(advantage) else advantage
                    builder.append(currentAdvantage)
                }
            }
        }

        if (builder.isNotEmpty()) {
            productAdvantage.text = builder.toString()
            advantagesView.visibility = View.VISIBLE
        }

        productConsumedBy.text = medicine.consumed_by
        productIngredients.text = medicine.ingredients
        productShape.text = medicine.shape

        moreDetails.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(medicine.link)
            startActivity(openURL)
        }

        moreDetails.paintFlags = moreDetails.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initSlideImages(medicine: Medicine) {
        val imageArray = ArrayList<SlideImage>()

        medicine.images?.let {
            if (it.isNotEmpty()) {
                for (i in it.indices) {
                    val imageModel = SlideImage()
                    imageModel.image = it[i]
                    imageArray.add(imageModel)
                }
            }
        }

        mPager.adapter = SlidingImageAdapter(this, imageArray)
        indicator.setViewPager(mPager)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}