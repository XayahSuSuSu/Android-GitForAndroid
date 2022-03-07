package com.xayah.gitforandroid.fragment.home

import androidx.lifecycle.ViewModel
import com.topjohnwu.superuser.ShellUtils

class HomeViewModel : ViewModel() {
    val gitVersion = ShellUtils.fastCmd("git --version").replace("git version ", "")
}