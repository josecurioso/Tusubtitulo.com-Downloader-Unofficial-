package subtitle_model;

public enum Lang {
	
	es_ESP,
	es_LAT,
	en_GB,
	ERR;
	
	public static String getCode(Lang aux){
		switch(aux){
			case es_ESP:
				return "es_ESP";
			
			case es_LAT:
				return "es_LAT";
				
			case en_GB:
				return "en_GB";
				
			case ERR:
				return "ERR";
		}
		return "ERR";
	}
	
	public static String getName(Lang aux){
		switch(aux){
			case es_ESP:
				return "Español (España)";
			
			case es_LAT:
				return "Español (Latinoamérica)";
				
			case en_GB:
				return "English";
				
			case ERR:
				return "Unknown";
		}
		return "Unknown";
	}
	
	public static int getPr(Lang aux){
		switch(aux){
			case es_ESP:
				return 1;
			
			case es_LAT:
				return 2;
				
			case en_GB:
				return 3;
				
			case ERR:
				return 4;
		}
		return 4;
	}
	
	public static Lang getByPriority(int aux){
		switch(aux){
		case 1:
			return es_ESP;
		
		case 2:
			return es_LAT;
			
		case 3:
			return en_GB;
			
		case 4:
			return ERR;
		}
		return ERR;
	}
	
	public static Lang getEnum(String aux){
		switch(aux){
		case "Español (España)":
			return es_ESP;
		
		case "Español (Latinoamérica)":
			return es_LAT;
			
		case "English":
			return en_GB;
			
		case "Unknown":
			return ERR;
		}
		return ERR;
	}
}
