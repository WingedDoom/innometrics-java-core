package storage;

import models.Activity;

import java.util.List;

/**
 * An interface that a client must implement to provide InnometricsJavaCore with
 * platform-specific persistent storage for a list of {@link models.Activity} instances.
 */
public interface PersistentActivitiesStorage {
    /**
     * A method that is intended to return a sublist of {@link Activity} entities,
     * from those stored in a persistent storage.
     * @param maxBatchSize maximum size of the returned list.
     * @return a sublist of currently stored activities with the given size, or smaller (if less activities
     * are currently stored). No structural changes will ever be performed on the returned list.
     */
    List<Activity> getActivitiesBatch(int maxBatchSize);

    /**
     * A method that signals the persistent storage to delete given list of activities.
     * @param activitiesToRemove list of activities to remove. InnometricsJavaCore guarantees to pass
     *                           the same {@link List} instance it received in {@code getActivitiesBatch} method.
     */
    void removeActivities(List<Activity> activitiesToRemove);

    /**
     * A method that signals the persistent storage to save given list of activities.
     * @param newActivities list of activities to save in a persistent storage.
     */
    void saveActivities(List<Activity> newActivities);
}
