package com.vision.rn_modules.surveillance.module_actions_executor.handlers.helpers;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

public class AssetFilesHelper {
    public static void copyAssets(Context context, Set<String> assetsToCopy) {
        AssetManager assetManager = context.getAssets();
        String[] assetFiles = null;
        try {
            assetFiles = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }

        if (assetFiles != null) for (String filename : assetFiles) {
            if (assetsToCopy.contains(filename)) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(filename);
                    File outFile = new File(context.getExternalFilesDir(null), filename);
                    if (outFile.exists()) {
                        boolean deleted = deleteFile(outFile);
                        if (!deleted) {
                            Log.d(
                                    "tag",
                                    "CopyAssetsHelper->copyAssets()->UNABLE_TO_REMOVE_FILE: " +
                                            outFile.getAbsolutePath() + "; IS_FILE: " +
                                            outFile.isFile() + "; IS_DIRECTORY: " +
                                            outFile.isDirectory()
                            );
                            continue;
                        }
                    }

                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
                } catch (IOException e) {
                    Log.e("tag", "Failed to copy asset file: " + filename, e);
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            // NOOP
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            // NOOP
                        }
                    }
                }
            }
        }
    }

    public static void deleteCopiedAssetFiles(Context context, List<String> assetFileNames) {
        for (int i = 0; i < assetFileNames.size(); ++i) {
            String fileName = assetFileNames.get(i);
            File fileToDelete = new File(context.getExternalFilesDir(null), fileName);
            if (fileToDelete.exists()) {
                boolean deleted = deleteFile(fileToDelete);
                Log.d(
                        "tag",
                        "CopyAssetsHelper->deleteCopiedAssetFiles()->FILE: " +
                                fileToDelete.getAbsolutePath() +
                                "; DELETED: " + deleted
                );
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private static boolean deleteFile(File file) {
        return file.delete();
    }

//    public static void copyAssets(Context context, Set<String> assetNames) {
//        AssetManager assetManager = context.getAssets();
//        String[] files = null;
//        try {
//            files = assetManager.list("");
//        } catch (IOException e) {
//            Log.e("tag", "Failed to get asset file list.", e);
//        }
//        if (files != null) for (String filename : files) {
//            if (assetNames.contains(filename)) {
//                InputStream in = null;
//                OutputStream out = null;
//                try {
//                    in = assetManager.open(filename);
//                    File outFile = new File(context.getExternalFilesDir(null), filename);
//                    out = new FileOutputStream(outFile);
//                    copyFile(in, out);
//                } catch (IOException e) {
//                    Log.e("tag", "Failed to copy asset file: " + filename, e);
//                } finally {
//                    if (in != null) {
//                        try {
//                            in.close();
//                        } catch (IOException e) {
//                            // NOOP
//                        }
//                    }
//                    if (out != null) {
//                        try {
//                            out.close();
//                        } catch (IOException e) {
//                            // NOOP
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private static void copyFile(InputStream in, OutputStream out) throws IOException {
//        byte[] buffer = new byte[1024];
//        int read;
//        while((read = in.read(buffer)) != -1){
//            out.write(buffer, 0, read);
//        }
//    }
}

/*
final static String TARGET_BASE_PATH = "/sdcard/appname/voices/";

private void copyFilesToSdCard() {
    copyFileOrDir(""); // copy all files in assets folder in my project
}

private void copyFileOrDir(String path) {
    AssetManager assetManager = this.getAssets();
    String assets[] = null;
    try {
        Log.i("tag", "copyFileOrDir() "+path);
        assets = assetManager.list(path);
        if (assets.length == 0) {
            copyFile(path);
        } else {
            String fullPath =  TARGET_BASE_PATH + path;
            Log.i("tag", "path="+fullPath);
            File dir = new File(fullPath);
            if (!dir.exists() && !path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                if (!dir.mkdirs())
                    Log.i("tag", "could not create dir "+fullPath);
            for (int i = 0; i < assets.length; ++i) {
                String p;
                if (path.equals(""))
                    p = "";
                else
                    p = path + "/";

                if (!path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                    copyFileOrDir( p + assets[i]);
            }
        }
    } catch (IOException ex) {
        Log.e("tag", "I/O Exception", ex);
    }
}

private void copyFile(String filename) {
    AssetManager assetManager = this.getAssets();

    InputStream in = null;
    OutputStream out = null;
    String newFileName = null;
    try {
        Log.i("tag", "copyFile() "+filename);
        in = assetManager.open(filename);
        if (filename.endsWith(".jpg")) // extension was added to avoid compression on APK file
            newFileName = TARGET_BASE_PATH + filename.substring(0, filename.length()-4);
        else
            newFileName = TARGET_BASE_PATH + filename;
        out = new FileOutputStream(newFileName);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        in.close();
        in = null;
        out.flush();
        out.close();
        out = null;
    } catch (Exception e) {
        Log.e("tag", "Exception in copyFile() of "+newFileName);
        Log.e("tag", "Exception in copyFile() "+e.toString());
    }

}
 */
