package edu.ptu.demo.test.gridview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ptu.demo.R;
import edu.ptu.demo.test.RecyclerActivity;
import edu.ptu.demo.test.utils.Utils;

/**
 * Created by anshu.wang on 2016/12/29.
 */

public class GridViewActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        RecyclerView rcv = (RecyclerView) findViewById(R.id.rcv);
        //GridLayout 3列
        GridLayoutManager mgr=new GridLayoutManager(this,3);
        rcv.setLayoutManager(mgr);
        rcv.setAdapter(new RecyclerView.Adapter() {
            public List datas = new ArrayList() {{
                for (int i = 0; i < 100; i++) {
                    add(" item " + i);
                }
            }};
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recycler_item, null);
                RecyclerActivity.ViewHolder vh = new RecyclerActivity.ViewHolder(view);
                return vh;
            }
            int color1 = 0xffaaccbb;
            int color2 = (Utils.generateBeautifulColor());
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                View convertView = viewHolder.itemView;
                if (position % 2 == 0)
                    convertView.setBackgroundColor(color1);
                else
                    convertView.setBackgroundColor(color2);
            }

            @Override
            public int getItemCount() {
                return datas.size();
            }
        });
        getMacFromArpCache("192.182.0.1");
    }
    /**
     * Try to extract a hardware MAC address from a given IP address using the
     * ARP cache (/proc/net/arp).<br>
     * <br>
     * We assume that the file has this structure:<br>
     * <br>
     * IP address       HW type     Flags       HW address            Mask     Device
     * 192.168.18.11    0x1         0x2         00:04:20:06:55:1a     *        eth0
     * 192.168.18.36    0x1         0x2         00:22:43:ab:2a:5b     *        eth0
     *
     * @param ip
     * @return the MAC from the ARP cache
     */
    public static String getMacFromArpCache(String ip) {
        if (ip == null)
            return null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                System.out.println("=++++>"+line);
                if (splitted != null && splitted.length >= 4 && ip.equals(splitted[0])) {
                    // Basic sanity check
                    String mac = splitted[3];
                    if (mac.matches("..:..:..:..:..:..")) {
                        return mac;
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
