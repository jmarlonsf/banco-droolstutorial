package droolstutorial;

public class ContaPrivada extends Conta{

	private Cliente dono;

	public Cliente getDono() {
		return dono;
	}

	public void setDono(Cliente dono) {
		this.dono = dono;
	}
	
	@Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("-----Conta Privada-----)\n");
        buff.append(super.toString());
        if (this.dono != null) {
            buff.append("Dono da conta: " + this.dono.toString() +"\n");
        }
        buff.append("-----Conta Privada fim-)");
        return buff.toString();
    }
	
}