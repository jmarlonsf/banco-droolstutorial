package droolstutorial;

public class Cliente {

	private String nome;
	private String sobrenome;
	private String pais;
	
	public Cliente(String nome, String sobrenome, String pais) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.pais = pais;
	}
	
	public Cliente() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	@Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("-----Cliente-----)\n");
        buff.append("Nome=" + this.nome + "\n");
        buff.append("Sobrenome=" + this.sobrenome + "\n");
        buff.append("País=" + this.pais + "\n");
        buff.append("-----Cliente fim-)");
        return buff.toString();
    }
}
