package ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled() = false
}