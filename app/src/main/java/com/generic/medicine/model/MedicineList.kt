package com.generic.medicine.model

data class MedicineDetails(
    var type:String = "",
    var list:List<Medicine> = arrayListOf()
)

data class MedicineList(
    var data:List<MedicineDetails> = arrayListOf()
)