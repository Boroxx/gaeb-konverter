import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Dateimanager {

	private File inputFile;
	public static ArrayList<ArrayList<String>> positionen = new ArrayList<ArrayList<String>>();

	public Dateimanager() {

	}

	public Dateimanager(File input) {
		inputFile = input;

	}

	public void einlesen() {

		BufferedReader br = null;
		FileReader fr = null;
		String inputString;

		try {
			fr = new FileReader("leistungen_new_2018.txt");
	
			br = new BufferedReader(fr);

			while ((inputString = br.readLine()) != null) {

				String[] tempArray = inputString.split(";");
				ArrayList<String> data = new ArrayList<String>(3);
				data.add(tempArray[0]);
				data.add(tempArray[1]);
				data.add(tempArray[2]);
				positionen.add(data);

			}

			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static String[][] get2DArrayData() {

		String[][] array = new String[positionen.size()][];
		for (int i = 0; i < positionen.size(); i++) {
			ArrayList<String> row = positionen.get(i);
			array[i] = row.toArray(new String[row.size()]);
		}

		return array;
	}

	public void displayData() {

		System.out.println(positionen);
	}

	public static void main(String[] args) {

		Dateimanager man = new Dateimanager();
		man.einlesen();
		// man.displayData();
		Display dis = new Display();
	}

}
