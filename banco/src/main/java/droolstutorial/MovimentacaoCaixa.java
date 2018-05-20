package droolstutorial;

import java.text.DateFormat;
import java.util.Date;

public class MovimentacaoCaixa {
	
	public static int CREDITO = 1;
    public static int DEBITO = 2;
	
	private Date dtMov;
	private double vlMontante;
	private int tpMov;
	private long nrConta;
	
	public MovimentacaoCaixa() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MovimentacaoCaixa(Date dtMov, double vlMontante, int tpMov, long nrConta) {
		super();
		this.dtMov = dtMov;
		this.vlMontante = vlMontante;
		this.tpMov = tpMov;
		this.nrConta = nrConta;
	}

	public Date getDtMov() {
		return dtMov;
	}
	public void setDtMov(Date dtMov) {
		this.dtMov = dtMov;
	}
	public double getVlMontante() {
		return vlMontante;
	}
	public void setVlMontante(double vlMontante) {
		this.vlMontante = vlMontante;
	}
	public int getTpMov() {
		return tpMov;
	}
	public void setTpMov(int tpMov) {
		this.tpMov = tpMov;
	}
	public long getNrConta() {
		return nrConta;
	}
	public void setNrConta(long nrConta) {
		this.nrConta = nrConta;
	}
	
//	@Override
//	public String toString() {
//		return "MovimentacaoCaixa [dtMov=" + dtMov + ", vlMontante=" + vlMontante + ", tpMov=" + tpMov + ", nrConta="
//				+ nrConta + "]";
//	}
	
	@Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuffer buff = new StringBuffer();
        buff.append("-----Movimentação do Caixa-----)\n");
        buff.append("Número da conta=" + this.nrConta + "\n");
        if (this.dtMov != null) {
            buff.append("Data do movimento= "
                    + DateFormat.getDateInstance().format(this.dtMov)
                    + "\n");
        } else {
            buff.append("Nenhuma data de movimento setada\n");
        }
        buff.append("Montante movimentado=" + this.vlMontante + "\n");
        buff.append("-----Movimentação do Caixa----)");
        return buff.toString();
    }
}
