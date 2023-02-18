package com.tg.vloan.net

/**
 * Created by frcx-hb on 2022/12/2 11:00.
 */
object ApiPath {
//    const val getBaseUrl = "http://nweaxom.cn/d3019.json/" //
    const val getBaseUrl = "http://gtm-cn-x0r337dx50o.gtm-a3b4.com/" //
    const val getBaseBacUrl = "https://jsonrequest-by.oss-cn-hangzhou.aliyuncs.com/d3019.json"
    const val pushUrl = "dagejietiaobei";
    const val isRenewUrl = "isRenewUrl" //是否从新获取json文件域名 (0是不用重新获取，1是要重新获取)
    const val getIsCheck = "getIsCheck" //获取相关配置
    const val getAppInfo = "getAppInfo" //获取协议的公司信息以及邮箱
    const val sendCode = "send.code" //发送验证码接口
    const val login = "login" //登录接口
    const val download = "download" //计算用户下载次数  每个APP只调用一次
    const val advertList = "advert.list" //产品列表
    const val advertRand = "advert.rand" //广告列表（如果广告接口没有数据 就取advert.list产品接口第一个）
    const val click = "click" //上报接口（点击广告列表中的数据都需要上报）
}