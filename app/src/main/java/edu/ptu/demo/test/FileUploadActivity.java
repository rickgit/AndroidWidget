package edu.ptu.demo.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import edu.ptu.demo.R;
import edu.ptu.demo.test.server.FileUploadService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Part;

/**
 * Created by WangAnshu on 2016/11/14.
 */

public class FileUploadActivity extends Activity {
    private static final Retrofit sRetrofit = new Retrofit.Builder()
            .baseUrl("http://baidu.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//   .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
            .build();

    private static final FileUploadService apiManager = sRetrofit.create(FileUploadService.class);

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tvUpload = (TextView) findViewById(R.id.upload);
        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// 创建 RequestBody，用于封装 请求RequestBody
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("image", file.getName(), requestFile);

// 添加描述
                String descriptionString = "hello, 这是文件描述";
                RequestBody description =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), descriptionString);

// 执行请求
                Call<ResponseBody> call = apiManager.upload(description, body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        Log.v("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error:", t.getMessage());
                    }
                });
            }
        });
    }
}
