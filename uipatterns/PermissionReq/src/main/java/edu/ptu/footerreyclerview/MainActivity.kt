package edu.ptu.footerreyclerview

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.net.Uri
import android.widget.Toast


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv.adapter =object :Adapter<ViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
                return object :ViewHolder(  LayoutInflater.from(parent?.context).inflate(R.layout.item_block,null) ){}
            }

            override fun getItemCount(): Int {
                return 2
            }

            override fun getItemViewType(position: Int): Int {
                if (position==1)
                    return 1;
                return super.getItemViewType(position)
            }

            override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                holder?.itemView?.setBackgroundColor(0xff00ffcc.toInt())
            }
        }
        rv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                print(recyclerView.toString()+" 移动位置 "+dx+" :" +dy)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        permission_btn.setOnClickListener{
            MPermissionUtils.requestPermissionsResult(this@MainActivity,1, arrayOf(Manifest.permission.CALL_PHONE) ,object :MPermissionUtils.OnPermissionListener{
                override fun onNeverAskAgain() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                @SuppressLint("MissingPermission")
                override fun onPermissionGranted() {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    val intent = Intent(Intent.ACTION_CALL)
                    val data = Uri.parse("tel:13076950595")
                    intent.data = data
                    startActivity(intent)
                }

                override fun onPermissionDenied() {
                    Toast.makeText(this@MainActivity,"授权失败！",Toast.LENGTH_LONG).show() //To change body of created functions use File | Settings | File Templates.
                }

            })

        }
    }

}
