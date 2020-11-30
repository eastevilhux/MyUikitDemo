package com.god.uikit.entity

import com.god.uikit.R
import com.god.uikit.commons.Constants.Companion.IMAGE_TYPE_RESOURCE
import com.god.uikit.commons.Constants.Companion.TEXT_TYPE_RESOUCE
import com.god.uikit.commons.Constants.Companion.TEXT_TYPE_TEXT

class ItemText private constructor(builder:Builder){


    var text : String? = null
        private set

    var textResId : Int = R.string.default_text
        private set

    var textType : Int = TEXT_TYPE_RESOUCE
        private set

    var selected : Boolean = false

    var selectedResId : Int = R.drawable.icon_selected
        private set

    var selectedResUrl : String? = null
        private set

    var selectedImageType : Int = IMAGE_TYPE_RESOURCE
        private set

    init {
        text = builder.text;
        textResId = builder.textResId;
        selected = builder.selected;
        selectedResId = builder.selectedResId;
        selectedResUrl = builder.selectedResUrl;
        selectedImageType = builder.selectedImageType;
        textType = builder.textType;
    }


    class Builder{
        var text : String? = null
        var textResId : Int = R.string.default_text
        var selected : Boolean = false
        var selectedResId : Int = R.drawable.icon_selected
        var selectedResUrl : String? = null
        var selectedImageType : Int = IMAGE_TYPE_RESOURCE
        var textType : Int = TEXT_TYPE_RESOUCE;

        fun text(text:String): Builder {
            this.text = text;
            this.textType = TEXT_TYPE_TEXT;
            return this;
        }

        fun textResId(textResId:Int): Builder {
            this.textResId = textResId;
            this.textType = TEXT_TYPE_RESOUCE
            return this;
        }

        fun selected(selected:Boolean): Builder {
            this.selected = selected;
            return this;
        }

        fun selectedResId(selectedResId:Int): Builder {
            this.selectedResId = selectedResId;
            return this;
        }

        fun selectedResUrl(selectedResUrl:String): Builder {
            this.selectedResUrl = selectedResUrl;
            return this;
        }

        fun selectedImageType(selectedImageType:Int): Builder {
            this.selectedImageType = selectedImageType;
            return this;
        }

        fun builder(): ItemText {
            return ItemText(this);
        }
    }
}