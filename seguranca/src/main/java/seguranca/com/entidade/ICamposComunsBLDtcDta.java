package seguranca.com.entidade;

import java.util.Date;

import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.StatusAtiLclFclEnum;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoContainerEnum;
import seguranca.com.enums.TipoSimNaoEnum;
import seguranca.com.enums.TipomodalEnum;

public interface ICamposComunsBLDtcDta {
	public Date getDataCadastro();

	public Date getDataEnvioMapa();

	public Date getDataAtracacao();

	public String getDescricaoBL();

	public String getDescricaoMercadoria();

	public String getArmador();

	public String getNavioViagem();

	public String getViagem();

	public String getNumeroATI();

	public String getReferenciaCliente();

	public Float getQuantidade();

	public Integer getFreeTime();

	public Integer getTemperatura();

	public Float getCifMercadoria();

	public StatusBLEnum getStatusBLEnum();

	public ModalidadeBLEnum getModalidadeBLEnum();

	public StatusAtiLclFclEnum getStatusAtiLclFclEnum();
	
	public TipoContainerEnum getTipoContainerEnum();

	public CadastroCodigoPais getPaisOrigem();

	public CadastroCodigoPais getPaisProcedencia();

	public CadastroEspecie getTipoEmbalagemEspecie();

	public CadastroNcm getNcm();

	public boolean isLiberado();

	public boolean isInspecao();

	public boolean isDesunitizacao();

	public boolean isReunitizar();

	public boolean isEnviarParaMapa();

	public CadastroEspecie getTipoEmbalagemEncontrada();

	public TipomodalEnum getTipoModalEnum();

	public CadastroImportador getImportador();
	
	public CadastroPortos getPortos();

	public CadastroRepresentante getRepresentante();

	public CadastroComissario getCadComissario();

	public boolean isDeferido();

	public TipoSimNaoEnum getCargaImo();

	public TipoSimNaoEnum getCargaReefer();

	public TipoSimNaoEnum getCargaComMadeira();

	public TipoSimNaoEnum getEntrePosto();
}
