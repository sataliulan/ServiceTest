package com.example.servicetest;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MyService extends Service {

    private final String url = "http://win.web.rh03.sycdn.kuwo.cn/171810bc460be4beeccc7f9848bf9f6d/571d9781/resource/m3/57/16/3573239238.mp4";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    Dialog dialog;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "-----service started!!----------", Toast.LENGTH_LONG).show();

        try {
            new DialogThread().start();
          /*  new Thread() {
                @Override
                public void run() {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
                    Looper.prepare();
                    initDialog(getApplicationContext());
                    dialog.show();
                    Looper.loop();
                }
            }.start();*/

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return START_STICKY;
    }

    private final String TAG = "com.example.servicetest";

    private class DialogThread extends Thread {
        private MediaPlayer mediaPlayer;
        private SurfaceHolder surfaceHolder;

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
            Looper.prepare();
            initDialog(getApplicationContext());
            dialog.show();
            Looper.loop();
        }

        //private String url="https://vt.tumblr.com/tumblr_o66w0fit4G1uy9sjk_480.mp4";
        private String url = "http://win.web.rh03.sycdn.kuwo.cn/f72eb8c40eee5d42c282f1d8c4222183/571ed53a/resource/m3/57/16/3573239238.mp4";

        private void initWebView(WebView webView) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setDefaultTextEncodingName("utf-8");
            webSettings.setSavePassword(false);
            webSettings.setSaveFormData(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(false);
            webSettings.setUseWideViewPort(true);//适配屏幕
            webSettings.setLoadWithOverviewMode(true);//缩放至屏幕大小
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);

                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }

        private void initDialog(Context context) {
            if (null == dialog) {
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
                Button btnCancel = (Button) view.findViewById(R.id.dialog_btn);
                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();

                        }
                    }
                });
                final Button btnShow = (Button) view.findViewById(R.id.tv_play);
                final WebView webView = (WebView) view.findViewById(R.id.web_show);
                initWebView(webView);
                btnShow.setEnabled(false);
                btnShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "-----Call start()------------");
                        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                            webView.loadUrl(url);
                            mediaPlayer.start();
                        }
                    }
                });

                SurfaceView sfvShow = (SurfaceView) view.findViewById(R.id.sfv_show);
                surfaceHolder = sfvShow.getHolder();
                /**
                 * create MediaPlayer
                 */
                // mediaPlayer=MediaPlayer.create(context,R.raw.orange);
                surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        mediaPlayer = new MediaPlayer();
                        setMediaPlayer(btnShow,mediaPlayer);
                       /* try {
                            mediaPlayer.setDataSource(url);
                            mediaPlayer.prepareAsync();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDisplay(surfaceHolder);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        try {

//                            mediaPlayer.prepareAsync();

                            mediaPlayer.setDisplay(holder);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {

                    }
                });
                surfaceHolder.setKeepScreenOn(true);
                surfaceHolder.setFixedSize(320, 240);
                surfaceHolder.setFormat(PixelFormat.OPAQUE);
               // setMediaPlayer(btnShow);
                dialog = new Dialog(context, R.style.define_dialog);

                dialog.setContentView(view);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                DisplayMetrics dm = new DisplayMetrics();
                WindowManager manager =
                        (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                //WindowManager manager = (WindowManager) getSystemService("window");
                manager.getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels;
                int height = dm.heightPixels;
                Window mWindow = dialog.getWindow();
                mWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                mWindow.setLayout((int) (width * 0.3f), (int) (height * 0.3f));
                mWindow.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                mWindow.setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface mdialog) {
                        // TODO Auto-generated method stub
                        if (dialog == null || dialog.isShowing()) {
                            return;
                        }
                        if (mediaPlayer != null) {
                            mediaPlayer.release();
                        }
                        Log.e(TAG, "-------Dialog dismissed!!---------");

                    }
                });
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Toast.makeText(getApplicationContext(), "---Dialog Showing----", Toast.LENGTH_SHORT);
                        if (mediaPlayer != null) {
                            Log.e(TAG, "------Dialog media ready to prepare------");
                            try {

                                //mediaPlayer.setDataSource(url);
                                //mediaPlayer.prepareAsync();
                                // mediaPlayer.prepare();
                                //  Log.e(TAG, "------Dialog media prepared------");
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG, e.getMessage());
                            }
                        } else
                            Log.e(TAG, "------Dialog media can't start------");
                    }
                });
            }

        }

        private void setMediaPlayer(final Button btnShow,final MediaPlayer _mediaPlayer) {

            _mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            try {
                _mediaPlayer.setDataSource(url);
                // mediaPlayer.prepareAsync();
                _mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                _mediaPlayer.setDisplay(surfaceHolder);

            } catch (IOException e) {
                e.printStackTrace();
            }
            _mediaPlayer.prepareAsync();
            _mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.e(TAG, "------Dialog media begin to start------");
                    btnShow.setEnabled(true);
                    //mediaPlayer.start();
                }
            });
        }


    }

}
