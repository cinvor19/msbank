package cz.sima.msbank.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Michal Šíma on 23.06.2020.
 */
@JsonClass(generateAdapter = true)
data class DashboardResponse(
    @Json(name = "accounts")
    val accounts: List<DashBoardAccountApi>,
    @Json(name = "announcements")
    val announcements: List<DashBoardAnnouncementApi>,
    @Json(name = "promos")
    val promos: List<DashBoardPromoApi>
)

@JsonClass(generateAdapter = true)
data class DashBoardAccountApi(
    @Json(name = "id")
    val id: String,
    @Json(name = "accountNumber")
    val accountNumber: String,
    @Json(name = "balance")
    val balance: String,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class DashBoardAnnouncementApi(
    @Json(name = "id")
    val id: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "title")
    val title: String
)

@JsonClass(generateAdapter = true)
data class DashBoardPromoApi(
    @Json(name = "id")
    val id: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "title")
    val title: String
)

@JsonClass(generateAdapter = true)
data class TransactionResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "date")
    val date: String,
    @Json(name = "counterPart")
    val counterPart: String,
    @Json(name = "amount")
    val amount: String
)
