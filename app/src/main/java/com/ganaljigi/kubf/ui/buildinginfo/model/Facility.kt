package com.ganaljigi.kubf.ui.buildinginfo.model

import androidx.annotation.DrawableRes
import com.ganalijigi.kubf.R

enum class Facility(val label: String, @DrawableRes val iconResId:Int){
    CAFE("카페", R.drawable.ic_feature_cafe),
    CONV("편의점", R.drawable.ic_feature_conv),
    PRINT("복사실", R.drawable.ic_feature_print),
    REST("휴게실", R.drawable.ic_feature_rest),
    KCUBE("K-CUBE", R.drawable.ic_feature_kcube),
    KHUB("K-Hub",R.drawable.ic_feature_kcube),
    SERVICE("IT-서비스센터", R.drawable.ic_feature_itser),
    PARK("주차장", R.drawable.ic_feature_park),
    BANK("은행",R.drawable.ic_feature_bank),
    ELEV("엘리베이터",R.drawable.ic_feature_elevator),
    POST("우체국",R.drawable.ic_feature_post),
    TOILET("장애인화장실",R.drawable.ic_feature_disable_toilet),
    CULF("문화시설",R.drawable.ic_feature_culture),
    STORE("복지매장",R.drawable.ic_feature_welfare),
    RES("식당",R.drawable.ic_feature_restaurant)
}