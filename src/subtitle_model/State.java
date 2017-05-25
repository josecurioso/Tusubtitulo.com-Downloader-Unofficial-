package subtitle_model;

public enum State {
	
	COMPLETE,
	TRANSLATING,
	REVISION;
	
	public static String toString(State aux){
		switch(aux){
			case COMPLETE:
				return "Complete";
			
			case TRANSLATING:
				return "Translating";
			
			case REVISION:
				return "Revision";
		}
		return "Data error";
	}
}
