package org.wesss.game_framework.test;

import org.wesss.game_framework.canvas.GameCanvasGraphics;
import org.wesss.game_framework.domain.GameObj;
import org.wesss.game_framework.mainLoop.MainLoopAdvancedInterface;
import org.wesss.game_framework.mainLoop.MainLoopCustomGroupsInterface;
import org.wesss.game_framework.mainLoop.MainLoopGroup;
import org.wesss.game_framework.mainLoop.MainLoopGroupFactory;
import org.wess.general_utils.collection.MapToSets;

import java.util.Set;

import static java.lang.Math.max;
import static org.mockito.Mockito.mock;

/**
 * A Standalone interface used for testing games without the need of any of the other gameframework overhead
 */
public class FakeMainLoopCustomGroupsInterface extends MainLoopCustomGroupsInterface {

    //TODO needs an endpoint to get/examine individual groups

    private GameCanvasGraphics dummyGraphics = mock(GameCanvasGraphics.class);
    private MapToSets<Integer, MainLoopGroup> layerToGroups;
    private int maxLayer; //TODO abstract this out in general-utils
    private MapToSets<Integer, MainLoopGroup> priorityToGroups;
    private int maxPriority;

    public static FakeMainLoopCustomGroupsInterface getFake(int maxPriority) {
        return new FakeMainLoopCustomGroupsInterface(maxPriority);
    }

    protected FakeMainLoopCustomGroupsInterface(int maxPriority) {
        super(new MainLoopGroupFactory(mock(MainLoopAdvancedInterface.class), maxPriority));
        priorityToGroups = new MapToSets<>();
        layerToGroups = new MapToSets<>();
    }

    @Override
    public MainLoopGroup createGroup(int priority, int layer) {
        MainLoopGroup newGroup = super.createGroup(priority, layer);
        layerToGroups.put(layer, newGroup);
        priorityToGroups.put(priority, newGroup);
        maxLayer = max(layer, maxLayer);
        maxPriority = max(layer, maxPriority);
        return newGroup;
    }

    @Override
    public boolean removeGroup(MainLoopGroup group) {
        //TODO double check null cases in mapToSets as called in this FakeMainLoop interface
        //TODO mapToSet removeKey and removeValue
        layerToGroups.remove(layerToGroups.getKey(group), group);
        priorityToGroups.remove(priorityToGroups.getKey(group), group);
        return super.removeGroup(group);
    }

    public void nextFrame() {
        nextFrame(dummyGraphics);
    }

    public void nextFrame(GameCanvasGraphics mockGraphics) {
        for (int i = 0; i < maxPriority + 1; i++) {
            Set<MainLoopGroup> priorityGroups = priorityToGroups.get(i);
            if (priorityGroups != null) {
                for (MainLoopGroup group : priorityGroups) {
                    for (GameObj gameObj : group.getGameObjsInMainLoop()) {
                        gameObj.update();
                    }
                }
            }
        }

        for (MainLoopGroup group : priorityToGroups.values()) {
            group.update();
        }

        for (MainLoopGroup group : priorityToGroups.values()) {
            group.paint(mockGraphics);
        }

        for (int i = 0; i < maxLayer + 1; i++) {
            Set<MainLoopGroup> layerGroups = layerToGroups.get(i);
            if (layerGroups != null) {
                for (MainLoopGroup group : layerGroups) {
                    for (GameObj gameObj : group.getGameObjsInMainLoop()) {
                        gameObj.paint(mockGraphics);
                    }
                }
            }
        }
    }
}
