package cz.sima.msbank.feature.supportchatthreads

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentSupportChatThreadsBinding

/**
 * Created by Michal Šíma on 26.09.2020.
 */
class SupportChatThreadsFragment :
    BaseVMFragment<FragmentSupportChatThreadsBinding, SupportChatThreadsViewModel>(SupportChatThreadsViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_support_chat_threads
    override fun hasBottomNav() = false
}
