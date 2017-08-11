package route;

/**
 * A main method to test my classes
 * 
 * @author Arqetype
 *
 */

public class Planner {

	public static void main(String[] args) {
		Map middleEarth = new Map("Middle Earth");	// load a map from a file
		
		System.out.println(middleEarth);
		
		System.out.println("Find a route using a depth first search...");
		middleEarth.planDeep(middleEarth.cities.get(0), middleEarth.cities.get(middleEarth.cities.size()-2));
	}
}
