package maze;

import graph.Edge;
import graph.Graph;
import graph.UnionSetGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import maze.Maze.Connection;

/**
 * This class contains static methods for generating mazes
 * 
 * @author Wesley Cox
 * @last_edited 12/01/15
 */
public class MazeGenerator {
	
	/**
	 * Generates a cycleless maze.
	 * @param x the width of the maze (in squares)
	 * @param y the height of the maze (in squares)
	 * @return a graph representing a maze such that there is only one unique path from any two unique squares
	 */
	public static Graph<Grid, Connection> createCyclelessMaze(int x, int y) {
		UnionSetGroup<Grid> gridGroup = new UnionSetGroup<Grid>();
		List<Edge<Grid, Connection>> paths = new ArrayList<>();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				Grid curGrid = new Grid(i, j);
				gridGroup.add(curGrid);
				
				//note only upward and rightward edges are added here; mirrored edges will be added later
				if (i != x - 1)
					paths.add(new Edge<Grid, Connection>(curGrid, new Grid(i + 1, j),
							Connection.NORMAL));
				if (j != y - 1)
					paths.add(new Edge<Grid, Connection>(curGrid, new Grid(i, j + 1),
							Connection.NORMAL));
			}
		}
		
		//build graph
		Graph<Grid, Connection> result = new Graph<>();
		Collections.shuffle(paths);
		
		int i = 0;
		while (gridGroup.getSetCount() != 1) {
			Edge<Grid, Connection> curEdge = paths.get(i);
			//if the edge's prev and dest are not in the same group
			if (!gridGroup.isSameGroup(curEdge.getPrev(), curEdge.getDest())) {
				gridGroup.combine(curEdge.getPrev(), curEdge.getDest());
				
				//add the edge (and its reverse) to the graph
				result.add(curEdge);
				result.add(curEdge.getDest(), curEdge.getPrev(), curEdge.getLabel());
			}
			i++;
		}
		
		return result;
	}
}
