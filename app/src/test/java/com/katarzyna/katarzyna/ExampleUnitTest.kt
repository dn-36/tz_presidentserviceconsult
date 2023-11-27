package com.katarzyna.katarzyna

import com.presidentserviceconsult.dimaz.black.cloaca.init_sdk.STATUS
import com.presidentserviceconsult.dimaz.black.cloaca.logic_cloaca.Cloaca
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test_format_have_namign() {
        val test =  Cloaca.logic(
            "https://domainname.com/1238sdfu8?sub1={wp1}&sub2={wp2}",
                    true,
            dataAttributionMap =  mapOf(
                "is_mobile_data_terms_signed" to "20.02.2022",
                "af_channel" to "true",
                "install_time" to "235.1_s",
                "af_status" to "organic",
                "campaign" to "test1_test2_test3_test4_test5_test6"
            ),
            goToWhite =  {},
            goToBlack =  {}
        )
        Assert.assertEquals(
            test.url,
            "https://domainname.com/1238sdfu8?" +
                    "sub1=test1" +
                    "&sub2=test2" +
                    "&sub3=test3" +
                    "&sub4=test4" +
                    "&sub5=test5" +
                    "&sub6=test6"
        )
    }

    @Test
    fun test_format_have_not_namign() {

        val test =  Cloaca.logic(
            "https://domainname.com/1238sdfu8?sub1={wp1}&sub2={wp2}",
            true,
            dataAttributionMap =  mapOf("" to ""),
            goToWhite =  {},
            goToBlack =  {}
        )
        Assert.assertEquals(
            test.url,
            "https://domainname.com/1238sdfu8"
        )

    }

    @Test
    fun test_status_no_namign_state_false() {

        val test =  Cloaca.logic(
            "https://domainname.com/1238sdfu8",
            false,
            dataAttributionMap =  mapOf(
                "is_mobile_data_terms_signed" to "20.02.2022",
                "af_channel" to "true",
                "install_time" to "235.1_s",
                "af_status" to "organic",
            ),
            goToWhite =  {},
            goToBlack =  {}
        )
        Assert.assertEquals(
            test.status, STATUS.ToGame

        )
    }

    @Test
    fun test_status_have_namign_state_false() {

        val test = Cloaca.logic(
            "https://domainname.com/1238sdfu8",
            false,
            dataAttributionMap = mapOf(
                "is_mobile_data_terms_signed" to "20.02.2022",
                "af_channel" to "true",
                "install_time" to "235.1_s",
                "af_status" to "organic",
                "campaign" to "test1_test2_test3_test4_test5_test6"
            ),
            goToWhite =  {},
            goToBlack =  {}
        )
        Assert.assertEquals(
            test.status,
            STATUS.ToOfer
        )
    }

@Test
fun test_status_no_namign_state_true() {

    val test =  Cloaca.logic(
        "https://domainname.com/1238sdfu8",
        false,
        dataAttributionMap =  mapOf("" to ""),
        goToWhite =  {},
        goToBlack =  {}
    )
    Assert.assertEquals(
        test.status, STATUS.ToGame

    )
}

@Test
fun test_status_have_namign_state_true() {

    val test = Cloaca.logic(
        "https://domainname.com/1238sdfu8",
        false,
        dataAttributionMap = mapOf(
            "is_mobile_data_terms_signed" to "20.02.2022",
            "af_channel" to "true",
            "install_time" to "235.1_s",
            "af_status" to "organic",
            "campaign" to "test1_test2_test3_test4_test5_test6"
        ),
        goToWhite =  {},
        goToBlack =  {}
    )
    Assert.assertEquals(
        test.status,
        STATUS.ToGame
    )
}}