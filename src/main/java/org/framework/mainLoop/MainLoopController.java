package org.framework.mainLoop;

import org.framework.interfaces.GameObj;

/**
 * This controller class is the endpoint for adding/removing objects from the MainLoopController
 *
 */
public class MainLoopController {

    // TODO test interface swap prevention

    //////////////////////////////////////////////////
    // Definition
    //////////////////////////////////////////////////

    public static final String DISABLED_BASICAPI_ERRMSG = "Basic API has been disabled";

    private static final int BACKGROUND_LAYER = 0;
    private static final int FOREGROUND_LAYER = 1;

    private static final int DEFAULT_PRIORITY = 0;
    // simulates "post update"
    protected static final int GAMEOBJ_GROUP_PRIORITY = 1;

    protected static final int DEFAULT_ACTIONGROUP = 0;
    protected static final int CLEAR_ACTIONGROUP = 1;
    protected static final int POSTCLEAR_ACTIONGROUP = 2;

    private MainLoopFactory mainLoopFactory;
    private MainLoopAdvancedInterface advancedInterface;
    private MainLoopGroupFactory groupFactory;
    private MainLoopGroup foregroundGroup;
    private MainLoopGroup backgroundGroup;
    private boolean apiSwapped;
    private boolean customGroupsExists;

    //////////////////////////////////////////////////
    // Initialization
    //////////////////////////////////////////////////

    protected MainLoopController(MainLoopFactory mainLoopFactory, MainLoopAdvancedInterface advInterface, MainLoopGroupFactory groupFactory) {
        this.mainLoopFactory = mainLoopFactory;
        this.advancedInterface = advInterface;
        this.groupFactory = groupFactory;

        // basic API setup
        apiSwapped = false;
        customGroupsExists = false;
        foregroundGroup = groupFactory.createMainLoopGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        backgroundGroup = groupFactory.createMainLoopGroup(DEFAULT_PRIORITY, BACKGROUND_LAYER);
    }

    //////////////////////////////////////////////////
    // Interface Swapping
    //////////////////////////////////////////////////

    /**
     * Returns an interface for defining groups of objects that all share an update priority, painting layer.
     * After this call, basic API calls (calls through this mainLoop) and the allocating of another custom groups
     * interface will not be permitted.
     *
     * @param maximumPriority the maximum priority that will be allocated to the custom made groups
     * @return an interface for creating and manage custom MainLoopGroups for managing GameObjs
     */
    public MainLoopCustomGroupsInterface customGroupsInterface(int maximumPriority) {
        if (customGroupsExists) {
            throw new RuntimeException("Another custom groups interface has already been given out");
        }
        customGroupsExists = true;
        disableBasicInterface();
        return mainLoopFactory.getCustomGroupsInterface(maximumPriority);
    }

    /**
     * Returns an interface for manipulating the objects and actions under the hood of the mainLoop.
     * After this call, all BasicAPI calls (calls through this mainLoop) will not be permitted.
     *
     * @return an interface for more detailed control over the mainLoop
     */
    public MainLoopAdvancedInterface advancedInterface() {
        disableBasicInterface();
        return advancedInterface;
    }

    private void disableBasicInterface() {
        apiSwapped = true;
        groupFactory.destroyMainLoopGroup(foregroundGroup);
        groupFactory.destroyMainLoopGroup(backgroundGroup);
    }

    /**
     * Adds obj to the foreground layer, removing it from the background layer if part of the
     * background
     *
     * @param obj
     * @throws Exception
     */
    public void add(GameObj obj) {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        backgroundGroup.remove(obj);
        foregroundGroup.add(obj);
    }

    /**
     * Adds obj to the background layer, removing it from the foreground layer if part of the
     * foreground
     *
     * @param obj
     */
    public void addBackground(GameObj obj) {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.remove(obj);
        backgroundGroup.add(obj);
    }

    /**
     * @param obj
     * @return true iff obj is currently being kept track of by the mainLoop's basic API
     */
    public boolean contains(GameObj obj) {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        return foregroundGroup.contains(obj) || backgroundGroup.contains(obj);
    }

    /**
     * @param obj
     * @return true iff obj was present and successfully removed from the mainLoop's basic API
     */
    public boolean remove(GameObj obj) {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        return (foregroundGroup.remove(obj) || backgroundGroup.remove(obj));
    }

    /**
     * Marks the mainLoop to be cleared at the next frame
     */
    public void markClear() {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.markClear();
        backgroundGroup.markClear();
    }

    /**
     * Marks the mainLoop to clear all foreground objs at the next frame
     */
    public void markClearForeground() {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.markClear();
    }

    /**
     * Marks the mainLoop to clear all background objs at the next frame
     */
    public void markClearBackground() {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        backgroundGroup.markClear();
    }

    /**
     * Holds given obj to be added to the mainLoop after the clearing phase
     *
     * @param obj
     */
    public void addPostClear(GameObj obj) {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.addPostClear(obj);
    }

    /**
     * Holds given obj to be added to the mainLoop's background group after the clearing phase
     *
     * @param obj
     */
    public void addBackgroundPostClear(GameObj obj) {
        if (apiSwapped)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        backgroundGroup.addPostClear(obj);
    }
}
