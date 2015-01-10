/****************************************************************************
Copyright (c) 2008-2010 Ricardo Quesada
Copyright (c) 2010-2012 cocos2d-x.org
Copyright (c) 2011      Zynga Inc.
Copyright (c) 2013-2014 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class AppActivity extends Cocos2dxActivity {
	private AdView adView;
	private static InterstitialAd interstitial;
	private static boolean bShowAd = false;
	private static Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;

		// 创建adView。
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-5072970286349933/3457682424");
		adView.setAdSize(AdSize.BANNER);
		
        FrameLayout fl = (FrameLayout) ((ViewGroup)this.findViewById(android.R.id.content)).getChildAt(0);
        
        final LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        this.setContentView(layout);
        
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.MATCH_PARENT,
        		LinearLayout.LayoutParams.WRAP_CONTENT,
        		1.0f);
        layout.addView(fl, param);

        final LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.MATCH_PARENT,
        		100);
        String result = "";
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL("http://wuruihong.com/eatnum.php");
//            url = new URL("http://wuruihong.com/eatnum.php?s=xiaomi");
//            url = new URL("http://wuruihong.com/eatnum.php?s=360");
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (result.equals("true")) {
        	bShowAd = true;
        	layout.addView(adView, param2);
        }

		// 启动一般性请求。
		AdRequest adRequest = new AdRequest.Builder().build();

		// 在adView中加载广告请求。
		adView.loadAd(adRequest);
		
	    interstitial = new InterstitialAd(this);
	    interstitial.setAdUnitId("ca-app-pub-5072970286349933/4934415623");
	    interstitial.setAdListener(new AdListener(){

			@Override
			public void onAdFailedToLoad(int errorCode) {
				super.onAdFailedToLoad(errorCode);
				
				interstitial.loadAd(new AdRequest.Builder().build());
			}
	    	
	    });
	    
	    adRequest = new AdRequest.Builder().build();
	    interstitial.loadAd(adRequest);
	}

	public static void showInterstitialAd() {
		if (bShowAd) {
			Activity c = (Activity)context;
			c.runOnUiThread(new Runnable() {
			    @Override
			    public void run() {
					if (interstitial.isLoaded()) {
						Log.i("AdMob", "Should show Ad now");
						interstitial.show();
					} else {
						Log.e("AdMob", "Ad is not loaded yet. Reload now");
						AdRequest adRequest = new AdRequest.Builder().build();
					    interstitial.loadAd(adRequest);
					}
			    }
			});
		}
	}

	public static void sendMail() {
		if (bShowAd) {
			final Activity c = (Activity)context;
			c.runOnUiThread(new Runnable() {
			    @Override
			    public void run() {
			    	Intent data=new Intent(Intent.ACTION_SENDTO);
			    	data.setData(Uri.parse("mailto:support@wuruihong.com"));
			    	data.putExtra(Intent.EXTRA_SUBJECT, c.getString(com.app4cn.eatnum.R.string.mail_subject));
			    	c.startActivity(data);
			    }
			});
		}
	}
	
	@Override
	public void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	public void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}
}
