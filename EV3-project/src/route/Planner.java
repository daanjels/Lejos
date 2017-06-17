package route;

/**
 * A main method to test my classes
 * 
 * @author Arqetype
 *
 */

public class Planner {

	public static void main(String[] args) {
		City H = new City("Hobbiton", 0, 0);
		City S = new City("The Shire", 5, 10);
		City B = new City("Bree", 15, 0);
		City F = new City("Sarn Ford", 20, 10);
		City W = new City("Weathertop", 30, -5);
		City T = new City("Tharbad", 30, 20);
		City O = new City("Ost-in-edhil", 40, 25);
		City L = new City("Last Bridge", 45, 0);
		City R = new City("Rivendell", 60, 5);
		City M = new City("Moira", 60, 25);
		
		Road HS = new Road(H, S);
		Road HB = new Road(H, B);
		Road SF = new Road(S, F);
		Road FT = new Road(F, T);
		Road BW = new Road(B, W);
		Road WL = new Road(W, L);
		Road TO = new Road(T, O);
		Road LR = new Road(L, R);
		Road OM = new Road(O, M);
		
		Map middleEarth = new Map("Middle Earth");
		middleEarth.addCity(H);
		middleEarth.addCity(S);
		middleEarth.addCity(B);
		middleEarth.addCity(F);
		middleEarth.addCity(W);
		middleEarth.addCity(T);
		middleEarth.addCity(O);
		middleEarth.addCity(L);
		middleEarth.addCity(R);
		middleEarth.addCity(M);
		
		middleEarth.addRoad(HS);
		middleEarth.addRoad(HB);
		middleEarth.addRoad(SF);
		middleEarth.addRoad(LR);
		middleEarth.addRoad(FT);
		middleEarth.addRoad(BW);
		middleEarth.addRoad(TO);
		middleEarth.addRoad(WL);
		middleEarth.addRoad(OM);
		
		System.out.println(middleEarth);
		
		System.out.println("Find a route using a depth first search...");
		middleEarth.planDeep(H, R);
	}

}
