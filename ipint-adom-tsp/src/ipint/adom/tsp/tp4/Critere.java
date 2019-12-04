package ipint.adom.tsp.tp4;

// Objet permettant de rassembler les objectifs à minimiser
public class Critere {

	private int cout1;
	private int cout2;
	
	public Critere(int cout1, int cout2) {
		
		this.cout1 = cout1;
		this.cout2 = cout2;
	}
	
	public int getCout1() {
		
		return cout1;
	}
	
	public int getCout2() {
		
		return cout2;
	}
	
	public boolean equals(Object o) {
		
		if(this == o)
			return true;
		
		if(o == null || getClass() != o.getClass())
			return false;
		
		Critere c = (Critere) o;
		return this.cout1 == c.cout1 &&
				this.cout2 == c.cout2;
	}
}
