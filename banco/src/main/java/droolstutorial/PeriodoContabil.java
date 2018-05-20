package droolstutorial;

import java.util.Date;

public class PeriodoContabil {
	private Date dtInicio;
	private Date dtFim;
	
	public PeriodoContabil(Date dtInicio, Date dtFim) {
		super();
		this.dtInicio = dtInicio;
		this.dtFim = dtFim;
	}
	
	public PeriodoContabil() {}
	
	public Date getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	public Date getDtFim() {
		return dtFim;
	}
	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}
	
	@Override
	public String toString() {
		return "PeriodoContabil [dtInicio=" + dtInicio + ", dtFim=" + dtFim + "]";
	}	
}
