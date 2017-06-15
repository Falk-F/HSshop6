package shop.valueobjects;

import java.util.HashMap;

/**
 * 
 * @author Tim
 *
 */
public class Warenkorb {
	HashMap<Integer, Integer> korb;

	public Warenkorb(){
		korb = new HashMap<Integer, Integer>();
	}

	public boolean artikelInWarenkorb(int nummer, int bestand){
		korb.put(nummer, bestand);
	
		return false;
	}

	public boolean artikelEntfernen(int nummer){
		korb.remove(nummer);
		return false;
	}

	public void warenkorbLeeren(){
		korb.clear();
	}

	public boolean artikelAnzahl(int nummer, int aenderung){
		int anzahl = korb.get(nummer) + aenderung;
		if(anzahl < 0){
			return false;
		}

		korb.remove(nummer);
		korb.put(nummer, anzahl);


		return true;
	}
	//	public HashMap<Integer, Integer> getKeys(){
	//		
	//		return  keys();
	//	}
	//	





}
