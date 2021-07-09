package com.csows.sweep.models

data class ResourceMaterial(val ResourceID: Int, val ResourceMaterial: String, val FileType:String) {
    override fun toString(): String {
        return ResourceMaterial
    }
}