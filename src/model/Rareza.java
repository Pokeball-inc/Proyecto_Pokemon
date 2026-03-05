package model;

public enum Rareza {

	BASICO,
	RARO,
	EPICO,
	LEGENDARIO;
	
	private double ratio;
	
	public double getRatio() {
		return ratio;
	}
}
