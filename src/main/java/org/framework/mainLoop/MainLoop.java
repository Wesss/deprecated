package org.framework.mainLoop;

import org.framework.interfaces.GameObj;

/**
 * This controller class is the endpoint for adding/removing objects from the MainLoop
 *
 */
public class MainLoop {

    // TODO properly remove basic interface upon calling a different interface

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
    private boolean basicOK;

    //////////////////////////////////////////////////
    // Initialization
    //////////////////////////////////////////////////

    protected MainLoop(MainLoopFactory mainLoopFactory, MainLoopAdvancedInterface advInterface, MainLoopGroupFactory groupFactory) {
        this.mainLoopFactory = mainLoopFactory;
        this.advancedInterface = advInterface;
        this.groupFactory = groupFactory;

        // basic API setup
        basicOK = true;
        foregroundGroup = groupFactory.createMainLoopGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        backgroundGroup = groupFactory.createMainLoopGroup(DEFAULT_PRIORITY, BACKGROUND_LAYER);
    }

    //////////////////////////////////////////////////
    // Interface Swapping
    //////////////////////////////////////////////////

    /**
     * Switches
     *
     * @param upperBoundPriority
     * @return an interface for more detailed control over the mainLoop
     */
    public MainLoopCustomGroupsInterface CustomGroups(int upperBoundPriority) {
        return mainLoopFactory.getCustomGroupsInterface(upperBoundPriority);
    }

    /**
     * After this call, all BasicAPI calls (calls through this mainloop) and CustomGroupInterface calls will
     * disallowed
     * @return an interface for more detailed control over the mainLoop
     */
    public MainLoopAdvancedInterface advancedInterface() {
        disableBasicInterface();
        return advancedInterface;
    }

    private void disableBasicInterface() {
        basicOK = false;
        groupFactory.destoryMainLoopGroup(foregroundGroup);
        groupFactory.destoryMainLoopGroup(backgroundGroup);
    }

    /**
     * Adds obj to the foreground layer, removing it from the background layer if part of the
     * background
     *
     * @param obj
     * @throws Exception
     */
    public void add(GameObj obj) {
        if (!basicOK)
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
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.remove(obj);
        backgroundGroup.add(obj);
    }

    /**
     * @param obj
     * @return true iff obj is currently being kept track of by the mainLoop's basic API
     */
    public boolean contains(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        return foregroundGroup.contains(obj) || backgroundGroup.contains(obj);
    }

    /**
     * @param obj
     * @return true iff obj was present and successfully removed from the mainLoop's basic API
     */
    public boolean remove(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        return (foregroundGroup.remove(obj) || backgroundGroup.remove(obj));
    }

    /**
     * Marks the mainLoop to be cleared at the next frame
     */
    public void markClear() {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.markClear();
        backgroundGroup.markClear();
    }

    /**
     * Marks the mainLoop to clear all foreground objs at the next frame
     */
    public void markClearForeground() {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.markClear();
    }

    /**
     * Marks the mainLoop to clear all background objs at the next frame
     */
    public void markClearBackground() {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        backgroundGroup.markClear();
    }

    /**
     * Holds given obj to be added to the mainLoop after the clearing phase
     *
     * @param obj
     */
    public void addPostClear(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.addPostClear(obj);
    }

    /**
     * Holds given obj to be added to the mainLoop's background group after the clearing phase
     *
     * @param obj
     */
    public void addBackgroundPostClear(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        backgroundGroup.addPostClear(obj);
    }
}
