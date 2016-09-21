package org.framework.mainLoop;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MainLoopCustomGroupsInterTest extends MainLoopTest {

    private static final int MAX_PRIORITY = 5;

    private MainLoopCustomGroupsInterface groupsInter = null;

    @Override
    @Before
    public void setup() {
        super.setup();
        groupsInter = mainLoopController.customGroupsInterface(MAX_PRIORITY);
    }

    @Test
    public void createGroup() {
        groupsInter.createGroup(0, 0);
        mainLoopModel.assertValid();
    }

    @Test
    public void createManyGroups() {
        groupsInter.createGroup(0, 0);
        groupsInter.createGroup(3, 0);
        mainLoopModel.nextFrame(mockGraphics);
        groupsInter.createGroup(3, 3);

        mainLoopModel.assertValid();
    }

    @Test
    public void createBadGroupWithOverMaxPriorityShouldThrow() {
        try {
            groupsInter.createGroup(6, 0);
            fail("should throw");
        } catch (Exception ignored) {

        }
        mainLoopModel.assertValid();
    }

    @Test
    public void createBadGroupWithNegativeParamsShouldThrow() {
        try {
            groupsInter.createGroup(-1, -1);
            fail("should throw");
        } catch (Exception ignored) {

        }
        mainLoopModel.assertValid();
    }

    @Test
    public void containsNoGroup() {
        assertThat(groupsInter.containsGroup(null), is(false));
    }

    @Test
    public void containsGroup() {
        MainLoopGroup group = groupsInter.createGroup(0, 0);
        assertThat(groupsInter.containsGroup(group), is(true));
    }

    @Test
    public void deleteNoGroups() {
        assertThat(groupsInter.removeGroup(null), is(false));
    }

    @Test
    public void deleteGroup() {
        MainLoopGroup group = groupsInter.createGroup(0, 0);
        assertThat(groupsInter.removeGroup(group), is(true));

        mainLoopModel.assertValid();
        assertThat(groupsInter.containsGroup(group), is(false));
    }

    @Test
    public void deleteGroupTwice() {
        MainLoopGroup group = groupsInter.createGroup(0, 0);
        assertThat(groupsInter.removeGroup(group), is(true));
        assertThat(groupsInter.removeGroup(group), is(false));

        mainLoopModel.assertValid();
        assertThat(groupsInter.containsGroup(group), is(false));
    }

    @Test
    public void getNoGroups() {
        Collection<MainLoopGroup> groups = groupsInter.getGroups();
        assertThat(groups, empty());
    }

    @Test
    public void getOneGroup() {
        MainLoopGroup group = groupsInter.createGroup(0, 0);
        Collection<MainLoopGroup> groups = groupsInter.getGroups();
        assertThat(groups, hasSize(1));
        assertThat(groups, hasItems(group));
    }

    @Test
    public void getManyGroups() {
        MainLoopGroup group1 = groupsInter.createGroup(0, 0);
        MainLoopGroup group2 = groupsInter.createGroup(3, 0);
        MainLoopGroup group3 = groupsInter.createGroup(3, 3);
        Collection<MainLoopGroup> groups = groupsInter.getGroups();
        assertThat(groups, hasSize(3));
        assertThat(groups, hasItems(group1, group2, group3));
    }

    @Test
    public void clearNoGroups() {
        groupsInter.removeAllGroups();
        mainLoopModel.assertValid();
    }

    @Test
    public void clearGroups() {
        groupsInter.createGroup(0, 0);
        groupsInter.createGroup(1, 1);
        mainLoopModel.nextFrame(mockGraphics);
        groupsInter.createGroup(2, 2);
        groupsInter.removeAllGroups();

        mainLoopModel.assertValid();
        assertThat(groupsInter.getGroups(), empty());
    }
}
