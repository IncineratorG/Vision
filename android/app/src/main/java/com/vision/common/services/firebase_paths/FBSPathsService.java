package com.vision.common.services.firebase_paths;


import java.util.Arrays;
import java.util.List;

public class FBSPathsService {
    private static FBSPathsService sInstance;

    public FBSPathsService() {

    }

    public static synchronized FBSPathsService get() {
        if (sInstance == null) {
            sInstance = new FBSPathsService();
        }

        return sInstance;
    }

    public List<String> requestPath() {
        return Arrays.asList("emulatorTestField", "testSubfield", "REQUEST");
    }
}
