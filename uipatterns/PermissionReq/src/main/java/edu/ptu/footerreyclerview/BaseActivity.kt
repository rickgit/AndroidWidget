package edu.ptu.footerreyclerview

import android.support.v4.app.FragmentActivity

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2018/1/8.
 */
open class BaseActivity : FragmentActivity(){
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        MPermissionUtils.onRequestPermissionsResult(this,requestCode,permissions,grantResults)
    }

}
