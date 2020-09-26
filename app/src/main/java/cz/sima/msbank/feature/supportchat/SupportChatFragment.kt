package cz.sima.msbank.feature.supportchat

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentSupportChatBinding

/**
 * Created by Michal Šíma on 26.09.2020.
 */
class SupportChatFragment :
    BaseVMFragment<FragmentSupportChatBinding, SupportChatViewModel>(SupportChatViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_support_chat
    override fun hasBottomNav() = false
}
