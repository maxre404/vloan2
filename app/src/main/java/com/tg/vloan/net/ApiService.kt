package com.tg.vloan.net

import retrofit2.http.GET
import retrofit2.http.Url
import okhttp3.ResponseBody
import retrofit2.http.POST
import com.tg.vloan.net.ApiPath
import com.tg.vloan.bean.BaseResponse
import com.tg.vloan.bean.RenewUrlBean
import com.tg.vloan.bean.AppInfoBean
import com.tg.vloan.bean.IsCheckBean
import com.tg.vloan.dto.LoginRequest
import com.tg.vloan.bean.UserBean
import com.tg.vloan.dto.CodeRequest
import com.tg.vloan.bean.AdvertBean
import com.tg.vloan.dto.ClickRequest
import com.tg.vloan.dto.DownloadRequest
import io.reactivex.Observable
import retrofit2.http.Body

/**
 * Created
 */
interface ApiService {
    @GET
    suspend fun getBaseUrl(@Url url: String?): ResponseBody?
    @GET
    suspend fun getUrl(@Url url: String?): String?

    @get:POST(ApiPath.isRenewUrl)
    val isRenewUrl: Observable<BaseResponse<RenewUrlBean?>?>?

    @POST(ApiPath.getAppInfo)
    suspend fun getAppInfo(): BaseResponse<AppInfoBean?>?

    @get:POST(ApiPath.getIsCheck)
    val isCheck: Observable<BaseResponse<IsCheckBean?>?>?

    @POST(ApiPath.login)
    fun login(@Body loginRequest: LoginRequest?): Observable<BaseResponse<UserBean?>?>?

    @POST(ApiPath.sendCode)
    fun sendCode(@Body codeRequest: CodeRequest?): Observable<BaseResponse<RenewUrlBean?>?>?

    @get:POST(ApiPath.advertList)
    val advertList: Observable<BaseResponse<List<AdvertBean?>?>?>?

    @POST(ApiPath.click)
    fun click(@Body clickRequest: ClickRequest?): Observable<ResponseBody?>?

    @POST(ApiPath.advertRand)
    fun advertRand(): Observable<BaseResponse<AdvertBean?>?>?

    @POST(ApiPath.download)
    fun download(@Body downloadRequest: DownloadRequest?): Observable<Void?>?
}