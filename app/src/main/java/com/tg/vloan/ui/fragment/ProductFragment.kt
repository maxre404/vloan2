package com.tg.vloan.ui.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.tg.vloan.R
import com.tg.vloan.base.BaseFragment
import com.tg.vloan.bean.AdvertBean
import com.tg.vloan.databinding.FragmentProductBinding
import com.tg.vloan.databinding.ItemAdvertBinding
import com.tg.vloan.dto.ClickRequest
import com.tg.vloan.ui.activity.WebViewActivity
import com.tg.vloan.utils.EmptyUtils
import com.tg.vloan.utils.ImageLoadUtils
import com.tg.vloan.viewmodel.HomeViewModel

class ProductFragment:BaseFragment<FragmentProductBinding>() {
    var viewModel: HomeViewModel?=null
    override fun generateBinding(): ViewBinding? {
        return FragmentProductBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun initData() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        viewModel?.loadData()
    }

    override fun initSubscription() {
        viewModel?.advertListLiveData?.observe(this){response->
            if (response?.isSuccess==true){
                binding?.rcvAdvert?.linear()?.setup {
                    addType<AdvertBean>(R.layout.item_advert)
                    onBind {
                        var item: AdvertBean = getModel()
                        var itemBinding = getBinding<ItemAdvertBinding>()
                        ImageLoadUtils.loadImage(itemBinding.ivAdvert, item.icon)
                        itemBinding.tvAdvert.setText(item.title)
                        itemBinding.tvMaxPrice.setText(item.maxPrice)
                        itemBinding.tvRate.setText(EmptyUtils.formatString(item.dayRate) + ("%"))
                        itemBinding!!.root.setOnClickListener { v ->
                            val clickRequest = ClickRequest(item.id, "1")
                            viewModel?.click(clickRequest)
                            WebViewActivity.gotoActivity(context, item.url, item.title,item.isAudit)
                        }
                    }
                }?.models = response.data
            }
        }
    }
     override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }
}