package com.example.zakh29pr;

import android.app.Activity;
        import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
        import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class Auxillary extends Activity implements OnClickListener {

        Button btnWeb;
        Button btnMap;
        Button btnCall;

        File directory;
        final int TYPE_PHOTO = 1;
        final int TYPE_VIDEO = 2;

        final int REQUEST_CODE_PHOTO = 1;
        final int REQUEST_CODE_VIDEO = 2;

        final String TAG = "myLogs";

        ImageView ivPhoto;
/** Called when the activity is first created. */
@Override
  public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.auxillary);

          btnWeb = (Button) findViewById(R.id.btnWeb);
          btnMap = (Button) findViewById(R.id.btnMap);
          btnCall = (Button) findViewById(R.id.btnCall);

          btnWeb.setOnClickListener(this);
          btnMap.setOnClickListener(this);
          btnCall.setOnClickListener(this);
                createDirectory();
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
          }

@Override
  public void onClick(View v) {
          Intent intent;
          switch(v.getId()) {
                  case R.id.btnWeb:
                          intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com"));
                          startActivity(intent);
                          break;
                  case R.id.btnMap:
                          intent = new Intent();
                          intent.setAction(Intent.ACTION_VIEW);
                          intent.setData(Uri.parse("geo:55.754283,37.62002"));
                          startActivity(intent);
                          break;
                  case R.id.btnCall:
                          intent = new Intent(Intent.ACTION_DIAL);
                          intent.setData(Uri.parse("tel:12345"));
                          startActivity(intent);
                          break;
          }
          }
        public void onClickPhoto(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //    intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
                startActivityForResult(intent, REQUEST_CODE_PHOTO);
        }

        public void onClickVideo(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                //  intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_VIDEO));
                startActivityForResult(intent, REQUEST_CODE_VIDEO);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode,
                                        Intent intent) {
                if (requestCode == REQUEST_CODE_PHOTO) {
                        if (resultCode == RESULT_OK) {
                                if (intent == null) {
                                        Log.d(TAG, "Intent is null");
                                } else {
                                        Log.d(TAG, "Photo uri: " + intent.getData());
                                        Bundle bndl = intent.getExtras();
                                        if (bndl != null) {
                                                Object obj = intent.getExtras().get("data");
                                                if (obj instanceof Bitmap) {
                                                        Bitmap bitmap = (Bitmap) obj;
                                                        Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
                                                                + bitmap.getHeight());
                                                        ivPhoto.setImageBitmap(bitmap);
                                                }
                                        }
                                }
                        } else if (resultCode == RESULT_CANCELED) {
                                Log.d(TAG, "Canceled");
                        }
                }

                if (requestCode == REQUEST_CODE_VIDEO) {
                        if (resultCode == RESULT_OK) {
                                if (intent == null) {
                                        Log.d(TAG, "Intent is null");
                                } else {
                                        Log.d(TAG, "Video uri: " + intent.getData());
                                }
                        } else if (resultCode == RESULT_CANCELED) {
                                Log.d(TAG, "Canceled");
                        }
                }
        }

        private Uri generateFileUri(int type) {
                File file = null;
                switch (type) {
                        case TYPE_PHOTO:
                                file = new File(directory.getPath() + "/" + "photo_"
                                        + System.currentTimeMillis() + ".jpg");
                                break;
                        case TYPE_VIDEO:
                                file = new File(directory.getPath() + "/" + "video_"
                                        + System.currentTimeMillis() + ".mp4");
                                break;
                }
                Log.d(TAG, "fileName = " + file);
                return Uri.fromFile(file);
        }

        private void createDirectory() {
                directory = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "MyFolder");
                if (!directory.exists())
                        directory.mkdirs();
        }
          }
