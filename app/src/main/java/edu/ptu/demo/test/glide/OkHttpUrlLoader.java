package edu.ptu.demo.test.glide;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import edu.ptu.demo.test.BaseApp;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.internal.tls.OkHostnameVerifier;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * A simple model loader for fetching media over http/https using OkHttp.
 */
public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    private final Call.Factory client;

    public OkHttpUrlLoader(Call.Factory client) {
        this.client = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return new OkHttpStreamFetcher(client, model);
    }

    /**
     * The default factory for {@link OkHttpUrlLoader}s.
     */
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private static volatile Call.Factory internalClient;
        private Call.Factory client;

        /**
         * Constructor for a new Factory that runs requests using a static singleton client.
         */
        public Factory() {
            this(getInternalClient());
        }

        /**
         * Constructor for a new Factory that runs requests using given client.
         *
         * @param client this is typically an instance of {@code OkHttpClient}.
         */
        public Factory(Call.Factory client) {
            this.client = client;
        }

        private static Call.Factory getInternalClient() {
            if (internalClient == null) {
                synchronized (Factory.class) {
                    if (internalClient == null) {
                        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                            @Override
                            public boolean verify(String arg0, SSLSession arg1) {
                                return true;
                            }

                        });
//                        internalClient = new OkHttpClient();
                        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
//                        builder.addInterceptor(new RequestInterceptor());
                        builder.connectTimeout(15, TimeUnit.SECONDS);
                        try {
                            ArrayList httpsSSLSocketFactory = getHttpsSSLSocketFactory();
                            builder.sslSocketFactory((SSLSocketFactory) httpsSSLSocketFactory.get(0), (X509TrustManager) httpsSSLSocketFactory.get(1));
                            builder.hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            });
//            builder.sslSocketFactory((SSLSocketFactory) httpsSSLSocketFactory.get(0));
                        } catch (Exception e) {
                            System.out.println("===>"+e.getMessage());
                        }
                        internalClient = builder.build();((OkHttpClient)internalClient).hostnameVerifier();

                    }
                }
            }
            return internalClient;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new OkHttpUrlLoader(client);
        }

        @Override
        public void teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }

    private static ArrayList getHttpsSSLSocketFactory() throws Exception {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509"); // 得到x509证书工厂
//            InputStream in = BaseApp.mApp.getApplicationContext().getAssets().open("aicai-open.cer");  //https证书
//            Certificate ca = cf.generateCertificate(in);
            KeyStore keystore = KeyStore.getInstance("BKS");// 创建证书库
            keystore.load(null, null);
//            keystore.setCertificateEntry("ca", ca);// 将获取的证书加到证书库
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm(); // 信任管理器
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keystore);
            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
            SSLContext context = SSLContext.getInstance("TLS"); // 生成实现指定安全套接字协议的
            context.init(null, wrappedTrustManagers, null);
            ArrayList<Object> objects = new ArrayList<>();
            objects.add(context.getSocketFactory());
            objects.add(tmf.getTrustManagers()[0]);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {

        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];

        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

    }
}
