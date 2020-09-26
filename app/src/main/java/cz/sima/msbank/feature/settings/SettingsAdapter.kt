package cz.sima.msbank.feature.settings

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseRecyclerAdapter
import cz.sima.msbank.base.DataBindingViewHolder
import cz.sima.msbank.feature.settings.model.SettingsDiffUtil
import cz.sima.msbank.feature.settings.model.SettingsHeader
import cz.sima.msbank.feature.settings.model.SettingsItem
import cz.sima.msbank.feature.settings.model.SettingsItemType

/**
 * Created by Michal Šíma on 26.09.2020.
 */
class SettingsAdapter(settingsViewModel: SettingsViewModel) :
    BaseRecyclerAdapter<SettingsItem>(settingsViewModel, SettingsDiffUtil) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getItemType().viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<SettingsItem> {

        return when (SettingsItemType.fromInt(viewType)) {
            SettingsItemType.ITEM -> SettingsItemViewHolder(
                getViewDataBinding(
                    parent,
                    R.layout.item_settings_item
                )
            )
            SettingsItemType.HEADER -> SettingsHeaderViewHolder(
                getViewDataBinding(
                    parent,
                    R.layout.item_settings_header
                )
            )
        }
    }

    inner class SettingsHeaderViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<SettingsHeader>(
            binding
        )

    inner class SettingsItemViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<SettingsHeader>(
            binding
        )
}
