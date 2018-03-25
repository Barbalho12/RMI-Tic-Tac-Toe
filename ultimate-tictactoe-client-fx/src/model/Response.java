package model;

import java.util.ArrayList;
import java.util.List;

public class Response {
	
	String settings[];
	String message;
	
	public Response(String response){
		init(response);
	}

	public String[] getSettings()  {
		return settings;
	}
	

	public String getMessage() {
		return message;
	}
	
	
	public void init(String text) {
		if(!text.contains("--------")) {
//			System.out.println("AQUI1");
			message = text;
			return;
		}
		text = text.replace("---------------------------------", "");
		
		text = text.replace("|", "");
		List<String> response = new ArrayList<String>();

		int cont = 0;
//		System.out.println(text);

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if(c == 'O') {
				response.add("O");
				cont++;
			}else if(c == 'X') {
				response.add("X");
				cont++;
			}else if(c == '_') {
				response.add("");
				cont++;
			}
			if(cont==81) {
				
				if(text.substring(i+1).trim().length() < 2) {
					message = null;
				}else {
//					System.out.println(text);
//					System.out.println(text.replace(" ", "").replace("\n", ).substring(cont+1).trim());

					System.out.println("AQUI2"+text.substring(i+1).trim().length());
					message = text.substring(i+1).trim();
				}
				break;
			}else {
				
			}
			
		}


		int coords[] = {1,	2,	3,		10,	11,	12,		19,	20,	21,		
						4,	5,	6,		13,	14, 15,		22,	23,	24,
						7,	8,	9,		16,	17,	18,		25,	26,	27,
						
						28, 29, 30,		37,	38, 39,		46, 47, 48, 
						31,	32,	33,		40,	41,	42,		49,	50,	51,		
						34,	35,	36,		43,	44, 45,		52,	53,	54,
						
						55,	56,	57,		64,	65,	66,		73,	74,	75,
						58,	59,	60,		67,	68,	69,		76,	77,	78,		
						61,	62,	63,		70,	71, 72,		79,	80,	81};
		
 		
		settings = new String [81];
		for(int i = 0; i < 81; i++) {
			settings[i] = response.get(coords[i]-1);
		}
	}
}
