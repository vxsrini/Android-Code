package net.learn2develop.lbsreceiver_datalogging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    public void CopyDB(InputStream inputStream, 
            OutputStream outputStream) throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String destDir = "/data/data/" + getPackageName() +
                "/databases/";
        String destPath = destDir + "MyDB";
        File f = new File(destPath);
        if (!f.exists()) {
        	//---make sure directory exists---
        	File directory = new File(destDir);
        	directory.mkdirs();
        	//---copy the db from the assets folder into 
            // the databases folder---
            try {
				CopyDB(getBaseContext().getAssets().open("mydb"),
				        new FileOutputStream(destPath));
			} catch (FileNotFoundException e) {		
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
    }

    public void onStart(View view) {        
        startService(new Intent(getBaseContext(), MyService.class));
    }

    public void onStop(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));        
    }    
    
}
