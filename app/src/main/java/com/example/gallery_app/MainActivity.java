  package com.example.gallery_app;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;

import java.util.ArrayList;

  public class MainActivity extends AppCompatActivity implements AddClicked{

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        galleryAdapter = new GalleryAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(galleryAdapter);

    }

      @Override
      public void clicked() {
          Pix.start(this, Options.init().setRequestCode(100).setCount(1));
      }


      @Override
      public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
          switch (requestCode) {
              case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                  if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      Pix.start(this, Options.init().setRequestCode(100));
                  } else {
                      Toast.makeText(MainActivity.this, "Approve permissions to open image picker", Toast.LENGTH_LONG).show();
                  }
                  return;
              }
          }
      }

      @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          if (resultCode == Activity.RESULT_OK && requestCode == 100) {
              ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
              galleryAdapter.add(returnValue.get(0));
          }
      }
  }
