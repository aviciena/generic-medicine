package com.generic.medicine.model

import java.io.Serializable

data class Medicine (
    var name: String = "",
    var description:String = "",
    var type:String = "",
    var category:String = "",
    var advantage:List<String>?,
    var consumed_by:String = "",
    var ingredients:String = "",
    var shape:String = "",
    var images:List<String>?,
    var link:String = ""
) : Serializable