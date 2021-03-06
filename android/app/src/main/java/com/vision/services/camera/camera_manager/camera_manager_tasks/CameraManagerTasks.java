package com.vision.services.camera.camera_manager.camera_manager_tasks;


import com.vision.services.camera.camera_manager.CameraManager;
import com.vision.services.camera.data.camera_preview_frame_data.CameraPreviewFrameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CameraManagerTasks {
    private Map<String, Map<String, CameraManager.CameraManagerTask>> mTasks;
    private Map<String, CameraManager.CameraManagerTaskCleanup> mTasksCleanup;

    public CameraManagerTasks() {
        mTasks = new HashMap<>();
        mTasksCleanup = new HashMap<>();
    }

    public synchronized String add(CameraManager.CameraManagerTask task) {
        String taskType = task.type();

        Map<String, CameraManager.CameraManagerTask> tasksOfType = mTasks.get(taskType);
        if (tasksOfType == null) {
            tasksOfType = new HashMap<>();
        }

        String taskId = UUID.randomUUID() + "_" + System.currentTimeMillis();
        tasksOfType.put(taskId, task);

        mTasks.put(taskType, tasksOfType);

        return taskId;
    }

    public synchronized void addTaskTypeCleanup(String taskType,
                                                CameraManager.CameraManagerTaskCleanup cleanup) {
        mTasksCleanup.put(taskType, cleanup);
    }

    public synchronized void removeTasksOfType(String taskType) {
        mTasks.remove(taskType);
        mTasksCleanup.remove(taskType);
    }

    public synchronized int tasksOfTypeCount(String taskType) {
        Map<String, CameraManager.CameraManagerTask> tasksOfType = mTasks.get(taskType);
        if (tasksOfType == null) {
            return 0;
        }

        return tasksOfType.size();
    }

    public synchronized int totalTasksCount() {
        int totalTasksCount = 0;
        for (Map.Entry<String, Map<String, CameraManager.CameraManagerTask>> tasksOfTypeEntry : mTasks.entrySet()) {
            totalTasksCount = totalTasksCount + tasksOfTypeEntry.getValue().size();
        }
        return totalTasksCount;
    }

    public synchronized void clear(boolean keepCleanups) {
        mTasks.clear();
        if (!keepCleanups) {
            mTasksCleanup.clear();
        }
    }

    public synchronized int onFrameChanged(CameraPreviewFrameData frame) {
        int totalNumberOfRemainingTasks = 0;

        for (Map.Entry<String, Map<String, CameraManager.CameraManagerTask>> tasksOfTypeEntry : mTasks.entrySet()) {
            Map<String, CameraManager.CameraManagerTask> tasksOfType = tasksOfTypeEntry.getValue();

            List<String> taskToRemoveIds = new ArrayList<>();
            for (Map.Entry<String, CameraManager.CameraManagerTask> tasksEntry : tasksOfType.entrySet()) {
                String taskId = tasksEntry.getKey();
                CameraManager.CameraManagerTask task = tasksEntry.getValue();

                boolean needRemoveTask = task.onCameraPreviewImageData(frame);
                if (needRemoveTask) {
                    taskToRemoveIds.add(taskId);
                }
            }

            if (taskToRemoveIds.size() == tasksOfType.size()) {
                tasksOfType.clear();
            } else {
                for (int i = 0; i < taskToRemoveIds.size(); ++i) {
                    String taskId = taskToRemoveIds.get(i);
                    tasksOfType.remove(taskId);
                }
            }

            totalNumberOfRemainingTasks = totalNumberOfRemainingTasks + tasksOfType.size();
        }

        return totalNumberOfRemainingTasks;
    }

    public synchronized void runCleanups() {
        for (Map.Entry<String, CameraManager.CameraManagerTaskCleanup> cleanupsEntry : mTasksCleanup.entrySet()) {
            cleanupsEntry.getValue().cleanup();
        }
        mTasksCleanup.clear();
    }
}
