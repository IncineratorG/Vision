package com.vision.common.services.firebase_paths;


import java.util.Arrays;
import java.util.List;

public class FBSPathsService {
    private static FBSPathsService sInstance;

    private final String APP_PATH = "vision";
    private final String GROUPS_PATH = "groups";

    public FBSPathsService() {

    }

    public static synchronized FBSPathsService get() {
        if (sInstance == null) {
            sInstance = new FBSPathsService();
        }

        return sInstance;
    }

    public List<String> currentRequestsPath() {
        return Arrays.asList("emulatorTestField", "testSubfield", "REQUEST");
    }

    public List<String> receiverRequestsPath(String receiverLogin) {
        return Arrays.asList("emulatorTestField", "testSubfield", "REQUEST");
    }

    public List<String> groupNamePath(String groupName) {
        return Arrays.asList(APP_PATH, GROUPS_PATH, groupName);
    }

    public List<String> groupPasswordPath(String groupName, String groupPassword) {
        return Arrays.asList(APP_PATH, GROUPS_PATH, groupName, groupPassword);
    }

    public List<String> devicePath(String groupName, String groupPassword, String deviceName) {
        return Arrays.asList(APP_PATH, GROUPS_PATH, groupName, groupPassword, deviceName);
    }

    public List<String> groupRootPath(String groupName, String groupPassword) {
        return Arrays.asList(APP_PATH, GROUPS_PATH, groupName, groupPassword);
    }

    // ===
    public List<String> test() {
        return Arrays.asList(APP_PATH, GROUPS_PATH, "a", "b", "c");
    }
    // ===
}
