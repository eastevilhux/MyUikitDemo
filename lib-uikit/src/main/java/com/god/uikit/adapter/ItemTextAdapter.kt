package com.god.uikit.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.god.uikit.R
import com.god.uikit.BR
import com.god.uikit.commons.BaseListAdapter
import com.god.uikit.commons.Constants
import com.god.uikit.commons.Constants.Companion.IMAGE_TYPE_RESOURCE
import com.god.uikit.commons.Constants.Companion.TEXT_TYPE_TEXT
import com.god.uikit.databinding.ListItemTextBinding
import com.god.uikit.entity.ItemText
import com.god.uikit.presenter.ItemTextPresenter

class ItemTextAdapter(list: MutableList<ItemText>?, context: Context) :
    BaseListAdapter<ListItemTextBinding, ItemText>(list, context, BR.itemText) {

    private var presenter : ItemTextPresenter? = null;

    override fun getLayoutRes(): Int {
        return R.layout.list_item_text;
    }

    override fun setBean(t: ItemText?) {
        dataBinding.itemText = t;
    }

    override fun setPresenter(dataBinding: ListItemTextBinding) {
        super.setPresenter(dataBinding)
        dataBinding.presenter = presenter;
    }

    fun setPresenter(presenter : ItemTextPresenter){
        this.presenter = presenter;
    }

    override fun setView(dataBinding: ListItemTextBinding, bean: ItemText) {
        super.setView(dataBinding, bean)
        if(bean.textType == Constants.TEXT_TYPE_TEXT){
            dataBinding.tvText.text = bean.text;
        }else{
            dataBinding.tvText.setText(bean.textResId);
        }
        if(bean.selectedImageType == Constants.IMAGE_TYPE_RESOURCE){
            dataBinding.ivSelected.setImageResource(bean.selectedResId);
        }else{
            Glide.with(dataBinding.ivSelected)
                .load(bean.selectedResUrl)
                .apply(
                    RequestOptions()
                    .placeholder(R.drawable.icon_selected)
                    .error(R.drawable.icon_selected))
                .into(dataBinding.ivSelected)
        }
    }
}