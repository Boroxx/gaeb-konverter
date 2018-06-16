
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

public class CSVParser {

	private ArrayList<ArrayList<String>> data;
	private File file;
	String positionen;
	private JTextField name;

	public CSVParser(JTextField name) {

		this.name = name;

	}

	private void saveDataAsCSV() {
		FileWriter filewriter = null;
		BufferedWriter br = null;
		try {
			filewriter = new FileWriter(file.toString());
			br = new BufferedWriter(filewriter);
			br.write(splitData());
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String splitData() {
		String header = "KOPF;ZZ18-000;;;;;\r\n";
		for (int row = 0; row < data.size(); row++) {
			header += data.get(row).get(0) + ";";

			for (int col = 3; col < 8; col++) {
				header += data.get(row).get(col) + ";";
			}
			header += "\r\n";

		}
		System.out.println(header);
		return header;
	}

	public void storeData() {

		data = Display.getUpdatedAufmass();
		file = new File("." + name.getText() + ".csv");
		saveDataAsCSV();

	}

}
