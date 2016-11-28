package edu.ptu.demo.test.http;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.alibaba.fastjson.JSONObject;

import edu.ptu.demo.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.POST;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class OkHttpActivity extends FragmentActivity {
    public static final String API_URL = "http://192.168.66.18:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

        JsonService jsonService = retrofit.create(JsonService.class);
        final Observable<JSONObject> callOrObservable = jsonService.getString();
        try {
//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                    try {
//                        Response<ResponseBody> execute = callOrObservable.execute();
//                        System.out.println(""+execute);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();

//             callOrObservable.enqueue(new Callback<JSONObject>() {
//                @Override
//                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
//                    System.out.println(""+response);
//                }
//
//                @Override
//                public void onFailure(Call<JSONObject> call, Throwable t) {
//                    System.out.println("");
//                }
//            });

            callOrObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<JSONObject>() {
                        @Override
                        public void call(JSONObject jsonObject) {
                            System.out.println("");
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface JsonService {
        @POST("examples/base.json")
        Observable<JSONObject> getString();
    }
}
