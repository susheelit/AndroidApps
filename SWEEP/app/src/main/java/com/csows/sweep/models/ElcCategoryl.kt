package com.csows.sweep.models

data class ElcCategoryl(
    val ElcCategoryID: Int,
    val Officercategory: String
) {
    override fun toString(): String {
        return Officercategory
    }
}