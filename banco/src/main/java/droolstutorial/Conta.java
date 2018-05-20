package droolstutorial;

public class Conta {
	private long nrConta;
	private double vlBalanco;
	
	public long getNrConta() {
		return nrConta;
	}
	public void setNrConta(long nrConta) {
		this.nrConta = nrConta;
	}
	public double getVlBalanco() {
		return vlBalanco;
	}
	public void setVlBalanco(double vlBalanco) {
		this.vlBalanco = vlBalanco;
	}
	
	@Override
	public String toString() {
		return "Conta [nrConta=" + nrConta + ", vlBalanco=" + vlBalanco + "]";
	}
}
